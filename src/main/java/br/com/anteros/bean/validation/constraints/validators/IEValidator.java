package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.IE;
import br.com.anteros.core.utils.ReflectionUtils;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;
import br.com.caelum.stella.type.Estado;

/**
 * Valida a cadeia gerada através do método {@linkplain #toString()} para
 * verificar se ela está de acordo com o padrão de Inscricao Estadual, no estado
 * epecificado.
 * 
 * @author Leonardo Bessa
 */
public class IEValidator implements ConstraintValidator<IE, Object> {

	private br.com.caelum.stella.validation.Validator<String> stellaValidator;

	private IE ie;	

	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value != null) {
			String ieValue = getIEValue(value);
			String estadoValue = getEstadoValue(value);
			final AnnotationMessageProducer annotationMessageProducer = new AnnotationMessageProducer(
					ie);
			if (ieValue.trim().length() == 0) {
				return true;
			} else {
				try {
					final Estado estado = Estado.valueOf(estadoValue);
					stellaValidator = estado.getIEValidator(
							annotationMessageProducer, ie.formatted());
				} catch (IllegalArgumentException e) {
					return false;
				}
				return stellaValidator.invalidMessagesFor(ieValue).isEmpty();
			}
		} else {
			return true;
		}
	}

	public void initialize(IE ie) {
		this.ie = ie;
	}

	private String getEstadoValue(final Object obj) {		
		try {
			return ReflectionUtils.getFieldValueByName(obj, ie.estadoField()).toString();
		} catch (Exception e) {
		}
		return "";
	}

	private String getIEValue(final Object obj)  {
		try {
			return ReflectionUtils.getFieldValueByName(obj, ie.ieField()).toString();
		} catch (Exception e) {
		}
		return "";
	}


}
