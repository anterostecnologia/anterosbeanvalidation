package br.com.anteros.bean.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.UUIDValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

@Target(ElementType.FIELD)
@Constraint(validatedBy={UUIDValidator.class})
@Retention(RUNTIME)
public @interface UUID {
	
    String message() default "{br.com.anteros.bean.validation.constraints.UUID.message}";
    UUIDPattern pattern() default UUIDPattern.UUID_PATTERN_4;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    public enum UUIDPattern {
    	UUID_PATTERN_1, UUID_PATTERN_2, UUID_PATTERN_3, UUID_PATTERN_4, UUID_PATTERN_5, UUID_PATTERN_6
    }
}


