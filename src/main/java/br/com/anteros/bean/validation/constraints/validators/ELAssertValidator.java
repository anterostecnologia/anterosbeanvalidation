package br.com.anteros.bean.validation.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.anteros.bean.validation.ConstraintValidatorContextImpl;
import br.com.anteros.bean.validation.GroupValidationContext;
import br.com.anteros.bean.validation.constraints.ELAssert;

/**
 * Validator for the {@link ELAssert} constraint annotation.
 *
 */
public class ELAssertValidator implements ConstraintValidator<ELAssert, Object> {

	private String alias;
	private ELAssertContext elAssertContext;

	public void initialize(ELAssert constraintAnnotation) {
		this.alias = constraintAnnotation.alias();
		this.elAssertContext = new ELAssertContext(constraintAnnotation.expression());
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		if (constraintValidatorContext instanceof ConstraintValidatorContextImpl) {
			GroupValidationContext<?> validationContext = ((ConstraintValidatorContextImpl) constraintValidatorContext)
					.getValidationContext();
			validationContext.setMessageParameter(alias, validationContext.getBean());
		}
		return elAssertContext.evaluateAssertExpression(value, alias);
	}

}
