package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.AlphaSpace;
import br.com.anteros.core.utils.StringUtils;

public class AlphaSpaceValidator extends GenericStringValidator<AlphaSpace> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAlphaSpace;
    }
}
