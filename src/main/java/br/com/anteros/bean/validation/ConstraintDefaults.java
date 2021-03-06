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

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import br.com.anteros.bean.validation.resource.messages.AnterosDefaultConstraints;
import br.com.anteros.core.log.LogLevel;
import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;
import br.com.anteros.validation.api.ConstraintValidator;


/**
 * Description: Provides access to the default constraints/validator implementation classes built into the framework.
 * These are configured in DefaultConstraints.properties.<br/>
 */
public class ConstraintDefaults {
    private static final Logger log = LoggerProvider.getInstance().getLogger(ConstraintDefaults.class.getName());
    
    /**
     * The default constraint data stored herein.
     */
    protected Map<String, Class<? extends ConstraintValidator<?, ?>>[]> defaultConstraints;

    /**
     * Create a new ConstraintDefaults instance.
     */
    public ConstraintDefaults() {
        defaultConstraints = loadDefaultConstraints();
    }

    /**
     * Get the default constraint data.
     * @return String-keyed map
     */
    public Map<String, Class<? extends ConstraintValidator<?, ?>>[]> getDefaultConstraints() {
        return defaultConstraints;
    }

    /**
     * Get the default validator implementation types for the specified constraint annotation type. 
     * @param annotationType
     * @return array of {@link ConstraintValidator} implementation classes
     */
    @SuppressWarnings("unchecked")
    public <A extends Annotation> Class<? extends ConstraintValidator<A, ?>>[] getValidatorClasses(
          Class<A> annotationType) {
        return (Class<? extends ConstraintValidator<A, ?>>[]) getDefaultConstraints().get(annotationType.getName());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Class<? extends ConstraintValidator<?, ?>>[]> loadDefaultConstraints() {
        Properties constraintProperties = AnterosDefaultConstraints.getConstraints();
        final ClassLoader classloader = getClassLoader();
        Map<String, Class<? extends ConstraintValidator<?, ?>>[]> loadedConstraints
                = new HashMap<String, Class<? extends ConstraintValidator<?,?>>[]>();
        for (Map.Entry<Object, Object> entry : constraintProperties.entrySet()) {

            StringTokenizer tokens = new StringTokenizer((String) entry.getValue(), ", ");
            LinkedList<Class<?>> classes = new LinkedList<Class<?>>();
            while (tokens.hasMoreTokens()) {
                final String eachClassName = tokens.nextToken();

                Class<?> constraintValidatorClass =
                      run(new PrivilegedAction<Class<?>>() {
                          public Class<?> run() {
                              try {
                                  return Class.forName(eachClassName, true, classloader);
                              } catch (ClassNotFoundException e) {
                                  log.log(LogLevel.ERROR, String.format("Cannot find class %s", eachClassName), e);
                                  return null;
                              }
                          }
                      });

                if (constraintValidatorClass != null) classes.add(constraintValidatorClass);

            }
            loadedConstraints
                  .put((String) entry.getKey(),
                        (Class<? extends ConstraintValidator<?, ?>>[]) classes.toArray(new Class[classes.size()]));

        }
        return loadedConstraints;
    }

    private ClassLoader getClassLoader() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if (classloader == null) classloader = getClass().getClassLoader();
        return classloader;
    }

    private static <T> T run(PrivilegedAction<T> action) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(action);
        } else {
            return action.run();
        }
    }
}
