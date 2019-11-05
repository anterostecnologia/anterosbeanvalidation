package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.BeanDescriptorImpl;
import br.com.anteros.bean.validation.constraints.NotNullIfAnotherFieldHasValue;
import br.com.anteros.core.metadata.beans.BeanDescriptor;
import br.com.anteros.persistence.metadata.accessor.PropertyAccessor;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

/**
 * Implementation of {@link NotNullIfAnotherFieldHasValue} validator.
 */
public class NotNullIfAnotherFieldHasValueValidator
		implements ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {

	private String fieldName;

	private String expectedFieldValue;

	private String[] dependFieldName;

	@Override
	public void initialize(NotNullIfAnotherFieldHasValue annotation) {
		fieldName = annotation.fieldName();
		expectedFieldValue = annotation.fieldValue();
		dependFieldName = annotation.dependFieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		BeanDescriptor descriptor = new BeanDescriptor(value.getClass());
		descriptor.getValue(fieldName);
		Object fieldValue = descriptor.getValue(fieldName);
		for (String dpFld : dependFieldName) {
			Object dependFieldValue = descriptor.getValue(dpFld);

			if (fieldValue != null && fieldValue.toString().equals(expectedFieldValue) && dependFieldValue == null) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
						.addNode(dpFld).addConstraintViolation();

				return false;
			}
		}

		return true;
	}

}
