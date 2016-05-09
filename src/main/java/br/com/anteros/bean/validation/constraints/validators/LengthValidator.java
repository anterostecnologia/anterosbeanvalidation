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

import br.com.anteros.bean.validation.constraints.Length;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

/**
 * Check that the character sequence length is between min and max.
 *
 * @author Emmanuel Bernard
 * @author Gavin King
 */
public class LengthValidator implements ConstraintValidator<Length, CharSequence> {

	private int min;
	private int max;

	public void initialize(Length parameters) {
		min = parameters.min();
		max = parameters.max();
		validateParameters();
	}

	public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
		if ( value == null ) {
			return true;
		}
		int length = value.length();
		return length >= min && length <= max;
	}

	private void validateParameters() {
		if ( min < 0 ) {
			throw new IllegalArgumentException("The min parameter cannot be negative.");
		}
		if ( max < 0 ) {
			throw new IllegalArgumentException("The max parameter cannot be negative.");
		}
		if ( max < min ) {
			throw new IllegalArgumentException("The length cannot be negative.");
		}
	}
}
