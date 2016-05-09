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

import java.security.AccessController;
import java.security.PrivilegedAction;

import br.com.anteros.core.utils.ClassUtils;
import br.com.anteros.validation.api.Configuration;
import br.com.anteros.validation.api.ValidationException;
import br.com.anteros.validation.api.ValidatorFactory;
import br.com.anteros.validation.api.spi.BootstrapState;
import br.com.anteros.validation.api.spi.ConfigurationState;
import br.com.anteros.validation.api.spi.ValidationProvider;

/**
 * Description: Implementation of {@link ValidationProvider} for jsr303
 * implementation of the apache-validation framework.
 * <p/>
 * <br/>
 * User: roman.stumm <br/>
 * Date: 29.10.2008 <br/>
 * Time: 14:45:41 <br/>
 */
public class AnterosValidationProvider implements ValidationProvider<AnterosValidatorConfiguration> {

    /**
     * Learn whether a particular builder class is suitable for this
     * {@link ValidationProvider}.
     * 
     * @param builderClass
     * @return boolean suitability
     */
    public boolean isSuitable(Class<? extends Configuration<?>> builderClass) {
        return AnterosValidatorConfiguration.class.equals(builderClass);
    }

    /**
     * {@inheritDoc}
     */
    public AnterosValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
        return new ConfigurationImpl(state, this);
    }

    /**
     * {@inheritDoc}
     */
    public Configuration<?> createGenericConfiguration(BootstrapState state) {
        return new ConfigurationImpl(state, null);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws br.com.anteros.validation.api.ValidationException
     *             if the ValidatorFactory cannot be built
     */
    public ValidatorFactory buildValidatorFactory(final ConfigurationState configuration) {
        final Class<? extends ValidatorFactory> validatorFactoryClass;
        try {
            String validatorFactoryClassname =
                configuration.getProperties().get(AnterosValidatorConfiguration.Properties.VALIDATOR_FACTORY_CLASSNAME);

            if (validatorFactoryClassname == null) {
                validatorFactoryClass = AnterosValidatorFactory.class;
            } else {
                validatorFactoryClass =
                    ClassUtils.getClass(validatorFactoryClassname).asSubclass(ValidatorFactory.class);
            }
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ValidationException("error building ValidatorFactory", ex);
        }

        return (System.getSecurityManager() == null)
            ? instantiateValidatorFactory(validatorFactoryClass, configuration) : AccessController
                .doPrivileged(new PrivilegedAction<ValidatorFactory>() {
                    public ValidatorFactory run() {
                        return instantiateValidatorFactory(validatorFactoryClass, configuration);
                    }
                });
    }

    private static ValidatorFactory instantiateValidatorFactory(
        final Class<? extends ValidatorFactory> validatorFactoryClass, final ConfigurationState configuration) {
        try {
            return validatorFactoryClass.getConstructor(ConfigurationState.class).newInstance(configuration);
        } catch (final Exception ex) {
            throw new ValidationException("Cannot instantiate : " + validatorFactoryClass, ex);
        }
    }

}
