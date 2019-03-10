package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.IPv4;
import br.com.anteros.core.utils.InetAddressValidator;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class IPv4Validator implements ConstraintValidator<IPv4, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return InetAddressValidator
                .getInstance()
                .isValidInet4Address(value);
    }

	@Override
	public void initialize(IPv4 constraintAnnotation) {
		
	}
}
