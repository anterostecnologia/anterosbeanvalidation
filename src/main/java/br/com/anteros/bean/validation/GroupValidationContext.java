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


import java.util.Map;

import br.com.anteros.bean.validation.groups.Group;
import br.com.anteros.bean.validation.groups.Groups;
import br.com.anteros.bean.validation.model.MetaBean;
import br.com.anteros.bean.validation.model.ValidationContext;
import br.com.anteros.bean.validation.util.PathImpl;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.MessageInterpolator;
import br.com.anteros.validation.api.TraversableResolver;

/**
 * Description: JSR-303 {@link ValidationContext} extension. <br/>
 */
public interface GroupValidationContext<T>
      extends ValidationContext<ConstraintValidationListener<T>> {
    
    /**
     * Get the groups of this {@link GroupValidationContext}.
     * @return the groups in their sequence for validation
     */
    Groups getGroups();

    /**
     * Set the current {@link Group}.
     * @param group to set
     */
    void setCurrentGroup(Group group);

    /**
     * Get the current {@link Group}.
     * @return Group
     */
    Group getCurrentGroup();

    /**
     * Get the property path.
     * @return {@link PathImpl}
     */
    PathImpl getPropertyPath();

    /**
     * Get the root {@link MetaBean}.
     * @return {@link MetaBean}
     */
    MetaBean getRootMetaBean();

    /**
     * Set the {@link ConstraintValidation}.
     * @param constraint to set
     */
    void setConstraintValidation(ConstraintValidation<?> constraint);

    /**
     * Get the {@link ConstraintValidation}.
     * @return {@link ConstraintValidation}
     */
    ConstraintValidation<?> getConstraintValidation();

    /**
     * Get the value being validated.
     * @return Object
     */
    Object getValidatedValue();

    /**
     * Set a fixed value for the context.
     * @param value to set
     */
    void setFixedValue(Object value);

    /**
     * Get the message resolver.
     * @return {@link MessageInterpolator}
     */
    MessageInterpolator getMessageResolver();

    /**
     * Get the {@link TraversableResolver}.
     * @return {@link TraversableResolver}
     */
    TraversableResolver getTraversableResolver();

    /**
     * Accumulate a validated constraint.
     * @param constraint
     * @return true when the constraint for the object in this path was not
     *         already validated in this context
     */
    boolean collectValidated(ConstraintValidator<?, ?> constraint);

    /**
     * Get the current owning class.
     * @return Class
     */
    Class<?> getCurrentOwner();

    /**
     * Set the current owning class.
     * @param currentOwner to set
     */
    void setCurrentOwner(Class<?> currentOwner);
    
    Map<String, Object> getMessageParameters();
    
    void setMessageParameter(String name, Object value);

}
