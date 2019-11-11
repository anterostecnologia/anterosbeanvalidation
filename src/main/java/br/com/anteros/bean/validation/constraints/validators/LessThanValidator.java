package br.com.anteros.bean.validation.constraints.validators;

import java.lang.reflect.Field;
import java.util.Comparator;

import br.com.anteros.bean.validation.constraints.LessThan;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class LessThanValidator implements ConstraintValidator<LessThan, Object> {


    private String field;
    private String lessThan;
    private Comparator<Object> comparator;

    @Override
    public void initialize(LessThan ann) {

        field = ann.field();
        lessThan = ann.lessThan();
        Class<? extends Comparator<Object>> comparatorClass = ann.comparator();
        try {
            comparator = comparatorClass.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't instantiate comparator",e);
        }
    }

    @Override
    public boolean isValid(Object validateThis, ConstraintValidatorContext ctx) {
        if (validateThis == null) {
            throw new IllegalArgumentException("validateThis is null");
        }
        Field fieldObj = null;
        Field lessThanObj = null;
        try {
            fieldObj=validateThis.getClass().getDeclaredField(field);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name",e);
        }
        try {
            lessThanObj = validateThis.getClass().getDeclaredField(lessThan);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid lessThan name",e);
        }
        if (fieldObj == null || lessThanObj == null) {
            throw new IllegalArgumentException("Invalid field names");
        }

        try {
            fieldObj.setAccessible(true);
            lessThanObj.setAccessible(true);
            Object fieldVal = fieldObj.get(validateThis);
            Object largerThanVal = lessThanObj.get(validateThis);
            return fieldVal == null && largerThanVal == null
            || fieldVal != null && largerThanVal != null
            && comparator.compare(fieldVal, largerThanVal) < 0;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't validate object", e);
        }

    }

}
