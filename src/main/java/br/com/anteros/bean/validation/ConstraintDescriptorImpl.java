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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.anteros.validation.api.Payload;
import br.com.anteros.validation.api.metadata.ConstraintDescriptor;

/**
 * Description: immutable, serializable implementation of ConstraintDescriptor
 * interface of JSR303<br>
 * User: roman.stumm<br>
 * Date: 22.04.2010<br>
 * Time: 10:21:23<br>
 */
public class ConstraintDescriptorImpl<T extends Annotation> implements ConstraintDescriptor<T>, Serializable {
    /** Serialization version */
    private static final long serialVersionUID = 1L;

    private final T annotation;
    private final Set<Class<?>> groups;
    private final Set<Class<? extends br.com.anteros.validation.api.Payload>> payload;
    private final List<java.lang.Class<? extends br.com.anteros.validation.api.ConstraintValidator<T, ?>>> constraintValidatorClasses;
    private final Map<String, Object> attributes;
    private final Set<ConstraintDescriptor<?>> composingConstraints;
    private final boolean reportAsSingleViolation;

    /**
     * Create a new ConstraintDescriptorImpl instance.
     * 
     * @param descriptor
     */
    public ConstraintDescriptorImpl(ConstraintDescriptor<T> descriptor) {
        this(descriptor.getAnnotation(), descriptor.getGroups(), descriptor.getPayload(), descriptor
            .getConstraintValidatorClasses(), descriptor.getAttributes(), descriptor.getComposingConstraints(),
            descriptor.isReportAsSingleViolation());
    }

    /**
     * Create a new ConstraintDescriptorImpl instance.
     * 
     * @param annotation
     * @param groups
     * @param payload
     * @param constraintValidatorClasses
     * @param attributes
     * @param composingConstraints
     * @param reportAsSingleViolation
     */
    public ConstraintDescriptorImpl(T annotation, Set<Class<?>> groups,
        Set<Class<? extends br.com.anteros.validation.api.Payload>> payload,
        List<java.lang.Class<? extends br.com.anteros.validation.api.ConstraintValidator<T, ?>>> constraintValidatorClasses,
        Map<String, Object> attributes, Set<ConstraintDescriptor<?>> composingConstraints,
        boolean reportAsSingleViolation) {
        this.annotation = annotation;
        this.groups = groups;
        this.payload = payload;
        this.constraintValidatorClasses = constraintValidatorClasses;
        this.attributes = attributes;
        this.composingConstraints = composingConstraints;
        this.reportAsSingleViolation = reportAsSingleViolation;
    }

    /**
     * {@inheritDoc}
     */
    public T getAnnotation() {
        return annotation;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Class<?>> getGroups() {
        return groups;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Class<? extends Payload>> getPayload() {
        return payload;
    }

    /**
     * {@inheritDoc}
     */
    public List<java.lang.Class<? extends br.com.anteros.validation.api.ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
        return constraintValidatorClasses;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * {@inheritDoc}
     */
    public Set<ConstraintDescriptor<?>> getComposingConstraints() {
        return composingConstraints;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isReportAsSingleViolation() {
        return reportAsSingleViolation;
    }
}
