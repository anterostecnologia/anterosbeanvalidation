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

import br.com.anteros.persistence.util.AnterosPersistenceHelper;
import br.com.anteros.validation.api.Path;
import br.com.anteros.validation.api.TraversableResolver;

/** @see br.com.anteros.validation.api.TraversableResolver */
public class AnterosPersistenceTraversableResolver implements TraversableResolver, CachingRelevant {

	/**
	 * {@inheritDoc}
	 */
	public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, ElementType elementType) {
		return traversableObject == null
				|| AnterosPersistenceHelper.isLoaded(traversableObject, traversableProperty.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, ElementType elementType) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean needsCaching() {
		return true;
	}
}
