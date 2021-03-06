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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import br.com.anteros.bean.validation.AnterosFactoryContext;
import br.com.anteros.bean.validation.AppendValidation;
import br.com.anteros.bean.validation.ConstraintAnnotationAttributes;
import br.com.anteros.bean.validation.Jsr303MetaBeanFactory;
import br.com.anteros.bean.validation.Validate;
import br.com.anteros.bean.validation.model.Validation;
import br.com.anteros.bean.validation.util.AccessStrategy;
import br.com.anteros.core.utils.ClassUtils;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Valid;

/**
 * Description: extension to validate parameters/return values of
 * methods/constructors.<br/>
 */
// TODO RSt - move. this is an optional module: move the whole package. core
// code has no dependencies on it
public class MethodValidatorMetaBeanFactory extends Jsr303MetaBeanFactory {
    /**
     * Create a new MethodValidatorMetaBeanFactory instance.
     * 
     * @param factoryContextO
     */
    public MethodValidatorMetaBeanFactory(AnterosFactoryContext factoryContext) {
        super(factoryContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasValidationConstraintsDefined(Method method) {
        return false;
    }

    /**
     * Finish building the specified {@link MethodBeanDescriptorImpl}.
     * 
     * @param descriptor
     */
    public void buildMethodDescriptor(MethodBeanDescriptorImpl descriptor) {
        try {
            buildMethodConstraints(descriptor);
            buildConstructorConstraints(descriptor);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private void buildConstructorConstraints(MethodBeanDescriptorImpl beanDesc) throws InvocationTargetException,
        IllegalAccessException {
        beanDesc.setConstructorConstraints(new HashMap<Constructor<?>, ConstructorDescriptor>());

        for (Constructor<?> cons : beanDesc.getMetaBean().getBeanClass().getDeclaredConstructors()) {
            if (!factoryContext.getFactory().getAnnotationIgnores().isIgnoreAnnotations(cons)) {

                ConstructorDescriptorImpl consDesc =
                    new ConstructorDescriptorImpl(beanDesc.getMetaBean(), new Validation[0]);
                beanDesc.putConstructorDescriptor(cons, consDesc);

                Annotation[][] paramsAnnos = cons.getParameterAnnotations();
                int idx = 0;
                for (Annotation[] paramAnnos : paramsAnnos) {
                    ParameterAccess access = new ParameterAccess(cons.getParameterTypes()[idx], idx);
                    processAnnotations(consDesc, paramAnnos, access, idx);
                    idx++;
                }
            }
        }
    }

    private void buildMethodConstraints(MethodBeanDescriptorImpl beanDesc) throws InvocationTargetException,
        IllegalAccessException {
        beanDesc.setMethodConstraints(new HashMap<Method, MethodDescriptor>());

        for (Method method : beanDesc.getMetaBean().getBeanClass().getDeclaredMethods()) {
            if (!factoryContext.getFactory().getAnnotationIgnores().isIgnoreAnnotations(method)) {

                MethodDescriptorImpl methodDesc = new MethodDescriptorImpl(beanDesc.getMetaBean(), new Validation[0]);
                beanDesc.putMethodDescriptor(method, methodDesc);

                // return value validations
                AppendValidationToList validations = new AppendValidationToList();
                ReturnAccess returnAccess = new ReturnAccess(method.getReturnType());
                for (Annotation anno : method.getAnnotations()) {
                    if (anno instanceof Valid || anno instanceof Validate) {
                        methodDesc.setCascaded(true);
                    } else {
                        processAnnotation(anno, methodDesc, returnAccess, validations);
                    }
                }
                methodDesc.addValidations(validations.getValidations());

                // parameter validations
                Annotation[][] paramsAnnos = method.getParameterAnnotations();
                int idx = 0;
                for (Annotation[] paramAnnos : paramsAnnos) {
                    ParameterAccess access = new ParameterAccess(method.getParameterTypes()[idx], idx);
                    processAnnotations(methodDesc, paramAnnos, access, idx);
                    idx++;
                }
            }
        }
    }

    private void processAnnotations(ProcedureDescriptor methodDesc, Annotation[] paramAnnos, AccessStrategy access,
        int idx) throws InvocationTargetException, IllegalAccessException {
        AppendValidationToList validations = new AppendValidationToList();
        boolean cascaded = false;
        for (Annotation anno : paramAnnos) {
            if (anno instanceof Valid || anno instanceof Validate) {
                cascaded = true;
            } else {
                processAnnotation(anno, methodDesc, access, validations);
            }
        }
        ParameterDescriptorImpl paramDesc =
            new ParameterDescriptorImpl(methodDesc.getMetaBean(), validations.getValidations().toArray(
                new Validation[validations.getValidations().size()]));
        paramDesc.setIndex(idx);
        paramDesc.setCascaded(cascaded);
        methodDesc.getParameterDescriptors().add(paramDesc);
    }

    private <A extends Annotation> void processAnnotation(A annotation, ProcedureDescriptor desc,
        AccessStrategy access, AppendValidation validations) throws InvocationTargetException, IllegalAccessException {

        if (annotation instanceof Valid || annotation instanceof Validate) {
            desc.setCascaded(true);
        } else {
            Constraint vcAnno = annotation.annotationType().getAnnotation(Constraint.class);
            if (vcAnno != null) {
                annotationProcessor.processAnnotation(annotation, null,
                    ClassUtils.primitiveToWrapper((Class<?>) access.getJavaType()), access, validations);
            } else {
                /**
                 * Multi-valued constraints
                 */
                if (ConstraintAnnotationAttributes.VALUE.isDeclaredOn(annotation.annotationType())) {
                    Annotation[] children = ConstraintAnnotationAttributes.VALUE.getValue(annotation);
                    if (children != null) {
                        for (Annotation child : children) {
                            processAnnotation(child, desc, access, validations); // recursion
                        }
                    }
                }
            }
        }
    }

}
