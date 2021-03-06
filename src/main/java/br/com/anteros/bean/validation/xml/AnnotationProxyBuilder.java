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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

import br.com.anteros.bean.validation.ConstraintAnnotationAttributes;
import br.com.anteros.bean.validation.util.SecureActions;
import br.com.anteros.validation.api.Payload;
import br.com.anteros.validation.api.ValidationException;

/**
 * Description: Holds the information and creates an annotation proxy during xml
 * parsing of validation mapping constraints. <br/>
 */
// TODO move this guy up to org.apache.bval.jsr303 or
// org.apache.bval.jsr303.model
final public class AnnotationProxyBuilder<A extends Annotation> {
    private final Class<A> type;
    private final Map<String, Object> elements = new HashMap<String, Object>();

    /**
     * Create a new AnnotationProxyBuilder instance.
     *
     * @param annotationType
     */
    public AnnotationProxyBuilder(Class<A> annotationType) {
        this.type = annotationType;
    }

    /**
     * Create a new AnnotationProxyBuilder instance.
     *
     * @param annotationType
     * @param elements
     */
    public AnnotationProxyBuilder(Class<A> annotationType, Map<String, Object> elements) {
        this(annotationType);
        for (Map.Entry<String, Object> entry : elements.entrySet()) {
            this.elements.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Create a builder initially configured to create an annotation equivalent
     * to <code>annot</code>.
     * 
     * @param annot Annotation to be replicated.
     */
    @SuppressWarnings("unchecked")
    public AnnotationProxyBuilder(A annot) {
        this((Class<A>) annot.annotationType());
        // Obtain the "elements" of the annotation
        final Method[] methods = doPrivileged(SecureActions.getDeclaredMethods(annot.annotationType()));
        for (Method m : methods) {
            if (!m.isAccessible()) {
                m.setAccessible(true);
            }
            try {
                Object value = m.invoke(annot);
                this.elements.put(m.getName(), value);
            } catch (IllegalArgumentException e) {
                // No args, so should not happen
                throw new ValidationException("Cannot access annotation " + annot + " element: " + m.getName());
            } catch (IllegalAccessException e) {
                throw new ValidationException("Cannot access annotation " + annot + " element: " + m.getName());
            } catch (InvocationTargetException e) {
                throw new ValidationException("Cannot access annotation " + annot + " element: " + m.getName());
            }
        }
    }

    /**
     * Add an element to the configuration.
     *
     * @param elementName
     * @param value
     */
    public void putValue(String elementName, Object value) {
        elements.put(elementName, value);
    }

    /**
     * Get the specified element value from the current configuration.
     *
     * @param elementName
     * @return Object value
     */
    public Object getValue(String elementName) {
        return elements.get(elementName);
    }

    /**
     * Learn whether a given element has been configured.
     *
     * @param elementName
     * @return <code>true</code> if an <code>elementName</code> element is found
     *         on this annotation
     */
    public boolean contains(String elementName) {
        return elements.containsKey(elementName);
    }

    /**
     * Get the number of configured elements.
     *
     * @return int
     */
    public int size() {
        return elements.size();
    }

    /**
     * Get the configured Annotation type.
     *
     * @return Class<A>
     */
    public Class<A> getType() {
        return type;
    }

    /**
     * Configure the well-known JSR303 "message" element.
     *
     * @param message
     */
    public void setMessage(String message) {
        ConstraintAnnotationAttributes.MESSAGE.put(elements, message);
    }

    /**
     * Configure the well-known JSR303 "groups" element.
     *
     * @param groups
     */
    public void setGroups(Class<?>[] groups) {
        ConstraintAnnotationAttributes.GROUPS.put(elements, groups);
    }

    /**
     * Configure the well-known JSR303 "payload" element.
     * 
     * @param payload
     */
    public void setPayload(Class<? extends Payload>[] payload) {
        ConstraintAnnotationAttributes.PAYLOAD.put(elements, payload);
    }

    /**
     * Create the annotation represented by this builder.
     *
     * @return {@link Annotation}
     */
    public A createAnnotation() {
        ClassLoader classLoader = SecureActions.getClassLoader(getType());
        @SuppressWarnings("unchecked")
        final Class<A> proxyClass = (Class<A>) Proxy.getProxyClass(classLoader, getType());
        final InvocationHandler handler = new AnnotationProxy(this);
        return doPrivileged(new PrivilegedAction<A>() {
            public A run() {
                try {
                    Constructor<A> constructor = proxyClass.getConstructor(InvocationHandler.class);
                    return constructor.newInstance(handler);
                } catch (Exception e) {
                    throw new ValidationException("Unable to create annotation for configured constraint", e);
                }
            }
        });
    }

    private static <T> T doPrivileged(final PrivilegedAction<T> action) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(action);
        } else {
            return action.run();
        }
    }
}
