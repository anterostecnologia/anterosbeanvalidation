/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.bean.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.anteros.bean.validation.util.AccessStrategy;
import br.com.anteros.bean.validation.xml.AnnotationIgnores;
import br.com.anteros.bean.validation.xml.MetaConstraint;
import br.com.anteros.core.utils.ArrayUtils;
import br.com.anteros.core.utils.ClassUtils;
import br.com.anteros.validation.api.ConstraintValidatorFactory;
import br.com.anteros.validation.api.MessageInterpolator;
import br.com.anteros.validation.api.TraversableResolver;
import br.com.anteros.validation.api.Validation;
import br.com.anteros.validation.api.ValidationException;
import br.com.anteros.validation.api.Validator;
import br.com.anteros.validation.api.ValidatorFactory;
import br.com.anteros.validation.api.spi.ConfigurationState;

/**
 * Description: a factory is a complete configurated object that can create
 * validators.<br/>
 * This instance is not thread-safe.<br/>
 */
public class AnterosValidatorFactory implements ValidatorFactory, Cloneable {
    private static volatile AnterosValidatorFactory DEFAULT_FACTORY;
    private static final ConstraintDefaults defaultConstraints =
            new ConstraintDefaults();

    private MessageInterpolator messageResolver;
    private TraversableResolver traversableResolver;
    private ConstraintValidatorFactory constraintValidatorFactory;
    private final Map<String, String> properties;

    /**
     * information from xml parsing
     */
    private final AnnotationIgnores annotationIgnores = new AnnotationIgnores();
    private final ConstraintCached constraintsCache = new ConstraintCached();
    private final Map<Class<?>, Class<?>[]> defaultSequences;

    /**
     * access strategies for properties with cascade validation @Valid support
     */
    private final Map<Class<?>, List<AccessStrategy>> validAccesses;
    private final Map<Class<?>, List<MetaConstraint<?, ? extends Annotation>>> constraintMap;

    /**
     * Convenience method to retrieve a default global ApacheValidatorFactory
     *
     * @return {@link AnterosValidatorFactory}
     */
    public static synchronized AnterosValidatorFactory getDefault() {
        if (DEFAULT_FACTORY == null) {
            DEFAULT_FACTORY =
                Validation.byProvider(AnterosValidationProvider.class).configure().buildValidatorFactory()
                    .unwrap(AnterosValidatorFactory.class);
        }
        return DEFAULT_FACTORY;
    }

    /**
     * Set a particular {@link AnterosValidatorFactory} instance as the default.
     *
     * @param aDefaultFactory
     */
    public static void setDefault(AnterosValidatorFactory aDefaultFactory) {
        DEFAULT_FACTORY = aDefaultFactory;
    }

    /**
     * Create a new ApacheValidatorFactory instance.
     */
    public AnterosValidatorFactory(ConfigurationState configurationState) {
        properties = new HashMap<String, String>();
        defaultSequences = new HashMap<Class<?>, Class<?>[]>();
        validAccesses = new HashMap<Class<?>, List<AccessStrategy>>();
        constraintMap =
                new HashMap<Class<?>, List<MetaConstraint<?, ? extends Annotation>>>();
        configure(configurationState);
    }

    /**
     * Configure this {@link AnterosValidatorFactory} from a
     * {@link ConfigurationState}.
     *
     * @param configuration
     */
    protected void configure(ConfigurationState configuration) {
        getProperties().putAll(configuration.getProperties());
        setMessageInterpolator(configuration.getMessageInterpolator());
        setTraversableResolver(configuration.getTraversableResolver());
        setConstraintValidatorFactory(configuration
                .getConstraintValidatorFactory());
    }

    /**
     * Get the property map of this {@link AnterosValidatorFactory}.
     *
     * @return Map<String, String>
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Shortcut method to create a new Validator instance with factory's
     * settings
     *
     * @return the new validator instance
     */
    public Validator getValidator() {
        return usingContext().getValidator();
    }

    /**
     * {@inheritDoc}
     *
     * @return the validator factory's context
     */
    public AnterosFactoryContext usingContext() {
        return new AnterosFactoryContext(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized AnterosValidatorFactory clone() {
        try {
            return (AnterosValidatorFactory) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(); // VM bug.
        }
    }

    /**
     * Set the {@link MessageInterpolator} used.
     *
     * @param messageResolver
     */
    public final void setMessageInterpolator(MessageInterpolator messageResolver) {
        this.messageResolver = messageResolver;
    }

    /**
     * {@inheritDoc}
     */
    public MessageInterpolator getMessageInterpolator() {
        return messageResolver;
    }

    /**
     * Set the {@link TraversableResolver} used.
     *
     * @param traversableResolver
     */
    public final void setTraversableResolver(
            TraversableResolver traversableResolver) {
        this.traversableResolver = traversableResolver;
    }

    /**
     * {@inheritDoc}
     */
    public TraversableResolver getTraversableResolver() {
        return traversableResolver;
    }

    /**
     * Set the {@link ConstraintValidatorFactory} used.
     *
     * @param constraintValidatorFactory
     */
    public final void setConstraintValidatorFactory(
            ConstraintValidatorFactory constraintValidatorFactory) {
        this.constraintValidatorFactory = constraintValidatorFactory;
    }

    /**
     * {@inheritDoc}
     */
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return constraintValidatorFactory;
    }

    /**
     * Return an object of the specified type to allow access to the
     * provider-specific API. If the Bean Validation provider implementation
     * does not support the specified class, the ValidationException is thrown.
     *
     * @param type the class of the object to be returned.
     * @return an instance of the specified class
     * @throws ValidationException if the provider does not support the call.
     */
    public <T> T unwrap(final Class<T> type) {
        if (type.isInstance(this)) {
            @SuppressWarnings("unchecked")
            T result = (T) this;
            return result;
        }

        // FIXME 2011-03-27 jw:
        // This code is unsecure.
        // It should allow only a fixed set of classes.
        // Can't fix this because don't know which classes this method should support.
        
        if (!(type.isInterface() || Modifier.isAbstract(type
                .getModifiers()))) {
            return newInstance(type);
        }
        try {
            Class<?> cls = ClassUtils.getClass(type.getName() + "Impl");
            if (type.isAssignableFrom(cls)) {
                @SuppressWarnings("unchecked")
                T result = (T) newInstance(cls);
                return result;
            }
        } catch (ClassNotFoundException e) {
            // do nothing
        }
        throw new ValidationException("Type " + type + " not supported");
    }

    private <T> T newInstance(final Class<T> cls) {
        return AccessController.doPrivileged(new PrivilegedAction<T>() {
            public T run() {
                try {
                    return cls.newInstance();
                } catch (final Exception ex) {
                    throw new ValidationException("Cannot instantiate : " + cls, ex);
                }
            }
        });
    }

    /**
     * Get the detected {@link ConstraintDefaults}.
     *
     * @return ConstraintDefaults
     */
    public ConstraintDefaults getDefaultConstraints() {
        return defaultConstraints;
    }

    /**
     * Get the detected {@link AnnotationIgnores}.
     *
     * @return AnnotationIgnores
     */
    public AnnotationIgnores getAnnotationIgnores() {
        return annotationIgnores;
    }

    /**
     * Get the constraint cache used.
     *
     * @return {@link ConstraintCached}
     */
    public ConstraintCached getConstraintsCache() {
        return constraintsCache;
    }

    /**
     * Add a meta-constraint to this {@link AnterosValidatorFactory}'s runtime
     * customizations.
     *
     * @param beanClass
     * @param metaConstraint
     */
    public void addMetaConstraint(Class<?> beanClass,
                                  MetaConstraint<?, ?> metaConstraint) {
        List<MetaConstraint<?, ? extends Annotation>> slot;
        synchronized (constraintMap) {
            slot = constraintMap.get(beanClass);
            if (slot == null) {
                slot = new ArrayList<MetaConstraint<?, ? extends Annotation>>();
                constraintMap.put(beanClass, slot);
            }
        }
        slot.add(metaConstraint);
    }

    /**
     * Mark a property of <code>beanClass</code> for nested validation.
     *
     * @param beanClass
     * @param accessStrategy
     *            defining the property to validate
     */
    public void addValid(Class<?> beanClass, AccessStrategy accessStrategy) {
        List<AccessStrategy> slot;
        synchronized (validAccesses) {
            slot = validAccesses.get(beanClass);
            if (slot == null) {
                slot = new ArrayList<AccessStrategy>();
                validAccesses.put(beanClass, slot);
            }
        }
        slot.add(accessStrategy);
    }

    /**
     * Set the default group sequence for a particular bean class.
     *
     * @param beanClass
     * @param groupSequence
     */
    public void addDefaultSequence(Class<?> beanClass, Class<?>... groupSequence) {
        defaultSequences.put(beanClass, safeArray(groupSequence));
    }

    /**
     * Retrieve the runtime constraint configuration for a given class.
     *
     * @param <T>
     * @param beanClass
     * @return List of {@link MetaConstraint}s applicable to
     *         <code>beanClass</code>
     */
    public <T> List<MetaConstraint<T, ? extends Annotation>> getMetaConstraints(
            Class<T> beanClass) {
        final List<MetaConstraint<?, ? extends Annotation>> slot = constraintMap.get(beanClass);
        if (slot == null) {
            return Collections.emptyList();
        }
        // noinspection RedundantCast
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final List<MetaConstraint<T, ? extends Annotation>> result = (List) slot;
        return Collections.unmodifiableList(result);
    }

    /**
     * Get the {@link AccessStrategy} {@link List} indicating nested bean
     * validations that must be triggered in the course of validating a
     * <code>beanClass</code> graph.
     *
     * @param beanClass
     * @return {@link List} of {@link AccessStrategy}
     */
    public List<AccessStrategy> getValidAccesses(Class<?> beanClass) {
        final List<AccessStrategy> slot = validAccesses.get(beanClass);
        return slot == null ? Collections.<AccessStrategy> emptyList() : Collections.unmodifiableList(slot);
    }

    /**
     * Get the default group sequence configured for <code>beanClass</code>.
     *
     * @param beanClass
     * @return group Class array
     */
    public Class<?>[] getDefaultSequence(Class<?> beanClass) {
        return safeArray(defaultSequences.get(beanClass));
    }

    private static Class<?>[] safeArray(Class<?>... array) {
        return ArrayUtils.isEmpty(array) ? ArrayUtils.EMPTY_CLASS_ARRAY : ArrayUtils.clone(array);
    }
}
