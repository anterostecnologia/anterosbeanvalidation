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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.OverridesAttribute;
import br.com.anteros.validation.api.Payload;
import br.com.anteros.validation.api.ReportAsSingleViolation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element has to represent a valid
 * credit card number. This is the Luhn algorithm implementation
 * which aims to check for user mistake, not credit card validity!
 *
 * @author Hardy Ferentschik
 * @author Emmanuel Bernard
 */
@Documented
@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@LuhnCheck
public @interface CreditCardNumber {
	String message() default "{br.com.anteros.bean.validation.constraints.CreditCardNumber.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * @return Whether non-digit characters in the validated input should be ignored ({@code true}) or result in a
	 * validation error ({@code false}). Default is {@code false}
	 */
	@OverridesAttribute(constraint = LuhnCheck.class, name = "ignoreNonDigitCharacters")
	boolean ignoreNonDigitCharacters() default false;

	/**
	 * Defines several {@code @CreditCardNumber} annotations on the same element.
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		CreditCardNumber[] value();
	}
}
