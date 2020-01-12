package br.com.anteros.bean.validation.constraints.validators;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import br.com.anteros.bean.validation.constraints.FromDateBeforeOrSameAsToDate;
import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
 
public class FromDateBeforeOrSameAsToDateValidator
        implements ConstraintValidator<FromDateBeforeOrSameAsToDate, Object> {
 
    private String fromDate;
 
    private String toDate;
    
    private LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
 
    @Override
    public boolean isValid(Object requestObject, ConstraintValidatorContext constraintValidatorContext) {
    	if (requestObject==null) {
    		return true;
    	}
    	Field fromDateField = ReflectionUtils.getFieldByName(requestObject.getClass(), fromDate);
    	Field toDateField = ReflectionUtils.getFieldByName(requestObject.getClass(), toDate);
    	
    	Object value1 = ReflectionUtils.getField(fromDateField, requestObject);
    	Object value2 = ReflectionUtils.getField(toDateField, requestObject);
    	
    	LocalDate fromLocalDate = null;
    	LocalDate toLocalDate = null;
    	if (value1 != null)
            fromLocalDate = (value1 instanceof LocalDate ? (LocalDate)value1: convertToLocalDateViaMilisecond((Date) value1));
    	if (value2 != null)
            toLocalDate = (value2 instanceof LocalDate ? (LocalDate)value2: convertToLocalDateViaMilisecond((Date) value2));
 
        if (fromLocalDate == null || toLocalDate == null) {
            return true;
        }
 
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                "{br.com.anteros.bean.validation.constraints.fromToDate.message}")
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