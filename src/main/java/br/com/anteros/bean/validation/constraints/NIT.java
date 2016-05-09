package br.com.anteros.bean.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.NITValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

/**
 * Restrição que pode ser associada a objetos em que o método
 * {@linkplain #toString()} represente um NIT (PIS/PASEP/SUS).
 * 
 * @author Leonardo Bessa
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = NITValidator.class)
public @interface NIT {
	String message() default "{br.com.anteros.bean.validation.constraints.NIT.message}";

	boolean formatted() default false;
	
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
