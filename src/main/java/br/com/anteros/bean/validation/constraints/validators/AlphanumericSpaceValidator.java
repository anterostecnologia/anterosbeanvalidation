package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.AlphanumericSpace;
import br.com.anteros.core.utils.StringUtils;

public class AlphanumericSpaceValidator extends GenericStringValidator<AlphanumericSpace> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAlphanumericSpace;
    }
}
