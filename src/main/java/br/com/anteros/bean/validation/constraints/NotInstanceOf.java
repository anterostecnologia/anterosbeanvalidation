package br.com.anteros.bean.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.NotInstanceOfValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

@Documented
@Constraint(validatedBy = NotInstanceOfValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface NotInstanceOf {
    String message() default "{br.com.anteros.bean.validation.constraints.NOT_INSTANCEOF}";
    Class<?>[] value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
