package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.Function;

import br.com.anteros.bean.validation.constraints.AsciiPrintable;
import br.com.anteros.core.utils.StringUtils;

public class AsciiPrintableValidator extends GenericStringValidator<AsciiPrintable> {
    @Override
    public Function<String, Boolean> condition() {
        return StringUtils::isAsciiPrintable;
    }
}
