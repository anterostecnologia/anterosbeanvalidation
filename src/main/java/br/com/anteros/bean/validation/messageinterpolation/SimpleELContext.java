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


