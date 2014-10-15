package br.com.anteros.bean.validation.constraints;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.anteros.bean.validation.constraints.validators.ELAssertValidator;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ELAssertValidator.class })
@Documented
public @interface ELAssert {

	String message() default "{br.com.anteros.bean.validation.constraints.ELAssert.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	String expression();

	String alias() default "_this";

	@Target({ TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ELAssert[] value();
	}
}
