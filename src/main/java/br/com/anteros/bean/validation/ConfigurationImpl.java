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


import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.anteros.bean.validation.resolver.DefaultTraversableResolver;
import br.com.anteros.bean.validation.util.SecureActions;
import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;
import br.com.anteros.validation.api.ConstraintValidatorFactory;
import br.com.anteros.validation.api.MessageInterpolator;
import br.com.anteros.validation.api.TraversableResolver;
import br.com.anteros.validation.api.ValidationException;
import br.com.anteros.validation.api.ValidationProviderResolver;
import br.com.anteros.validation.api.ValidatorFactory;
import br.com.anteros.validation.api.spi.BootstrapState;
import br.com.anteros.validation.api.spi.ConfigurationState;
import br.com.anteros.validation.api.spi.ValidationProvider;


/**
 * Description: used to configure apache-validation for jsr303.
 * Implementation of Configuration that also implements ConfigurationState,
 * hence this can be passed to buildValidatorFactory(ConfigurationState).
 * <br/>
 */
public class ConfigurationImpl implements AnterosValidatorConfiguration, ConfigurationState {
    private static final Logger log = LoggerProvider.getInstance().getLogger(ConfigurationImpl.class.getName());

    /**
     * Configured {@link ValidationProvider}
     */
    //couldn't this be parameterized <ApacheValidatorConfiguration> or <? super ApacheValidatorConfiguration>?
    protected final ValidationProvider<?> provider;

    /**
     * Configured {@link ValidationProviderResolver}
     */
    protected final ValidationProviderResolver providerResolver;

    /**
     * Configured {@link ValidationProvider} class
     */
    protected Class<? extends ValidationProvider<?>> providerClass;

    /**
     * Configured {@link MessageInterpolator}
     */
    protected MessageInterpolator messageInterpolator;

    /**
     * Configured {@link ConstraintValidatorFactory}
     */
    protected ConstraintValidatorFactory constraintValidatorFactory;

    private TraversableResolver traversableResolver;

    // BEGIN DEFAULTS
    /**
     * false = dirty flag (to prevent from multiple parsing validation.xml)
     */
    private boolean prepared = false;
    private final TraversableResolver defaultTraversableResolver =
          new DefaultTraversableResolver();

    /**
     * Default {@link MessageInterpolator}
     */
    protected final MessageInterpolator defaultMessageInterpolator =
          new DefaultMessageInterpolator();

    private final ConstraintValidatorFactory defaultConstraintValidatorFactory =
          new DefaultConstraintValidatorFactory();
    // END DEFAULTS

    private Set<InputStream> mappingStreams = new HashSet<InputStream>();
    private Map<String, String> properties = new HashMap<String, String>();
    private boolean ignoreXmlConfiguration = false;

    /**
     * Create a new ConfigurationImpl instance.
     * @param aState
     * @param aProvider
     */
    public ConfigurationImpl(BootstrapState aState, ValidationProvider<?> aProvider) {
        if (aProvider != null) {
            this.provider = aProvider;
            this.providerResolver = null;
        } else if (aState != null) {
            this.provider = null;
            if (aState.getValidationProviderResolver() == null) {
                providerResolver = aState.getDefaultValidationProviderResolver();
            } else {
                providerResolver = aState.getValidationProviderResolver();
            }
        } else {
            throw new ValidationException("either provider or state are required");
        }
    }

    /**
     * {@inheritDoc}
     */
    public AnterosValidatorConfiguration traversableResolver(TraversableResolver resolver) {
        traversableResolver = resolver;
        this.prepared = false;
        return this;
    }

    /**
     * {@inheritDoc}
     * Ignore data from the <i>META-INF/validation.xml</i> file if this
     * method is called.
     *
     * @return this
     */
    public AnterosValidatorConfiguration ignoreXmlConfiguration() {
        ignoreXmlConfiguration = true;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public ConfigurationImpl messageInterpolator(MessageInterpolator resolver) {
        this.messageInterpolator = resolver;
        this.prepared = false;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public ConfigurationImpl constraintValidatorFactory(
          ConstraintValidatorFactory constraintFactory) {
        this.constraintValidatorFactory = constraintFactory;
        this.prepared = false;
        return this;
    }

    /**
     * {@inheritDoc}
     * Add a stream describing constraint mapping in the Bean Validation
     * XML format.
     *
     * @return this
     */
    public AnterosValidatorConfiguration addMapping(InputStream stream) {
        mappingStreams.add(stream);
        return this;
    }

    /**
     * {@inheritDoc}
     * Add a provider specific property. This property is equivalent to
     * XML configuration properties.
     * If we do not know how to handle the property, we silently ignore it.
     *
     * @return this
     */
    public AnterosValidatorConfiguration addProperty(String name, String value) {
        properties.put(name, value);
        return this;
    }

    /**
     * {@inheritDoc}
     * Return a map of non type-safe custom properties.
     *
     * @return null
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * {@inheritDoc}
     * Returns true if Configuration.ignoreXMLConfiguration() has been called.
     * In this case, we ignore META-INF/validation.xml
     *
     * @return true
     */
    public boolean isIgnoreXmlConfiguration() {
        return ignoreXmlConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    public Set<InputStream> getMappingStreams() {
        return mappingStreams;
    }

    /**
     * {@inheritDoc}
     */
    public MessageInterpolator getMessageInterpolator() {
        return messageInterpolator;
    }

    /**
     * {@inheritDoc}
     */
    public MessageInterpolator getDefaultMessageInterpolator() {
        return defaultMessageInterpolator;
    }

    /**
     * {@inheritDoc}
     */
    public TraversableResolver getDefaultTraversableResolver() {
        return defaultTraversableResolver;
    }

    /**
     * {@inheritDoc}
     */
    public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
        return defaultConstraintValidatorFactory;
    }

    /**
     * {@inheritDoc}
     * main factory method to build a ValidatorFactory
     *
     * @throws ValidationException if the ValidatorFactory cannot be built
     */
    public ValidatorFactory buildValidatorFactory() {
        return run(SecureActions.doPrivBuildValidatorFactory(this));
    }

    public ValidatorFactory doPrivBuildValidatorFactory() {
        prepare();
        if (provider != null) {
            return provider.buildValidatorFactory(this);
        } else {
            return findProvider().buildValidatorFactory(this);
        }
    }

    private void prepare() {
        if (prepared) return;
        parseValidationXml();
        applyDefaults();
        prepared = true;
    }

    /** Check whether a validation.xml file exists and parses it with JAXB */
    private void parseValidationXml() {
        if (isIgnoreXmlConfiguration()) {
            log.debug("ignoreXmlConfiguration == true");
        }
    }

    private void applyDefaults() {
        // make sure we use the defaults in case they haven't been provided yet
        if (traversableResolver == null) {
            traversableResolver = getDefaultTraversableResolver();
        }
        if (messageInterpolator == null) {
            messageInterpolator = getDefaultMessageInterpolator();
        }
        if (constraintValidatorFactory == null) {
            constraintValidatorFactory = getDefaultConstraintValidatorFactory();
        }
    }

    /**
     * {@inheritDoc}
     * @return the constraint validator factory of this configuration.
     */
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return constraintValidatorFactory;
    }

    /**
     * {@inheritDoc}
     */
    public TraversableResolver getTraversableResolver() {
        return traversableResolver;
    }

    /**
     * Get the configured {@link ValidationProvider}.
     * @return {@link ValidationProvider}
     */
    public ValidationProvider<?> getProvider() {
        return provider;
    }

    private ValidationProvider<?> findProvider() {
        if (providerClass != null) {
            for (ValidationProvider<?> provider : providerResolver
                  .getValidationProviders()) {
                if (providerClass.isAssignableFrom(provider.getClass())) {
                    return provider;
                }
            }
            throw new ValidationException(
                  "Unable to find suitable provider: " + providerClass);
        } else {
            List<ValidationProvider<?>> providers = providerResolver.getValidationProviders();
            return providers.get(0);
        }
    }

    /**
     * Set {@link ValidationProvider} class.
     * @param providerClass
     */
    public void setProviderClass(Class<? extends ValidationProvider<?>> providerClass) {
        this.providerClass = providerClass;
    }

    private static <T> T run(PrivilegedAction<T> action) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(action);
        } else {
            return action.run();
        }
    }
}
