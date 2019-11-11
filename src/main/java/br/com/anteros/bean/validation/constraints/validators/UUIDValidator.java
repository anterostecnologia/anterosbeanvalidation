package br.com.anteros.bean.validation.constraints.validators;

import java.util.regex.Pattern;

import br.com.anteros.bean.validation.constraints.UUID;
import br.com.anteros.bean.validation.constraints.UUID.UUIDPattern;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<UUID, String> {
	protected java.util.regex.Pattern pattern;

	// Pattern 1 was encountered in an actual application
	public static final Pattern UUID_PATTERN_1 = Pattern
			.compile("(:?[a-f0-9]){8,8}-(:?[a-f0-9]){4,4}-(:?[a-f0-9]){4,4}-(:?[a-f0-9]){4,4}-(:?[a-f0-9]){12,12}");

	// Patterns 2 - 4 are tuning steps (expected to get faster with each step)
	public static final Pattern UUID_PATTERN_2 = Pattern
			.compile("(:?[a-f0-9]){8}-(:?[a-f0-9]){4}-(:?[a-f0-9]){4}-(:?[a-f0-9]){4}-(:?[a-f0-9]){12}");
	public static final Pattern UUID_PATTERN_3 = Pattern
			.compile("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}");
	public static final Pattern UUID_PATTERN_4 = Pattern
			.compile("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$");

	// Pattern 5 takes into account that uppercase letters are valid for
	// UUID.fromString()
	public static final Pattern UUID_PATTERN_5 = Pattern
			.compile("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$", Pattern.CASE_INSENSITIVE);

	// Pattern 6 is taken from http://stackoverflow.com/a/13653180 and is more
	// strict
	public static final Pattern UUID_PATTERN_6 = Pattern.compile(
			"^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", Pattern.CASE_INSENSITIVE);

	public void initialize(UUID annotation) {
		if (annotation.pattern().equals(UUIDPattern.UUID_PATTERN_1)) {
			pattern = UUID_PATTERN_1;
		} else if (annotation.pattern().equals(UUIDPattern.UUID_PATTERN_2)) {
			pattern = UUID_PATTERN_2;
		} else if (annotation.pattern().equals(UUIDPattern.UUID_PATTERN_3)) {
			pattern = UUID_PATTERN_3;
		} else if (annotation.pattern().equals(UUIDPattern.UUID_PATTERN_4)) {
			pattern = UUID_PATTERN_4;
		} else if (annotation.pattern().equals(UUIDPattern.UUID_PATTERN_5)) {
			pattern = UUID_PATTERN_5;
		} else if (annotation.pattern().equals(UUIDPattern.UUID_PATTERN_6)) {
			pattern = UUID_PATTERN_6;
		}
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || pattern.matcher(value).matches();
	}
}
