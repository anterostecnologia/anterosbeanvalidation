package br.com.anteros.bean.validation.constraints;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.DomainValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

/**
 * <p>
 * --
 * TODO - This class is NOT part of the bean_validation spec and might disappear
 * as soon as a final version of the specification contains a similar functionality.
 * --
 * </p>
 * Description: annotation to validate a java.io.File is a directory<br/>
 */
@Documented
@Constraint(validatedBy = DomainValidator.class)
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
public @interface Domain {

    Class<?>[] groups() default {};

    String message() default "{br.com.anteros.bean.validation.constraints.DOMAIN.message}";
    Class<? extends Payload>[] payload() default {};
    boolean allowLocal() default false;
}