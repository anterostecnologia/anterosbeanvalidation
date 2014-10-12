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
package br.com.anteros.bean.validation.resolver;

import java.lang.annotation.ElementType;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.validation.Path;
import javax.validation.TraversableResolver;

import br.com.anteros.bean.validation.util.PrivilegedActions;
import br.com.anteros.core.log.LogLevel;
import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;
import br.com.anteros.core.utils.ClassUtils;

/** @see javax.validation.TraversableResolver */
public class DefaultTraversableResolver implements TraversableResolver, CachingRelevant {
    private static final Logger log = LoggerProvider.getInstance().getLogger(DefaultTraversableResolver.class.getName());

    /** Class to load to check whether Anteros Persistence is on the classpath. */
    private static final String ANTEROS_PERSISTENCE_CLASSNAME =
          "br.com.anteros.persistence.session.configuration.AnterosPersistenceConfiguration";

    /** Class to instantiate in case Anteros Persistence is on the classpath. */
    private static final String ANTEROS_PERSISTENCE_AWARE_TRAVERSABLE_RESOLVER_CLASSNAME =
          "br.com.anteros.bean.validation.resolver.AnterosPersistenceTraversableResolver";


    private TraversableResolver anterosPersistenceTR;

    /**
     * Create a new DefaultTraversableResolver instance.
     */
    public DefaultTraversableResolver() {
        initAnterosPersistence();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isReachable(Object traversableObject, Path.Node traversableProperty,
                               Class<?> rootBeanType, Path pathToTraversableObject,
                               ElementType elementType) {
        return anterosPersistenceTR == null || anterosPersistenceTR.isReachable(traversableObject, traversableProperty,
              rootBeanType, pathToTraversableObject, elementType);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isCascadable(Object traversableObject, Path.Node traversableProperty,
                                Class<?> rootBeanType, Path pathToTraversableObject,
                                ElementType elementType) {
        return anterosPersistenceTR == null || anterosPersistenceTR.isCascadable(traversableObject, traversableProperty,
              rootBeanType, pathToTraversableObject, elementType);
    }

    /** Tries to load detect and load Anteros Persistence. */
    @SuppressWarnings("unchecked")
    private void initAnterosPersistence() {
        final ClassLoader classLoader = getClassLoader();
        try {
            PrivilegedActions.getClass(classLoader, ANTEROS_PERSISTENCE_CLASSNAME);
            log.log(LogLevel.DEBUG, String.format("Found %s on classpath.", ANTEROS_PERSISTENCE_CLASSNAME));
        } catch (Exception e) {
            log.log(LogLevel.DEBUG, String.format("Cannot find %s on classpath. All properties will per default be traversable.", ANTEROS_PERSISTENCE_CLASSNAME));
            return;
        }

        try {
            Class<? extends TraversableResolver> anterosPersistenceAwareResolverClass =
              (Class<? extends TraversableResolver>)
                ClassUtils.getClass(classLoader, ANTEROS_PERSISTENCE_AWARE_TRAVERSABLE_RESOLVER_CLASSNAME, true);
            anterosPersistenceTR = anterosPersistenceAwareResolverClass.newInstance();
            log.log(LogLevel.DEBUG, String.format("Instantiated an instance of %s.", ANTEROS_PERSISTENCE_AWARE_TRAVERSABLE_RESOLVER_CLASSNAME));
        } catch (Exception e) {
			log.log(LogLevel.WARN,
					String.format(
							"Unable to load or instantiate Anteros Persistence aware resolver %s. All properties will per default be traversable.",
							ANTEROS_PERSISTENCE_AWARE_TRAVERSABLE_RESOLVER_CLASSNAME, e));
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean needsCaching() {
        return anterosPersistenceTR != null && CachingTraversableResolver.needsCaching(anterosPersistenceTR);
    }

    private static ClassLoader getClassLoader()
    {
      return (System.getSecurityManager() == null)
        ? getClassLoader0()
        : AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
              public ClassLoader run() {
                return getClassLoader0();
              }
          });
    }

    private static ClassLoader getClassLoader0()
    {
      final ClassLoader loader = Thread.currentThread().getContextClassLoader();
      return (loader != null) ? loader : ClassUtils.class.getClassLoader();
    }
}
