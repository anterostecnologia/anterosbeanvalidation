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
package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.NotEmpty;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

import java.util.Collection;

/**
 * Description: <br/>
 */
public class NotEmptyValidatorForCollection implements ConstraintValidator<NotEmpty, Collection<?>> {
    public void initialize(NotEmpty constraintAnnotation) {
        // do nothing
    }

    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
    	if ( value == null ) {
			return false;
		}
		return value.size() > 0;
    }
}
