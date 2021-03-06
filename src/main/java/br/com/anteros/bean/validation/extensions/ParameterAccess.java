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

package br.com.anteros.bean.validation.extensions;

import java.lang.annotation.ElementType;
import java.lang.reflect.Type;

import br.com.anteros.bean.validation.util.AccessStrategy;


/**
 * Implementation of {@link AccessStrategy} for method parameters.
 *
 * @author Carlos Vara
 */
public class ParameterAccess extends AccessStrategy {

    private Type paramType;
    private int paramIdx;

    /**
     * Create a new ParameterAccess instance.
     * @param paramType
     * @param paramIdx
     */
    public ParameterAccess(Type paramType, int paramIdx ) {
        this.paramType = paramType;
        this.paramIdx = paramIdx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Object instance) {
        throw new UnsupportedOperationException("Obtaining a parameter value not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ElementType getElementType() {
        return ElementType.PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getJavaType() {
        return this.paramType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPropertyName() {
        return "" + paramIdx;
    }

}
