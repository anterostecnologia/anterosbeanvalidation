package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.OneOfStrings;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class OneOfStringsValidator extends OneOfValidator<OneOfStrings, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return super.isValid(value, annotation.value(), StringUtils::equals, context);
    }
}
