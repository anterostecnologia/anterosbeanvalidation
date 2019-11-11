package br.com.anteros.bean.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.anteros.bean.validation.constraints.validators.NullIfAnotherFieldHasValueValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

/**
 * Validates that fields[] {@code dependFieldNames} is not null if field {@code fieldName} has value {@code fieldValue}.
 * 
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NullIfAnotherFieldHasValueValidator.class)
@Documented
public @interface NullIfAnotherFieldHasValue {

  String fieldName();

  String[] dependFieldName();

  String message() default "{br.com.anteros.bean.validation.constraints.NullIfAnotherFieldHasValue.message}";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
  @Target({ TYPE, ANNOTATION_TYPE })
  @Retention(RUNTIME)
  @Documented
  @interface List {
    NullIfAnotherFieldHasValue[] value();
  }
}