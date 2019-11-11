package br.com.anteros.bean.validation.constraints.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;

import br.com.anteros.bean.validation.constraints.FromDateBeforeOrSameAsToDate;
import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
 
public class FromDateBeforeOrSameAsToDateValidator
        implements ConstraintValidator<FromDateBeforeOrSameAsToDate, Object> {
 
    private String fromDate;
 
    private String toDate;
 
    @Override
    public boolean isValid(Object requestObject, ConstraintValidatorContext constraintValidatorContext) {
    	if (requestObject==null) {
    		return true;
    	}
    	Field fromDateField = ReflectionUtils.getFieldByName(requestObject.getClass(), fromDate);
    	Field toDateField = ReflectionUtils.getFieldByName(requestObject.getClass(), toDate);
 
        LocalDate fromLocalDate = (LocalDate) ReflectionUtils.getField(fromDateField, requestObject);
        LocalDate toLocalDate = (LocalDate) ReflectionUtils.getField(toDateField, requestObject);
 
        if (fromLocalDate == null || toLocalDate == null) {
            return true;
        }
 
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                "{app.validation.constraints.fromDateSameOrBeforeToDate.msg}")
                .addNode(fromDate)
                .addConstraintViolation();
 
        return fromLocalDate.isEqual(toLocalDate) || fromLocalDate.isBefore(toLocalDate);
    }

    @Override
	public void initialize(FromDateBeforeOrSameAsToDate constraintAnnotation) {
		fromDate = constraintAnnotation.fromDate();
        toDate = constraintAnnotation.toDate();
		
	}
}