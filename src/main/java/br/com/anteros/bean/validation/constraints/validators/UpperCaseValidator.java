package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.UpperCase;
import br.com.anteros.core.utils.StringUtils;

public class UpperCaseValidator extends GenericStringValidator<UpperCase> {

    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAllUpperCase;
    }

}
