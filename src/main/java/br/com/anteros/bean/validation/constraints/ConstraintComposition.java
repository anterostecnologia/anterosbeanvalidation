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

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Boolean operator that is applied to all constraints of a composing constraint annotation.
 * <p>
 * A composed constraint annotation can define a boolean combination of the constraints composing it,
 * by using {@code @ConstraintComposition}.
 * </p>
 * @author Dag Hovland
 * @author Federico Mancini
 */
@Documented
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ConstraintComposition {
	/**
	 * The value of this element specifies the boolean operator,
	 * namely disjunction (OR), negation of the conjunction (ALL_FALSE),
	 * or, the default, simple conjunction (AND).
	 *
	 * @return the {@code CompositionType} value
	 */
	CompositionType value() default CompositionType.AND;
}
