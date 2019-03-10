package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.Alpha;
import br.com.anteros.core.utils.StringUtils;

public class AlphaValidator extends GenericStringValidator<Alpha> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAlphanumeric;
    }
}