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
package br.com.anteros.bean.validation.constraints;

import br.com.anteros.bean.validation.constraints.validators.NotEmptyValidator;
import br.com.anteros.bean.validation.constraints.validators.NotEmptyValidatorForCollection;
import br.com.anteros.bean.validation.constraints.validators.NotEmptyValidatorForMap;
import br.com.anteros.bean.validation.constraints.validators.NotEmptyValidatorForString;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <pre>
 * This class is NOT part of the bean_validation spec and might disappear
 * as soon as a final version of the specification contains a similar functionality.
 * </pre>
 */
@Documented
@Constraint(
      validatedBy = {NotEmptyValidatorForCollection.class, NotEmptyValidatorForMap.class,
            NotEmptyValidatorForString.class, NotEmptyValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface NotEmpty {
    Class<?>[] groups() default {};

    String message() default "{br.com.anteros.bean.validation.constraints.NotEmpty.message}";

    Class<? extends Payload>[] payload() default {};
}
