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
package br.com.anteros.bean.validation.messageinterpolation;

import java.lang.reflect.Method;
import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.ValueExpression;
import javax.el.VariableMapper;


public class SimpleELContext extends ELContext {
	private static final ELResolver DEFAULT_RESOLVER = new CompositeELResolver() {
		{
			add( new RootResolver() );
			add( new ArrayELResolver( false ) );
			add( new ListELResolver( false ) );
			add( new MapELResolver( false ) );
			add( new ResourceBundleELResolver() );
			add( new BeanELResolver( false ) );
		}
	};

	private final MapBasedFunctionMapper functions;
	private final VariableMapper variableMapper;
	private final ELResolver resolver;

	public SimpleELContext() {
		functions = new MapBasedFunctionMapper();
		variableMapper = new MapBasedVariableMapper();
		resolver = DEFAULT_RESOLVER;
	}

	@Override
	public ELResolver getELResolver() {
		return resolver;
	}

	@Override
	public MapBasedFunctionMapper getFunctionMapper() {
		return functions;
	}

	@Override
	public VariableMapper getVariableMapper() {
		return variableMapper;
	}

	public ValueExpression setVariable(String name, ValueExpression expression) {
		return variableMapper.setVariable( name, expression );
	}

	public void setFunction(String prefix, String localName, Method method) {
		functions.setFunction( prefix, localName, method );
	}
}


