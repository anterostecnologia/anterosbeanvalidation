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

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
import br.com.anteros.validation.api.constraints.AssertFalse;

/**
 * Description: assert that value is false<br/>
 */
public class AssertFalseValidator implements ConstraintValidator<AssertFalse, Boolean> {

    public void initialize(AssertFalse annotation) {
    }

    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value == null || !value;
    }

}