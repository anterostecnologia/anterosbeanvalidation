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

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

import br.com.anteros.bean.validation.util.SecureActions;
import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.validation.api.ConstraintValidatorFactory;
import br.com.anteros.validation.api.MessageInterpolator;
import br.com.anteros.validation.api.TraversableResolver;
import br.com.anteros.validation.api.ValidationException;
import br.com.anteros.validation.api.Validator;
import br.com.anteros.validation.api.ValidatorContext;

/**
 * Description: Represents the context that is used to create
 * <code>ClassValidator</code> instances.<br/>
 */
public class AnterosFactoryContext implements ValidatorContext {
    private final AnterosValidatorFactory factory;
    private final MetaBeanFinder metaBeanFinder;

    private MessageInterpolator messageInterpolator;
    private TraversableResolver traversableResolver;
    private ConstraintValidatorFactory constraintValidatorFactory;

    /**
     * Create a new ApacheFactoryContext instance.
     * 
     * @param factory
     */
    public AnterosFactoryContext(AnterosValidatorFactory factory) {
        this.factory = factory;
        this.metaBeanFinder = buildMetaBeanFinder();
    }

    /**
     * Create a new ApacheFactoryContext instance.
     * 
     * @param factory
     * @param metaBeanFinder
     */
    protected AnterosFactoryContext(AnterosValidatorFactory factory, MetaBeanFinder metaBeanFinder) {
        this.factory = factory;
        this.metaBeanFinder = metaBeanFinder;
    }

    /**
     * Get the {@link AnterosValidatorFactory} used by this
     * {@link AnterosFactoryContext}.
     * 
     * @return {@link AnterosValidatorFactory}
     */
    public AnterosValidatorFactory getFactory() {
        return factory;
    }

    /**
     * Get the metaBeanFinder.
     * 
     * @return {@link MetaBeanFinder}
     */
    public final MetaBeanFinder getMetaBeanFinder() {
        return metaBeanFinder;
    }

    /**
     * {@inheritDoc}
     */
    public ValidatorContext messageInterpolator(MessageInterpolator messageInterpolator) {
        this.messageInterpolator = messageInterpolator;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public ValidatorContext traversableResolver(TraversableResolver traversableResolver) {
        this.traversableResolver = traversableResolver;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public ValidatorContext constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
        this.constraintValidatorFactory = constraintValidatorFactory;
        return this;
    }

    /**
     * Get the {@link ConstraintValidatorFactory}.
     * 
     * @return {@link ConstraintValidatorFactory}
     */
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return constraintValidatorFactory == null ? factory.getConstraintValidatorFactory()
            : constraintValidatorFactory;
    }

    /**
     * {@inheritDoc}
     */
    public Validator getValidator() {
        AnterosBeanValidator validator = new AnterosBeanValidator(this);
        if (Boolean.parseBoolean(factory.getProperties().get(
            AnterosValidatorConfiguration.Properties.TREAT_MAPS_LIKE_BEANS))) {
            validator.setTreatMapsLikeBeans(true);
        }
        return validator;
    }

    /**
     * Get the {@link MessageInterpolator}.
     * 
     * @return {@link MessageInterpolator}
     */
    public MessageInterpolator getMessageInterpolator() {
        return messageInterpolator == null ? factory.getMessageInterpolator() : messageInterpolator;
    }

    /**
     * Get the {@link TraversableResolver}.
     * 
     * @return {@link TraversableResolver}
     */
    public TraversableResolver getTraversableResolver() {
        return traversableResolver == null ? factory.getTraversableResolver() : traversableResolver;
    }

    /**
     * Create MetaBeanManager that uses factories:
     * <ol>
     * <li>if enabled by
     * {@link AnterosValidatorConfiguration.Properties#ENABLE_INTROSPECTOR}, an
     * {@link IntrospectorMetaBeanFactory}</li>
     * <li>{@link MetaBeanFactory} types (if any) specified by
     * {@link AnterosValidatorConfiguration.Properties#METABEAN_FACTORY_CLASSNAMES}
     * </li>
     * <li>if no {@link Jsr303MetaBeanFactory} has yet been specified (this
     * allows factory order customization), a {@link Jsr303MetaBeanFactory}
     * which handles both JSR303-XML and JSR303-Annotations</li>
     * <li>if enabled by
     * {@link AnterosValidatorConfiguration.Properties#ENABLE_METABEANS_XML}, an
     * {@link XMLMetaBeanFactory}</li>
     * </ol>
     * 
     * @return a new instance of MetaBeanManager with adequate MetaBeanFactories
     */
    protected MetaBeanFinder buildMetaBeanFinder() {
        List<MetaBeanFactory> builders = new ArrayList<MetaBeanFactory>();
        if (Boolean.parseBoolean(factory.getProperties().get(
            AnterosValidatorConfiguration.Properties.ENABLE_INTROSPECTOR))) {
            builders.add(new IntrospectorMetaBeanFactory());
        }
        String[] factoryClassNames =
            StringUtils.tokenizeToStringArray(
                factory.getProperties().get(AnterosValidatorConfiguration.Properties.METABEAN_FACTORY_CLASSNAMES),", ;");
        if (factoryClassNames != null) {
            for (String clsName : factoryClassNames) {
                @SuppressWarnings("unchecked")
                Class<? extends MetaBeanFactory> factoryClass = (Class<? extends MetaBeanFactory>) loadClass(clsName);
                builders.add(createMetaBeanFactory(factoryClass));
            }
        }
        boolean jsr303Found = false;
        for (MetaBeanFactory builder : builders) {
            jsr303Found |= builder instanceof Jsr303MetaBeanFactory;
        }
        if (!jsr303Found) {
            builders.add(new Jsr303MetaBeanFactory(this));
        }
        return createMetaBeanManager(builders);
    }

    /**
     * Create a {@link MetaBeanManager} using the specified builders.
     * 
     * @param builders
     *            {@link MetaBeanFactory} {@link List}
     * @return {@link MetaBeanManager}
     */
    @SuppressWarnings("deprecation")
    protected MetaBeanFinder createMetaBeanManager(List<MetaBeanFactory> builders) {
        return new MetaBeanManager(new MetaBeanBuilder(builders.toArray(new MetaBeanFactory[builders.size()])));
    }

    private <F extends MetaBeanFactory> F createMetaBeanFactory(final Class<F> cls) {
        return run(new PrivilegedAction<F>() {

            public F run() {
                try {
                    Constructor<F> c = ReflectionUtils.getMatchingAccessibleConstructor(cls, AnterosFactoryContext.this.getClass());
                    if (c != null) {
                        return c.newInstance(AnterosFactoryContext.this);
                    }
                    c = ReflectionUtils.getMatchingAccessibleConstructor(cls, getFactory().getClass());
                    if (c != null) {
                        return c.newInstance(getFactory());
                    }
                    return cls.newInstance();
                } catch (Exception e) {
                    throw new ValidationException(e);
                }
            }
        });
    }

    private static <T> T doPrivileged(final PrivilegedAction<T> action) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(action);
        } else {
            return action.run();
        }
    }

    private Class<?> loadClass(final String className) {
        ClassLoader loader = doPrivileged(SecureActions.getContextClassLoader());
        if (loader == null)
            loader = getClass().getClassLoader();

        try {
            return Class.forName(className, true, loader);
        } catch (ClassNotFoundException ex) {
            throw new ValidationException("Unable to load class: " + className, ex);
        }
    }

    private static <T> T run(PrivilegedAction<T> action) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(action);
        } else {
            return action.run();
        }
    }
}
