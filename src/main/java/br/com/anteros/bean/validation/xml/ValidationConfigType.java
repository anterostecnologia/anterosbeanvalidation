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
package br.com.anteros.bean.validation.xml;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "anteros-validation-configuration")
public class ValidationConfigType {

    @Element(name = "default-provider")
    protected String defaultProvider;
    @Element(name = "message-interpolator")
    protected String messageInterpolator;
    @Element(name = "traversable-resolver")
    protected String traversableResolver;
    @Element(name = "constraint-validator-factory")
    protected String constraintValidatorFactory;
    @Element(name = "constraint-mapping")
    protected List<String> constraintMapping;
    
    @Element
    protected List<PropertyType> property;

    /**
     * Gets the value of the defaultProvider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultProvider() {
        return defaultProvider;
    }

    /**
     * Sets the value of the defaultProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultProvider(String value) {
        this.defaultProvider = value;
    }

    /**
     * Gets the value of the messageInterpolator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageInterpolator() {
        return messageInterpolator;
    }

    /**
     * Sets the value of the messageInterpolator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageInterpolator(String value) {
        this.messageInterpolator = value;
    }

    /**
     * Gets the value of the traversableResolver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraversableResolver() {
        return traversableResolver;
    }

    /**
     * Sets the value of the traversableResolver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraversableResolver(String value) {
        this.traversableResolver = value;
    }

    /**
     * Gets the value of the constraintValidatorFactory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstraintValidatorFactory() {
        return constraintValidatorFactory;
    }

    /**
     * Sets the value of the constraintValidatorFactory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstraintValidatorFactory(String value) {
        this.constraintValidatorFactory = value;
    }

    /**
     * Gets the value of the constraintMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraintMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraintMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getConstraintMapping() {
        if (constraintMapping == null) {
            constraintMapping = new ArrayList<String>();
        }
        return this.constraintMapping;
    }

    /**
     * Gets the value of the property property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyType }
     * 
     * 
     */
    public List<PropertyType> getProperty() {
        if (property == null) {
            property = new ArrayList<PropertyType>();
        }
        return this.property;
    }

}
