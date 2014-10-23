package br.com.anteros.bean.validation.constraints.validators;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.anteros.bean.validation.constraints.Required;
import br.com.anteros.core.utils.StringUtils;

public class FieldRequiredValidator implements ConstraintValidator<Required, Object> {

	public void initialize(Required annotation) {

	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null)
			return false;
		if (value instanceof Date) {
			return ((Date) value).getTime() > 0;
		} else if (value instanceof java.sql.Date) {
			return ((java.sql.Date) value).getTime() > 0;
		} else if (value instanceof Collection<?>) {
			return !((Collection<?>) value).isEmpty();
		} else if (value instanceof String) {
			return !StringUtils.isEmpty((String) value);
		}
		return true;
	}

}
