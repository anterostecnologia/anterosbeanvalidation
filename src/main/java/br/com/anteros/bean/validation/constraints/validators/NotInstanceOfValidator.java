package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.NotInstanceOf;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class NotInstanceOfValidator implements ConstraintValidator<NotInstanceOf, Object> {

    private NotInstanceOf annotation;

    @Override
    public void initialize(NotInstanceOf constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (null == value) {
            return false;
        }

        Class<?>[] classes = annotation.value();

        for(Class<?> cls : classes) {
            if (cls.isInstance(value)) {
                return false;
            }
        }

        return true;
    }
}
