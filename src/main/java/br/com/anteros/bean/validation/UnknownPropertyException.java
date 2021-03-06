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

import br.com.anteros.validation.api.ValidationException;

/**
 * Internal exception thrown when trying to access a property that doesn't exist
 * in a bean.
 * 
 * @version $Rev: 1166451 $ $Date: 2011-09-07 19:32:26 -0300 (Qua, 07 Set 2011) $
 * 
 * @author Carlos Vara
 */
public class UnknownPropertyException extends ValidationException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new UnknownPropertyException instance.
     * @param message
     */
    public UnknownPropertyException(String message) {
        super(message);
    }

    /**
     * Create a new UnknownPropertyException instance.
     */
    public UnknownPropertyException() {
        super();
    }

    /**
     * Create a new UnknownPropertyException instance.
     * @param message
     * @param cause
     */
    public UnknownPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new UnknownPropertyException instance.
     * @param cause
     */
    public UnknownPropertyException(Throwable cause) {
        super(cause);
    }

}
