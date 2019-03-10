package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.Numeric;
import br.com.anteros.core.utils.StringUtils;

public class NumericValidator extends GenericStringValidator<Numeric> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isNumeric;
    }
}
