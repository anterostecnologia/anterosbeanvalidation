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

import java.util.Calendar;

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
import br.com.anteros.validation.api.constraints.Past;

/**
 * Description: validate a date or calendar representing a date in the past<br/>
 */
public class PastValidatorForCalendar implements ConstraintValidator<Past, Calendar> {

    public void initialize(Past annotation) {
    }

    public boolean isValid(Calendar cal, ConstraintValidatorContext context) {
        return cal == null || cal.before(now());
    }


    /**
     * overwrite when you need a different algorithm for 'now'.
     *
     * @return current date/time
     */
    protected Calendar now() {
        return Calendar.getInstance();
    }
}
