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
package br.com.anteros.bean.validation.model;

import java.lang.reflect.Type;

/**
 * Description: implementation of a dynamic type. can be used inside a
 * MetaProperty for instance-based types <br/>
 */
public interface DynaType extends Type {
    /**
     * Resolve the type indirection.
     * @return Type
     */
    Type getRawType();
}
