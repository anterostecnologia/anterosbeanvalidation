package br.com.anteros.bean.validation.constraints.validators;

import java.util.Objects;

import br.com.anteros.bean.validation.constraints.OneOfLongs;
import br.com.anteros.core.utils.ArrayUtils;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class OneOfLongsValidator extends OneOfValidator<OneOfLongs, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return super.isValid(value, ArrayUtils.toObject(annotation.value()), Objects::equals, context);
    }
}
