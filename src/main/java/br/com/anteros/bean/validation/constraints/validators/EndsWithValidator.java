package br.com.anteros.bean.validation.constraints.validators;

import java.util.function.BiFunction;

import br.com.anteros.bean.validation.constraints.EndsWith;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class EndsWithValidator implements ConstraintValidator<EndsWith, String> {

    protected EndsWith annotation;

    @Override
    public void initialize(EndsWith constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] prefixes = annotation.value();

        BiFunction<String, String, Boolean> fct =
                !annotation.ignoreCase() ?
                        StringUtils::endsWith :
                        StringUtils::endsWithIgnoreCase;

        for(String p : prefixes) {
            if (fct.apply(value, p)) {
                return true;
            }
        }

        return false;
    }
}
