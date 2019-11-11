package br.com.anteros.bean.validation.constraints.validators;

import java.lang.reflect.Field;
import java.util.Comparator;

import br.com.anteros.bean.validation.constraints.LessOrEqualsThan;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class LessOrEqualsThanValidator implements ConstraintValidator<LessOrEqualsThan, Object> {


    private String field;
    private String lessOrEqualsThan;
    private Comparator<Object> comparator;

    @Override
    public void initialize(LessOrEqualsThan ann) {

        field = ann.field();
        lessOrEqualsThan = ann.lessOrEqualsThan();
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
        Field lessOrEqualsThanObj = null;
        try {
            fieldObj=validateThis.getClass().getDeclaredField(field);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name",e);
        }
        try {
            lessOrEqualsThanObj = validateThis.getClass().getDeclaredField(lessOrEqualsThan);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid lessOrEqualsThan name",e);
        }
        if (fieldObj == null || lessOrEqualsThanObj == null) {
            throw new IllegalArgumentException("Invalid field names");
        }

        try {
            fieldObj.setAccessible(true);
            lessOrEqualsThanObj.setAccessible(true);
            Object fieldVal = fieldObj.get(validateThis);
            Object largerThanVal = lessOrEqualsThanObj.get(validateThis);
            return fieldVal == null && largerThanVal == null
            || fieldVal != null && largerThanVal != null
            && comparator.compare(fieldVal, largerThanVal) <= 0;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't validate object", e);
        }

    }

}
