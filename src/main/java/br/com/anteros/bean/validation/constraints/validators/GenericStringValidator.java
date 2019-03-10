package br.com.anteros.bean.validation.constraints.validators;



import java.lang.annotation.Annotation;
import java.util.function.Function;

import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public abstract class GenericStringValidator <T extends Annotation> implements ConstraintValidator<T, String> {

    protected T annotation;

    @Override
    public void initialize(T constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return condition().apply(value);
    }

    public abstract Function<String, Boolean> condition();
}