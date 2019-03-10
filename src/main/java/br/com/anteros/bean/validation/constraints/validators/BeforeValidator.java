package br.com.anteros.bean.validation.constraints.validators;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.anteros.bean.validation.constraints.Before;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class BeforeValidator implements ConstraintValidator<Before, Date> {

    private Before annotation;

    @Override
    public void initialize(Before constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(annotation.format());
        try {
            Date beforeDate = sdf.parse(annotation.value());
            boolean isBefore = value.before(beforeDate);
            return isBefore;
        } catch (ParseException e) {
            return false;
        }
    }
}
