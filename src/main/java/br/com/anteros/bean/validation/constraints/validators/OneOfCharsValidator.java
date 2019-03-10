package br.com.anteros.bean.validation.constraints.validators;

import java.util.Objects;

import br.com.anteros.bean.validation.constraints.OneOfChars;
import br.com.anteros.core.utils.ArrayUtils;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class OneOfCharsValidator extends OneOfValidator<OneOfChars, Character> {
    @Override
    public boolean isValid(Character value, ConstraintValidatorContext context) {
        return super.isValid(value, ArrayUtils.toObject(annotation.value()), Objects::equals, context);
    }
}
