package br.com.anteros.bean.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.anteros.bean.validation.constraints.validators.CNPJValidator;

/**
 * Restrição que pode ser associada a objetos em que o método
 * {@linkplain #toString()} represente um CNPJ.
 * 
 * @author Leonardo Bessa
 * @author David Paniz
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { FIELD, METHOD })
@Constraint(validatedBy = CNPJValidator.class)
public @interface CNPJ {
    String message() default "{br.com.anteros.bean.validation.constraints.CNPJ.message}";

    boolean formatted() default false;
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
