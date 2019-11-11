package br.com.anteros.bean.validation.constraints.validators;

import java.lang.reflect.Field;
import java.util.Comparator;

import br.com.anteros.bean.validation.constraints.GreaterOrEqualsThan;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class GreaterOrEqualsThanValidator implements ConstraintValidator<GreaterOrEqualsThan, Object> {


    private String field;
    private String greaterOrEqualsThan;
    private Comparator<Object> comparator;

    @Override
    public void initialize(GreaterOrEqualsThan ann) {

        field = ann.field();
        greaterOrEqualsThan = ann.greaterOrEqualsThan();
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
        Field greaterOrEqualsThanObj = null;
        try {
            fieldObj=validateThis.getClass().getDeclaredField(field);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name",e);
        }
        try {
            greaterOrEqualsThanObj = validateThis.getClass().getDeclaredField(greaterOrEqualsThan);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid greaterThan name",e);
        }
        if (fieldObj == null || greaterOrEqualsThanObj == null) {
            throw new IllegalArgumentException("Invalid field names");
        }

        try {
            fieldObj.setAccessible(true);
            greaterOrEqualsThanObj.setAccessible(true);
            Object fieldVal = fieldObj.get(validateThis);
            Object largerThanVal = greaterOrEqualsThanObj.get(validateThis);
            return fieldVal == null && largerThanVal == null
            || fieldVal != null && largerThanVal != null
            && comparator.compare(fieldVal, largerThanVal) >= 0;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't validate object", e);
        }

    }

}
