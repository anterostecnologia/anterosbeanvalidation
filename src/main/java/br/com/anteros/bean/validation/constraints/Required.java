package br.com.anteros.bean.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.anteros.bean.validation.constraints.validators.FieldRequiredValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Documented
@Constraint(validatedBy = { FieldRequiredValidator.class })
public @interface Required {

	String message() default "{br.com.anteros.bean.validation.constraints.Required.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
