package br.com.anteros.bean.validation.messageinterpolation;

import java.util.Arrays;

import javax.validation.MessageInterpolator.Context;


public class ParameterTermResolver implements TermResolver {

	public String interpolate(Context context, String expression) {
		String resolvedExpression;
		Object variable = context.getConstraintDescriptor()
				.getAttributes()
				.get( removeCurlyBraces( expression ) );
		if ( variable != null ) {
			if ( variable.getClass().isArray() ) {
				resolvedExpression = Arrays.toString( (Object[]) variable );
			}
			else {
				resolvedExpression = variable.toString();
			}
		}
		else {
			resolvedExpression = expression;
		}
		return resolvedExpression;
	}

	private String removeCurlyBraces(String parameter) {
		return parameter.substring( 1, parameter.length() - 1 );
	}
}
