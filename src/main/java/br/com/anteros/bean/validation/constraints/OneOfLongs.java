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

import br.com.anteros.bean.validation.constraints.validators.OneOfLongsValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = OneOfLongsValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface OneOfLongs {
    String message() default "{br.com.anteros.bean.validation.constraints.ONE_OF_LONGS.message}";

    long[] value() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
