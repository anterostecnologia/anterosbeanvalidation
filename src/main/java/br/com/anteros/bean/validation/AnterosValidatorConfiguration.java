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

import br.com.anteros.validation.api.Configuration;
import br.com.anteros.validation.api.ValidatorContext;
import br.com.anteros.validation.api.ValidatorFactory;
import br.com.anteros.validation.api.spi.ConfigurationState;

/**
 * Description: Uniquely identify Apache BVal in the Bean Validation bootstrap
 * strategy. Also contains Apache BVal specific configurations<br/>
 */
public interface AnterosValidatorConfiguration
      extends Configuration<AnterosValidatorConfiguration> {

    /**
     * Proprietary property keys for {@link ConfigurationImpl}  
     */
    public interface Properties {
        /**
         * the location where to look for the validation.xml file.     
         * default: "META-INF/validation.xml"
         */
        String VALIDATION_XML_PATH = "apache.bval.validation-xml-path";

        /**
         * true/false. use Introspector (java beans) metadata additionally
         * to build metadata with JSR303.<br>
         * This means that all properties exist that are java-bean properties and
         * and that some features (Hidden, Readonly) are taken from Introspector
         * to create the meta data.<br>
         * default: false
         */
        String ENABLE_INTROSPECTOR = "apache.bval.enable-introspector";


        /**
         * BeanValidator.treatMapsLikeBeans.
         * default: false
         */
        String TREAT_MAPS_LIKE_BEANS = "apache.bval.treat-maps-like-beans";

        /**
         * Specifies the classname of the {@link ValidatorFactory} to use: this
         * class is presumed have a constructor that accepts a single
         * {@link ConfigurationState} argument.
         */
         String VALIDATOR_FACTORY_CLASSNAME = "apache.bval.validator-factory-classname";

        /**
         * Specifies the names, delimited by whitespace, of
         * {@link MetaBeanFactory} classes that should be added to collaborate
         * with an {@link AnterosFactoryContext}'s {@link MetaBeanFinder}. These
         * are instantiated per {@link ValidatorContext}, attempting to use
         * constructor arguments of decreasing specificity:
         * <ol>
         * <li>assignable from the creating {@link AnterosFactoryContext}</li>
         * <li>assignable from the associated {@link AnterosValidatorFactory}</li>
         * <li>default (no-args) constructor</li>
         * </ol>
         */
         String METABEAN_FACTORY_CLASSNAMES = "apache.bval.metabean-factory-classnames";
    }
}
