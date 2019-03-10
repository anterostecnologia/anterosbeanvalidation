package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.InstanceOf;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class InstanceOfValidator implements ConstraintValidator<InstanceOf, Object> {

    private InstanceOf annotation;

    @Override
    public void initialize(InstanceOf constraintAnnotation) {
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
                return true;
            }
        }

        return false;
    }
}
