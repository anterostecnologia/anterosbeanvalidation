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
package br.com.anteros.bean.validation;

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorFactory;
import br.com.anteros.validation.api.ValidationException;


/**
 * Description: create constraint instances with the default / no-arg constructor <br/>
 */
public class DefaultConstraintValidatorFactory implements ConstraintValidatorFactory {

    /**
     * Instantiate a Constraint.
     *
     * @return Returns a new Constraint instance
     *         The ConstraintFactory is <b>not</b> responsible for calling Constraint#initialize
     */
    public <T extends ConstraintValidator<?, ?>> T getInstance(final Class<T> constraintClass)
    {
      // 2011-03-27 jw: Do not use PrivilegedAction.
      // Otherwise any user code would be executed with the privileges of this class.
      try
      {
        return constraintClass.newInstance();
      }
      catch (final Exception ex)
      {
        throw new ValidationException("Cannot instantiate : " + constraintClass, ex);
      }
    }
}
