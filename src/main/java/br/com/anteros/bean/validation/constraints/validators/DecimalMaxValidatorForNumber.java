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

import java.math.BigDecimal;
import java.math.BigInteger;

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
import br.com.anteros.validation.api.constraints.DecimalMax;

/** Description: validate that number-value of passed object is <= maxvalue<br/> */
public class DecimalMaxValidatorForNumber
      implements ConstraintValidator<DecimalMax, Number> {

    private BigDecimal maxValue;

    public void initialize(DecimalMax annotation) {
        try {
            this.maxValue = new BigDecimal(annotation.value());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(
                  annotation.value() + " does not represent a valid BigDecimal format");
        }
    }

    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(maxValue) != 1;
        } else if (value instanceof BigInteger) {
            return (new BigDecimal((BigInteger) value)).compareTo(maxValue) != 1;
        } else {
            return (new BigDecimal(value.doubleValue()).compareTo(maxValue)) != 1;
        }
    }           
}
