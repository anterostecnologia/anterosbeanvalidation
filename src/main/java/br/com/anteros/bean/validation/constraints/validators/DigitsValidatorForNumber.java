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

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
import br.com.anteros.validation.api.constraints.Digits;

/**
 * Validates that the <code>Number</code> being validates matches the pattern
 * defined in the constraint.
 */
public class DigitsValidatorForNumber implements ConstraintValidator<Digits, Number> {

    private int integral;
    private int fractional;

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getFractional() {
        return fractional;
    }

    public void setFractional(int fractional) {
        this.fractional = fractional;
    }

    public void initialize(Digits annotation) {
        this.integral = annotation.integer();
        this.fractional = annotation.fraction();
        if (integral < 0) {
            throw new IllegalArgumentException(
                  "The length of the integer part cannot be negative.");
        }
        if (fractional < 0) {
            throw new IllegalArgumentException(
                  "The length of the fraction part cannot be negative.");
        }
    }

    public boolean isValid(Number num, ConstraintValidatorContext context) {
        if (num == null) {
            return true;
        }

        BigDecimal bigDecimal;
        if (num instanceof BigDecimal) {
            bigDecimal = (BigDecimal) num;
        } else {
            bigDecimal = new BigDecimal(num.toString());
        }
        bigDecimal = bigDecimal.stripTrailingZeros();

        int intLength = bigDecimal.precision() - bigDecimal.scale();
        if (integral >= intLength) {
            int factionLength = bigDecimal.scale() < 0 ? 0 : bigDecimal.scale();
            return fractional >= factionLength;
        } else {
            return false;
        }
    }
}
