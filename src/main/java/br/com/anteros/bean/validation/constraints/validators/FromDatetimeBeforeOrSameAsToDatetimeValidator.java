package br.com.anteros.bean.validation.constraints.validators;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import br.com.anteros.bean.validation.constraints.FromDateBeforeOrSameAsToDate;
import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
 
public class FromDatetimeBeforeOrSameAsToDatetimeValidator
        implements ConstraintValidator<FromDateBeforeOrSameAsToDate, Object> {
 
    private String fromDate;
 
    private String toDate;
    
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
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
    	
    	LocalDateTime fromLocalDate = null;
    	LocalDateTime toLocalDate = null;
    	if (value1 != null)
            fromLocalDate = (value1 instanceof LocalDateTime ? (LocalDateTime)value1: convertToLocalDateTimeViaInstant((Date) value1));
    	if (value2 != null)
            toLocalDate = (value2 instanceof LocalDateTime ? (LocalDateTime)value2: convertToLocalDateTimeViaInstant((Date) value2));
 
        if (fromLocalDate == null || toLocalDate == null) {
            return true;
        }
 
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                "{app.validation.constraints.fromDatetimeSameOrBeforeToDatetime.msg}")
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