package br.com.anteros.bean.validation.resource.messages;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import br.com.anteros.core.resource.messages.AnterosBundle;

public class AnterosValidationMessages_ptBR implements AnterosBundle {

	private final Map<String, String> messages = new HashMap<String, String>();

	public AnterosValidationMessages_ptBR() {
		messages.put("javax.validation.constraints.AssertFalse.message","{label} deve ser falso");
		messages.put("javax.validation.constraints.AssertTrue.message","{label} deve ser verdadeiro");
		messages.put("javax.validation.constraints.DecimalMax.message","{label} deve ser menor ou igual a {value}");
		messages.put("javax.validation.constraints.DecimalMin.message","{label} deve ser maior ou igual a {value}");
		messages.put("javax.validation.constraints.Digits.message","{label} valor num\u00E9rico fora do limite (<{integer} d\u00EDgitos>.<{fraction} d\u00EDgitos> esperado)");
		messages.put("javax.validation.constraints.Future.message","{label} deve estar no futuro");
		messages.put("javax.validation.constraints.Max.message","{label} deve ser menor ou igual a {value}");
		messages.put("javax.validation.constraints.Min.message","{label} deve ser maior ou igual a {value}");
		messages.put("javax.validation.constraints.NotNull.message","{label} n\u00E3o pode ser nulo");
		messages.put("javax.validation.constraints.Null.message","{label} deve ser nulo");
		messages.put("javax.validation.constraints.Past.message","{label} deve estar no passado");
		messages.put("javax.validation.constraints.Pattern.message","{label} deve corresponder \u00E0 '{regexp}'");
		messages.put("javax.validation.constraints.Size.message","{label} tamanho deve estar entre {min} e {max}");

		messages.put("br.com.anteros.bean.validation.constraints.CreditCardNumber.message","n\u00FAmero de Cart\u00E3o de Cr\u00E9dito inv\u00E1lido");
		messages.put("br.com.anteros.bean.validation.constraints.Email.message","n\u00E3o \u00E9 um endere\u00E7o de e-mail");
		messages.put("br.com.anteros.bean.validation.constraints.Length.message","{label} tamanho deve estar entre {min} e {max}");
		messages.put("br.com.anteros.bean.validation.constraints.NotBlank.message","{label} n\u00E3o pode estar vazio");
		messages.put("br.com.anteros.bean.validation.constraints.NotEmpty.message","{label} n\u00E3o pode estar vazio");
		messages.put("br.com.anteros.bean.validation.constraints.Range.message","{label} deve estar entre {min} e {max}");
		messages.put("br.com.anteros.bean.validation.constraints.SafeHtml.message","{label} pode ter um conte\u00FAdo html inseguro");
		messages.put("br.com.anteros.bean.validation.constraints.ELAssert.message","express\u00E3o '{expression}' n\u00E3o retornou verdadeiro");
		messages.put("br.com.anteros.bean.validation.constraints.URL.message","{label} deve ser uma URL v\u00E1lida");
		messages.put("br.com.anteros.bean.validation.constraints.Required.message","O campo {label} \u00e9 requerido\u002c deve ter um valor\u002e");


		messages.put("br.com.anteros.bean.validation.constraints.CNPJ.message","N\u00e3o \u00e9 um CNPJ v\u00e1lido");
		messages.put("br.com.anteros.bean.validation.constraints.CPF.message","N\u00e3o \u00e9 um CPF v\u00e1lido");
		messages.put("br.com.anteros.bean.validation.constraints.IE.message","N\u00e3o \u00e9 uma Inscri\u00e7\u00e3o Estadual v\u00e1lida");
		messages.put("br.com.anteros.bean.validation.constraints.NIT.message","N\u00e3o \u00e9 um PIS\u002fNIT v\u00e1lido");
		messages.put("br.com.anteros.bean.validation.constraints.TituloEleitoral.message","N\u00e3o \u00e9 um Titulo Eleitoral v\u00e1lido");
		
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
