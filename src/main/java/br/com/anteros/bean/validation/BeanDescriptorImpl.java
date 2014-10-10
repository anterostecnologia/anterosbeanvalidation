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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import br.com.anteros.bean.validation.model.Features;
import br.com.anteros.bean.validation.model.MetaBean;
import br.com.anteros.bean.validation.model.MetaProperty;

/**
 * Description: Implements {@link BeanDescriptor}.<br/>
 */
public class BeanDescriptorImpl extends ElementDescriptorImpl implements BeanDescriptor {
    /**
     * The {@link AnterosFactoryContext} (not) used by this
     * {@link BeanDescriptorImpl}
     */
    protected final AnterosFactoryContext factoryContext;

    /**
     * Create a new BeanDescriptorImpl instance.
     * 
     * @param factoryContext
     * @param metaBean
     */
    protected BeanDescriptorImpl(AnterosFactoryContext factoryContext, MetaBean metaBean) {
        super(metaBean, metaBean.getBeanClass(), metaBean.getValidations());
        this.factoryContext = factoryContext;
    }

    /**
     * Returns true if the bean involves validation:
     * <ul>
     * <li>a constraint is hosted on the bean itself</li>
     * <li>a constraint is hosted on one of the bean properties, OR</li>
     * <li>a bean property is marked for cascade (<code>@Valid</code>)</li>
     * </ul>
     * 
     * @return true if the bean involves validation
     */
    public boolean isBeanConstrained() {
        if (hasAnyConstraints())
            return true;
        for (MetaProperty mprop : metaBean.getProperties()) {
            if (mprop.getMetaBean() != null || mprop.getFeature(Features.Property.REF_CASCADE) != null)
                return true;
        }
        return false;
    }

    private boolean hasAnyConstraints() {
        if (hasConstraints())
            return true;
        for (MetaProperty mprop : metaBean.getProperties()) {
            if (getConstraintDescriptors(mprop.getValidations()).size() > 0)
                return true;
        }
        return false;
    }

    /**
     * Return the property level constraints for a given propertyName or {@code null} if
     * either the property does not exist or has no constraint. The returned
     * object (and associated objects including ConstraintDescriptors) are
     * immutable.
     * 
     * @param propertyName
     *            property evaluated
     */
    public PropertyDescriptor getConstraintsForProperty(String propertyName) {
        if (propertyName == null || propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("propertyName cannot be null or empty");
        }
        MetaProperty prop = metaBean.getProperty(propertyName);
        if (prop == null)
            return null;
        // If no constraints and not cascaded, return null
        if (prop.getValidations().length == 0 && prop.getFeature(Features.Property.REF_CASCADE) == null) {
            return null;
        }
        return getPropertyDescriptor(prop);
    }

    private PropertyDescriptor getPropertyDescriptor(MetaProperty prop) {
        PropertyDescriptorImpl edesc = prop.getFeature(Jsr303Features.Property.PropertyDescriptor);
        if (edesc == null) {
            edesc = new PropertyDescriptorImpl(prop);
            prop.putFeature(Jsr303Features.Property.PropertyDescriptor, edesc);
        }
        return edesc;
    }

    /**
     * {@inheritDoc}
     * 
     * @return the property descriptors having at least a constraint defined
     */
    public Set<PropertyDescriptor> getConstrainedProperties() {
        Set<PropertyDescriptor> validatedProperties = new HashSet<PropertyDescriptor>();
        for (MetaProperty prop : metaBean.getProperties()) {
            if (prop.getValidations().length > 0
                || (prop.getMetaBean() != null || prop.getFeature(Features.Property.REF_CASCADE) != null)) {
                validatedProperties.add(getPropertyDescriptor(prop));
            }
        }
        return Collections.unmodifiableSet(validatedProperties);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "BeanDescriptorImpl{" + "returnType=" + elementClass + '}';
    }
}
