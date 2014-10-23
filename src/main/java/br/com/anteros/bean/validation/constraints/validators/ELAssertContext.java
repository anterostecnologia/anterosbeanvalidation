package br.com.anteros.bean.validation.constraints.validators;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintDeclarationException;

import br.com.anteros.el.api.ELContext;
import br.com.anteros.el.api.ExpressionFactory;
import br.com.anteros.el.api.ValueExpression;
import br.com.anteros.el.util.SimpleContext;

/**
 * Context used by validator implementations dealing with script expressions.
 * Instances are thread-safe and can be re-used several times to evaluate
 * different bindings against one given given script expression.
 */
class ELAssertContext {

	private final String expression;
	private ExpressionFactory expressionFactory;
	private ELContext context;

	public ELAssertContext(String expression) {
		this.expression = expression;
		this.expressionFactory = ExpressionFactory.newInstance();
		this.context = new SimpleContext();
	}

	public boolean evaluateAssertExpression(Object object, String alias) {
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put(alias, object);

		return evaluateAssertExpression(bindings);
	}

	public boolean evaluateAssertExpression(Map<String, Object> bindings) {
		Object result;

		ValueExpression valueExpression = expressionFactory.createValueExpression(context, this.expression,
				Object.class);
		for (String alias : bindings.keySet()){
			context.getELResolver().setValue(context, null, alias, bindings.get(alias));
		}
		
		result = valueExpression.getValue(context);

		return handleResult(result);
	}

	private boolean handleResult(Object evaluationResult) {
		if (evaluationResult == null) {
			throw new ConstraintDeclarationException("Expression \"" + expression
					+ "\" returned null, but must return either true or false.");
		}

		if (!(evaluationResult instanceof Boolean)) {
			throw new ConstraintDeclarationException("Script \"" + expression + "\" returned " + expression
					+ " (of type " + evaluationResult.getClass().getCanonicalName()
					+ "), but must return either true or false.");
		}

		return Boolean.TRUE.equals(evaluationResult);
	}
}
