package br.com.anteros.bean.validation.constraints.validators;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;

import br.com.anteros.bean.validation.constraints.GreaterThan;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class GreaterThanValidator implements ConstraintValidator<GreaterThan, Object> {


    private String field;
    private String greaterThan;
    private Comparator<Object> comparator;

    @Override
    public void initialize(GreaterThan ann) {

        field = ann.field();
        greaterThan = ann.greaterThan();
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
        Field greaterThanObj = null;
        try {
            fieldObj=validateThis.getClass().getDeclaredField(field);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name",e);
        }
        try {
            greaterThanObj = validateThis.getClass().getDeclaredField(greaterThan);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid greaterThan name",e);
        }
        if (fieldObj == null || greaterThanObj == null) {
            throw new IllegalArgumentException("Invalid field names");
        }

        try {
            fieldObj.setAccessible(true);
            greaterThanObj.setAccessible(true);
            Object fieldVal = fieldObj.get(validateThis);
            Object largerThanVal = greaterThanObj.get(validateThis);
            return fieldVal == null && largerThanVal == null
            || fieldVal != null && largerThanVal != null
            && comparator.compare(fieldVal, largerThanVal) > 0;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't validate object", e);
        }

    }

	

}
