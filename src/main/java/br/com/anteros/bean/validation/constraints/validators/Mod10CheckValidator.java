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

import java.util.List;

import br.com.anteros.bean.validation.constraints.Mod10Check;
import br.com.anteros.core.utils.ModUtil;
import br.com.anteros.validation.api.ConstraintValidator;

/**
 * Mod10 (Luhn algorithm implementation) Check validator
 *
 * http://en.wikipedia.org/wiki/Luhn_algorithm
 * http://en.wikipedia.org/wiki/Check_digit
 *
 * @author George Gastaldi
 * @author Hardy Ferentschik
 * @author Victor Rezende dos Santos
 */
public class Mod10CheckValidator extends ModCheckBase
		implements ConstraintValidator<Mod10Check, CharSequence> {

	/**
	 * Multiplier to be used by odd digits on Mod10 algorithm
	 */
	private int multiplier;

	/**
	 * Weight to be used by even digits on Mod10 algorithm
	 */
	private int weight;


	public void initialize(Mod10Check constraintAnnotation) {
		super.initialize(
				constraintAnnotation.startIndex(),
				constraintAnnotation.endIndex(),
				constraintAnnotation.checkDigitIndex(),
				constraintAnnotation.ignoreNonDigitCharacters()
		);
		this.multiplier = constraintAnnotation.multiplier();
		this.weight = constraintAnnotation.weight();

		if ( this.multiplier < 0 ) {
			throw new IllegalArgumentException("Multiplier cannot be negative: "+multiplier);
		}
		if ( this.weight < 0 ) {
			throw new IllegalArgumentException("Weight cannot be negative: "+weight);
		}
	}

	/**
	 * Validate check digit using Mod10
	 *
	 * @param digits The digits over which to calculate the checksum
	 * @param checkDigit the check digit
	 *
	 * @return {@code true} if the mod 10 result matches the check digit, {@code false} otherwise
	 */
	@Override
	public boolean isCheckDigitValid(List<Integer> digits, char checkDigit) {
		int modResult = ModUtil.calculateMod10Check( digits, this.multiplier, this.weight );

		if ( !Character.isDigit( checkDigit ) ) {
			return false;
		}

		int checkValue = extractDigit( checkDigit );
		return checkValue == modResult;
	}
}
