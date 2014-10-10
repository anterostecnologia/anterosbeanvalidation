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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.anteros.bean.validation.ConstraintValidation;
import br.com.anteros.bean.validation.ElementDescriptorImpl;
import br.com.anteros.bean.validation.model.MetaBean;
import br.com.anteros.bean.validation.model.Validation;

/**
 * Description: {@link MethodDescriptor} implementation.<br/>
 */
public class MethodDescriptorImpl extends ElementDescriptorImpl implements MethodDescriptor, ProcedureDescriptor {
    private final List<ParameterDescriptor> parameterDescriptors = new ArrayList<ParameterDescriptor>();
    private boolean cascaded;

    /**
     * Create a new MethodDescriptorImpl instance.
     * 
     * @param metaBean
     * @param validations
     */
    protected MethodDescriptorImpl(MetaBean metaBean, Validation[] validations) {
        super(metaBean, metaBean.getClass(), validations);
    }

    /**
     * Create a new MethodDescriptorImpl instance.
     * 
     * @param elementClass
     * @param validations
     */
    protected MethodDescriptorImpl(Class<?> elementClass, Validation[] validations) {
        super(elementClass, validations);
    }

    /**
     * {@inheritDoc}
     */
    public List<ParameterDescriptor> getParameterDescriptors() {
        // index aligned
        return parameterDescriptors;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isCascaded() {
        return cascaded;
    }

    /**
     * {@inheritDoc}
     */
    public void setCascaded(boolean cascaded) {
        this.cascaded = cascaded;
    }

    /**
     * Add the specified validations to this {@link MethodDescriptorImpl}.
     * @param validations
     */
    void addValidations(Collection<ConstraintValidation<?>> validations) {
        getMutableConstraintDescriptors().addAll(validations);
    }
}
