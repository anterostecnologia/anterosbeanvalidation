package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.IPv6;
import br.com.anteros.core.utils.InetAddressValidator;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class IPv6Validator implements ConstraintValidator<IPv6, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return InetAddressValidator
                .getInstance()
                .isValidInet6Address(value);
    }

	@Override
	public void initialize(IPv6 constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}
}
