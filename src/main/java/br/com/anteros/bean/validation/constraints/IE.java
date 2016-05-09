package br.com.anteros.bean.validation.constraints;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.IEValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

/**
 * Restrição que pode ser associada a classes que contenham um objeto que
 * represente uma Inscricao Estadual e outro objeto identificando o estado a que
 * este documento pertence.
 * 
 * @author Leonardo Bessa
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE })
@Constraint(validatedBy = IEValidator.class)
public @interface IE {
	String message() default "{br.com.anteros.bean.validation.constraints.IE.message}";

	String ieField() default "ie";

	String estadoField() default "estado";

	boolean formatted() default false;
	
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
