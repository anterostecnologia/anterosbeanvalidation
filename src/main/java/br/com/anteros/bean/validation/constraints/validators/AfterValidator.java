package br.com.anteros.bean.validation.constraints.validators;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.anteros.bean.validation.constraints.After;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class AfterValidator implements ConstraintValidator<After, Date> {

    private After annotation;

    @Override
    public void initialize(After constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(annotation.format());
        try {
            Date AfterDate = sdf.parse(annotation.value());
            boolean isAfter = value.after(AfterDate);
            return isAfter;
        } catch (ParseException e) {
            return false;
        }
    }
}
