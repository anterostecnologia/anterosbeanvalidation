package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.TituloEleitoral;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

/**
 * Valida a cadeia gerada através do método {@linkplain #toString()} para
 * verificar se ela está de acordo com o padrão de Título Eleitoral.
 * 
 * @author Leonardo Bessa
 */
public class TituloEleitoralValidator implements
		ConstraintValidator<TituloEleitoral, String> {
	private br.com.caelum.stella.validation.TituloEleitoralValidator stellaValidator;

	public void initialize(TituloEleitoral tituloEleitoral) {
		AnnotationMessageProducer messageProducer = new AnnotationMessageProducer(
				tituloEleitoral);
		stellaValidator = new br.com.caelum.stella.validation.TituloEleitoralValidator(messageProducer);
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value != null) {
			if (value.trim().length() == 0) {
				return true;
			} else {
				return stellaValidator.invalidMessagesFor(value).isEmpty();
			}
		} else {
			return true;
		}
	}
}
