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

import java.util.Collection;

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
import br.com.anteros.validation.api.constraints.Size;

/** Check that a Collection's size is between min and max. */
public class SizeValidatorForCollection extends SizeValidator
      implements ConstraintValidator<Size, Collection<?>> {

    /**
     * Checks the number of entries in a map.
     *
     * @param collection The collection to validate.
     * @param context    context in which the constraint is evaluated.
     * @return Returns <code>true</code> if the collection is <code>null</code> or the number of entries in
     *         <code>collection</code> is between the specified <code>min</code> and <code>max</code> values (inclusive),
     *         <code>false</code> otherwise.
     */
    public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {
        if (collection == null) {
            return true;
        }
        int length = collection.size();
        return length >= min && length <= max;
    }

}
