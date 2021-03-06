package br.com.anteros.bean.validation.resource.messages;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import br.com.anteros.core.resource.messages.AnterosBundle;

public class AnterosValidationMessages implements AnterosBundle {

	private final Map<String, String> messages = new HashMap<String, String>();

	public AnterosValidationMessages() {
		
		messages.put("javax.validation.constraints.AssertFalse.message","{label} must be false");
		messages.put("javax.validation.constraints.AssertTrue.message","{label} must be true");
		messages.put("javax.validation.constraints.DecimalMax.message","{label} must be less than or equal to {value}");
		messages.put("javax.validation.constraints.DecimalMin.message","{label} must be greater than or equal to {value}");
		messages.put("javax.validation.constraints.Digits.message","{label} numeric value out of bounds (<{integer} digits>.<{fraction} digits> expected)");
		messages.put("javax.validation.constraints.Future.message","{label} must be in the future");
		messages.put("javax.validation.constraints.Max.message","{label} must be less than or equal to {value}");
		messages.put("javax.validation.constraints.Min.message","{label} must be greater than or equal to {value}");
		messages.put("javax.validation.constraints.NotNull.message","{label} may not be null");
		messages.put("javax.validation.constraints.Null.message","{label} must be null");
		messages.put("javax.validation.constraints.Past.message","{label} must be in the past");
		messages.put("javax.validation.constraints.Pattern.message","{label} must match '{regexp}'");
		messages.put("javax.validation.constraints.Size.message","{label} size must be between {min} and {max}");

		messages.put("br.com.anteros.bean.validation.constraints.CreditCardNumber.message","invalid credit card number");
		messages.put("br.com.anteros.bean.validation.constraints.Email.message","not a well-formed email address");
		messages.put("br.com.anteros.bean.validation.constraints.Length.message","{label} length must be between {min} and {max}");
		messages.put("br.com.anteros.bean.validation.constraints.NotBlank.message","{label} may not be empty");
		messages.put("br.com.anteros.bean.validation.constraints.NotEmpty.message","{label} may not be empty");
		messages.put("br.com.anteros.bean.validation.constraints.Range.message","{label} must be between {min} and {max}");
		messages.put("br.com.anteros.bean.validation.constraints.SafeHtml.message","{label} may have unsafe html content");
		messages.put("br.com.anteros.bean.validation.constraints.ELAssert.message","expression '{expression}' didn't evaluate to true");
		messages.put("br.com.anteros.bean.validation.constraints.URL.message","{label} must be a valid URL");
		messages.put("br.com.anteros.bean.validation.constraints.Required.message","Field {label} is required, must have a value\u002e");

		messages.put("br.com.anteros.bean.validation.constraints.CNPJ.message","N\u00e3o \u00e9 um CNPJ v\u00e1lido");
		messages.put("br.com.anteros.bean.validation.constraints.CPF.message","N\u00e3o \u00e9 um CPF v\u00e1lido");
		messages.put("br.com.anteros.bean.validation.constraints.IE.message","N\u00e3o \u00e9 uma Inscri\u00e7\u00e3o Estadual v\u00e1lida");
		messages.put("br.com.anteros.bean.validation.constraints.NIT.message","N\u00e3o \u00e9 um PIS\u002fNIT v\u00e1lido");
		messages.put("br.com.anteros.bean.validation.constraints.TituloEleitoral.message","N\u00e3o \u00e9 um Titulo Eleitoral v\u00e1lido");
		
		messages.put("br.com.anteros.bean.validation.constraints.ALPHA.message",
				"Validated value '${validatedValue}' doesn't meet the conditions. The string needs to contain only unicode letters.");
		messages.put("br.com.anteros.bean.validation.constraints.ALPHA_NUMERIC.message",
				"Validated value '${validatedValue}' doesn't meet the conditions. The string needs to contain only unicode letters or digits.");
		messages.put("br.com.anteros.bean.validation.constraints.ALPHA_NUMERIC_SPACE.message",
				"Validated value '${validatedValue}' doesn't meet the conditions. The string needs to contain only unicode letters, digits or spaces (' ').");
		messages.put("br.com.anteros.bean.validation.constraints.ALPHA_SPACE.message",
				"Validated value '${validatedValue}' doesn't meet the conditions. The string needs to contain only unicode letters or spaces (' ').");
		messages.put("br.com.anteros.bean.validation.constraints.ASCII_PRINTABLE.message",
				"Validated value '${validatedValue}' doesn't meet the conditions. The string needs to contain only printable ascii characters.");
		messages.put("br.com.anteros.bean.validation.constraints.IPV4.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to represent a valid IPv4 address.");
		messages.put("br.com.anteros.bean.validation.constraints.IPV6.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to represent a valid IPv6 address.");
		messages.put("br.com.anteros.bean.validation.constraints.LOWERCASE.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to contain only lower case letters.");
		messages.put("br.com.anteros.bean.validation.constraints.NUMERIC.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to ne a number.");
		messages.put("br.com.anteros.bean.validation.constraints.STARTS_WITH.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to start with the give prefix(es): '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.ENDS_WITH.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to end with the give suffix(es): '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.UPPERCASE.message",
				"Validated value '${validatedValue}' doesn't meet the required conditions. The string needs to contain only upper case letters.");
		messages.put("br.com.anteros.bean.validation.constraints.PARSEABLE.message",
				"Validated value '${validatedValue == null ? '<null>' : validatedValue}' cannot be parsed to '${value.getFriendlyName()}'.");
		messages.put("br.com.anteros.bean.validation.constraints.PASSWORD.message",
				"Validate value '${validatedValue}' doesn't meet the password security policies.");
		
		messages.put("br.com.anteros.bean.validation.constraints.INSTANCEOF.message",
				"Validated value '${validatedValue}' is an instance of the required classes: '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.NOT_INSTANCEOF.message",
				"Validated value '${validatedValue}' is not an instance of the excluded classes: '{value}'");
		messages.put("br.com.anteros.bean.validation.constraints.EXP_JS.message","Validated value '${validatedValue}' doesn't match the condition imposed by the Java Script expression: '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.AFTER.message","Validated value '${validatedValue}' deve ser maior que '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.BEFORE.message","Validated value '${validatedValue}' deve ser menor que '{value}'.");

		messages.put("br.com.anteros.bean.validation.constraints.ONE_OF_CHARS.message","Validated value '${validatedValue}' cannot be found in the list: '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.ONE_OF_DOUBLES.message","Validated value '${validatedValue}' cannot be found in the list: '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.ONE_OF_INTEGERS.message","Validated value '${validatedValue}' cannot be found in the list: '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.ONE_OF_LONGS.message","Validated value '${validatedValue}' cannot be found in the list: '{value}'.");
		messages.put("br.com.anteros.bean.validation.constraints.ONE_OF_STRINGS.message","Validated value '${validatedValue}' cannot be found in the list: '{value}'.");

		messages.put("br.com.anteros.bean.validation.constraints.DURATION_MAX.message","deve ser menor que${inclusive == true ? ' ou igual a' : ''}${dias == 0 ? '' : dias == 1 ? ' 1 dia' : ' ' += dias += ' dias'}${horas == 0 ? '' : horas == 1 ? ' 1 hora' : ' ' += horas += ' horas'}${minutos == 0 ? '' : minutos == 1 ? ' 1 minuto' : ' ' += minutos += ' minutos'}${segundos == 0 ? '' : segundos == 1 ? ' 1 segundo' : ' ' += segundos += ' segundos'}${miliss == 0 ? '' : miliss == 1 ? ' 1 mili' : ' ' += miliss += ' miliss'}${nanos == 0 ? '' : nanos == 1 ? ' 1 nano' : ' ' += nanos += ' nanos'}");
		messages.put("br.com.anteros.bean.validation.constraints.DURATION_MIN.message","deve ser maior que${inclusive == true ? ' ou igual a' : ''}${dias == 0 ? '' : dias == 1 ? ' 1 dia' : ' ' += dias += ' dias'}${horas == 0 ? '' : horas == 1 ? ' 1 hora' : ' ' += horas += ' horas'}${minutos == 0 ? '' : minutos == 1 ? ' 1 minuto' : ' ' += minutos += ' minutos'}${segundos == 0 ? '' : segundos == 1 ? ' 1 segundo' : ' ' += segundos += ' segundos'}${miliss == 0 ? '' : miliss == 1 ? ' 1 mili' : ' ' += miliss += ' miliss'}${nanos == 0 ? '' : nanos == 1 ? ' 1 nano' : ' ' += nanos += ' nanos'}");
		messages.put("br.com.anteros.bean.validation.constraints.DOMAIN.message","Endereço de domínio inválido {value}");
		
		messages.put("br.com.anteros.bean.validation.constraints.NotNullIfAnotherFieldHasValue.message","{fieldName} deve ser preenchido se o campo {fieldName} for igual a {fieldValue}.");
		messages.put("br.com.anteros.bean.validation.constraints.NullIfAnotherFieldHasValue.message","{fieldName} deve ser null se o campo {fieldName} for preenchido.");
		messages.put("br.com.anteros.bean.validation.constraints.FieldMatch.message","O campo {first} deve ter o valor correspondente ao campo {second} ");
		messages.put("br.com.anteros.bean.validation.constraints.EqualsFields.message","O campo {baseField} deve ter o valor correspondente ao campo {matchField} ");
		messages.put("br.com.anteros.bean.validation.constraints.UUID.message","{label} contém um UUID inválido.");
		messages.put("br.com.anteros.bean.validation.constraints.fromToDate.message","{label} deve ser maior que {label}");
		messages.put("br.com.anteros.bean.validation.constraints.greaterThan.message","{label} deve ser maior que {label}");
		messages.put("br.com.anteros.bean.validation.constraints.greaterOrEqualsThan.message","{label} deve ser maior ou igual a {label}");
		messages.put("br.com.anteros.bean.validation.constraints.lessThan.message","{label} deve ser menor que {label}");
		messages.put("br.com.anteros.bean.validation.constraints.lessOrEqualsThan.message","{label} deve ser menor ou igual a {label}");
		
	}

	public String getMessage(String key) {
		return messages.get(key);
	}

	public String getMessage(String key, Object... parameters) {
		return MessageFormat.format(getMessage(key), parameters);
	}
	
	public Enumeration<String> getKeys() {
		return new Vector<String>(messages.keySet()).elements();
	}
}
