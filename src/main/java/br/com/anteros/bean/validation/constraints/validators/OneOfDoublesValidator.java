package br.com.anteros.bean.validation.constraints.validators;

import java.util.Objects;

import br.com.anteros.bean.validation.constraints.OneOfDoubles;
import br.com.anteros.core.utils.ArrayUtils;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class OneOfDoublesValidator extends OneOfValidator<OneOfDoubles, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return super.isValid(value, ArrayUtils.toObject(annotation.value()), Objects::equals, context);
    }

}
