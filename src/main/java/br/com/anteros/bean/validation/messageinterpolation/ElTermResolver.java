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

import java.util.Locale;
import java.util.Map;

import br.com.anteros.bean.validation.GroupValidationContext;
import br.com.anteros.bean.validation.UnknownPropertyException;
import br.com.anteros.el.api.ELException;
import br.com.anteros.el.api.ExpressionFactory;
import br.com.anteros.el.api.PropertyNotFoundException;
import br.com.anteros.el.api.ValueExpression;
import br.com.anteros.validation.api.MessageInterpolator;

/**
 * Resolver for the el expressions.
 * 
 */
public class ElTermResolver implements TermResolver {
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
			ValueExpression valueExpression = bindContextValues(expression, context, elContext);
			resolvedExpression = (String) valueExpression.getValue(elContext);
		} catch (PropertyNotFoundException pnfe) {
			throw new UnknownPropertyException("EL expression '%s' references an unknown property", pnfe);
		} catch (ELException e) {
			throw new ExpressionLanguageException("Error in EL expression " + expression, e);
		} catch (Exception e) {
			throw new ExpressionLanguageException("An exception occurred during evaluation of EL expression "
					+ expression, e);
		}

		return resolvedExpression;
	}

	private ValueExpression bindContextValues(String messageTemplate,
			MessageInterpolator.Context messageInterpolatorContext, SimpleELContext elContext) {
		// bind the validated value
		ValueExpression valueExpression = expressionFactory.createValueExpression(
				messageInterpolatorContext.getValidatedValue(), Object.class);
		elContext.setVariable(VALIDATED_VALUE_NAME, valueExpression);

		// bind a formatter instantiated with proper locale
		valueExpression = expressionFactory.createValueExpression(new FormatterWrapper(locale), FormatterWrapper.class);
		elContext.setVariable(RootResolver.FORMATTER, valueExpression);

		// map the annotation values
		for (Map.Entry<String, Object> entry : messageInterpolatorContext.getConstraintDescriptor().getAttributes()
				.entrySet()) {
			valueExpression = expressionFactory.createValueExpression(entry.getValue(), Object.class);
			elContext.setVariable(entry.getKey(), valueExpression);
		}

		// check for custom message parameters
		if (messageInterpolatorContext instanceof GroupValidationContext) {
			GroupValidationContext internalContext = (GroupValidationContext) messageInterpolatorContext;
			Map<String,Object> messageParameters = internalContext.getMessageParameters();
			for (String key : messageParameters.keySet()) {
				valueExpression = expressionFactory.createValueExpression(
						internalContext.getMessageParameters().get(key), Object.class);
				elContext.setVariable(key, valueExpression);
			}
		}

		return expressionFactory.createValueExpression(elContext, messageTemplate, String.class);
	}
}
