package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.BiFunction;

import br.com.anteros.bean.validation.constraints.StartsWith;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class StartsWithValidator implements ConstraintValidator<StartsWith, String> {

    protected StartsWith annotation;

    @Override
    public void initialize(StartsWith constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] prefixes = annotation.value();

        BiFunction<String, String, Boolean> fct =
                !annotation.ignoreCase() ?
                        StringUtils::startsWith :
                        StringUtils::startsWithIgnoreCase;

        for(String p : prefixes) {
            if (fct.apply(value, p)) {
                return true;
            }
        }

        return false;
    }
}
