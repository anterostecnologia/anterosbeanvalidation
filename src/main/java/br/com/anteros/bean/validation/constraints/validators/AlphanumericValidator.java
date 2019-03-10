package br.com.anteros.bean.validation.constraints.validators;


import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.Alphanumeric;
import br.com.anteros.core.utils.StringUtils;

public class AlphanumericValidator extends GenericStringValidator<Alphanumeric> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAlphanumeric;
    }
}