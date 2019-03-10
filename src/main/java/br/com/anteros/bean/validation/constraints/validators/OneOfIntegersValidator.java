package br.com.anteros.bean.validation.constraints.validators;


import java.util.Objects;

import br.com.anteros.bean.validation.constraints.OneOfIntegers;
import br.com.anteros.core.utils.ArrayUtils;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class OneOfIntegersValidator extends OneOfValidator<OneOfIntegers, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return super.isValid(value, ArrayUtils.toObject(annotation.value()), Objects::equals, context);
    }
}
