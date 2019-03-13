package br.com.anteros.bean.validation.constraints;


import br.com.anteros.bean.validation.constraints.validators.AlphaValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks if the String contains only unicode letters.
 */
@Documented
@Constraint(validatedBy = AlphaValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Alpha {
    String message() default "{br.com.anteros.bean.validation.constraints.ALPHA.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}