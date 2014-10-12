package br.com.anteros.bean.validation.messageinterpolation;

import java.util.Locale;
import java.util.Map;

import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.validation.MessageInterpolator;

import br.com.anteros.bean.validation.UnknownPropertyException;
import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

/**
 * Resolver for the el expressions.
 * 
 */
public class ElTermResolver implements TermResolver {
	private static final Logger log = LoggerProvider.getInstance().getLogger(ElTermResolver.class);

	/**
	 * Name under which the currently validate value is bound to the EL context.
	 */
	private static final String VALIDATED_VALUE_NAME = "validatedValue";

	/**
	 * The locale for which to interpolate the expression.
	 */
	private final Locale locale;

	/**
	 * Factory for creating EL expressions
	 */
	private static final ExpressionFactory expressionFactory;

	static {
		expressionFactory = ExpressionFactory.newInstance();
	}
	
	public ElTermResolver(Locale locale) {
		this.locale = locale;
	}


	public String interpolate(MessageInterpolator.Context context, String expression) {
		String resolvedExpression = expression;
		SimpleELContext elContext = new SimpleELContext();
		try {
			ValueExpression valueExpression = bindContextValues( expression, context, elContext );
			resolvedExpression = (String) valueExpression.getValue( elContext );
		}
		catch ( PropertyNotFoundException pnfe ) {
			throw new UnknownPropertyException("EL expression '%s' references an unknown property",pnfe);
		}
		catch ( ELException e ) {
			throw new ExpressionLanguageException("Error in EL expression "+expression,e);
		}
		catch ( Exception e ) {
			throw new ExpressionLanguageException("An exception occurred during evaluation of EL expression "+expression,e);
		}

		return resolvedExpression;
	}

	private ValueExpression bindContextValues(String messageTemplate, MessageInterpolator.Context messageInterpolatorContext, SimpleELContext elContext) {
		// bind the validated value
		ValueExpression valueExpression = expressionFactory.createValueExpression(
				messageInterpolatorContext.getValidatedValue(),
				Object.class
		);
		elContext.setVariable( VALIDATED_VALUE_NAME, valueExpression );

		// bind a formatter instantiated with proper locale
		valueExpression = expressionFactory.createValueExpression(
				new FormatterWrapper( locale ),
				FormatterWrapper.class
		);
		elContext.setVariable( RootResolver.FORMATTER, valueExpression );

		// map the annotation values
		for ( Map.Entry<String, Object> entry : messageInterpolatorContext.getConstraintDescriptor()
				.getAttributes()
				.entrySet() ) {
			valueExpression = expressionFactory.createValueExpression( entry.getValue(), Object.class );
			elContext.setVariable( entry.getKey(), valueExpression );
		}

		return expressionFactory.createValueExpression( elContext, messageTemplate, String.class );
	}
}
