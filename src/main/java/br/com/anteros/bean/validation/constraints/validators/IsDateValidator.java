package br.com.anteros.bean.validation.constraints.validators;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.anteros.bean.validation.constraints.IsDate;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class IsDateValidator implements ConstraintValidator<IsDate, String> {

    private IsDate annotation;

    @Override
    public void initialize(IsDate constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.isEmpty(value)) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(annotation.value());
        try {
            sdf.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
