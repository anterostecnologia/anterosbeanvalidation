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

import br.com.anteros.bean.validation.constraints.validators.PasswordValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Password {

    boolean containsUpperCase() default true;

    boolean containsLowerCase() default true;

    boolean containsSpecialChar() default true;

    boolean containsDigits() default true;

    boolean allowSpace() default false;

    int minSize() default 8;

    int maxSize() default 32;

    String message() default "{br.com.anteros.bean.validation.constraints.PASSWORD}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
