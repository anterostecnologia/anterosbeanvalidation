package br.com.anteros.bean.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.FromDatetimeBeforeOrSameAsToDatetimeValidator;
import br.com.anteros.validation.api.Constraint;
 
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FromDatetimeBeforeOrSameAsToDatetimeValidator.class)
@Documented
public @interface FromDatetimeBeforeOrSameAsToDatetime {
 
    String message() default "{br.com.anteros.bean.validation.constraints.fromToDatetime.message}";
 
    Class[] groups() default {};
 
    Class[] payload() default {};
 
    String fromDate();
 
    String toDate();
 
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List
    {
        FromDatetimeBeforeOrSameAsToDatetime[] value();
    }
}
