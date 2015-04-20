package br.com.anteros.bean.validation.resource.messages;

import java.util.Properties;

public class AnterosDefaultConstraints {

	private AnterosDefaultConstraints() {
	}

	public static Properties getConstraints() {

		Properties result = new Properties();
		result.put("javax.validation.constraints.AssertFalse", "br.com.anteros.bean.validation.constraints.validators.AssertFalseValidator");
		result.put("javax.validation.constraints.AssertTrue", "br.com.anteros.bean.validation.constraints.validators.AssertTrueValidator");
		result.put(
				"javax.validation.constraints.DecimalMax",
				"br.com.anteros.bean.validation.constraints.validators.DecimalMaxValidatorForNumber, br.com.anteros.bean.validation.constraints.validators.DecimalMaxValidatorForString");
		result.put(
				"javax.validation.constraints.DecimalMin",
				"br.com.anteros.bean.validation.constraints.validators.DecimalMinValidatorForNumber, br.com.anteros.bean.validation.constraints.validators.DecimalMinValidatorForString");
		result.put(
				"javax.validation.constraints.Digits",
				"br.com.anteros.bean.validation.constraints.validators.DigitsValidatorForNumber, br.com.anteros.bean.validation.constraints.validators.DigitsValidatorForString");
		result.put(
				"javax.validation.constraints.Future",
				"br.com.anteros.bean.validation.constraints.validators.FutureValidatorForDate, br.com.anteros.bean.validation.constraints.validators.FutureValidatorForCalendar");
		result.put("javax.validation.constraints.Max",
				"br.com.anteros.bean.validation.constraints.validators.MaxValidatorForNumber, br.com.anteros.bean.validation.constraints.validators.MaxValidatorForString");
		result.put("javax.validation.constraints.Min",
				"br.com.anteros.bean.validation.constraints.validators.MinValidatorForNumber, br.com.anteros.bean.validation.constraints.validators.MinValidatorForString");
		result.put("javax.validation.constraints.NotNull", "br.com.anteros.bean.validation.constraints.validators.NotNullValidator");
		result.put("javax.validation.constraints.Null", "br.com.anteros.bean.validation.constraints.validators.NullValidator");
		result.put("javax.validation.constraints.Past",
				"br.com.anteros.bean.validation.constraints.validators.PastValidatorForDate, br.com.anteros.bean.validation.constraints.validators.PastValidatorForCalendar");
		result.put(
				"javax.validation.constraints.Size",
				"br.com.anteros.bean.validation.constraints.validators.SizeValidatorForString, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForMap, "
						+ "br.com.anteros.bean.validation.constraints.validators.SizeValidatorForCollection, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfBoolean, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfByte, "
						+ "br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfChar, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfDouble, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfFloat, "
						+ "br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfInt, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfLong, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfObject, br.com.anteros.bean.validation.constraints.validators.SizeValidatorForArrayOfShort ");
		result.put("javax.validation.constraints.Pattern", "br.com.anteros.bean.validation.constraints.validators.PatternValidator");

		return result;
	}
}
