package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.LowerCase;
import br.com.anteros.core.utils.StringUtils;

public class LowerCaseValidator extends GenericStringValidator<LowerCase> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAllLowerCase;
    }
}
