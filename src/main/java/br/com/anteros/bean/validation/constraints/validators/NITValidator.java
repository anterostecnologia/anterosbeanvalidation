package br.com.anteros.bean.validation.constraints.validators;

import br.com.anteros.bean.validation.constraints.NIT;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;

/**
 * Valida a cadeia gerada através do método {@linkplain #toString()} para
 * verificar se ela está de acordo com o padrão de um NIT. O padrão NIT é o
 * mesmo utilizado no PIS, PASEP e o SUS.
 * 
 * @author Fabio Kung
 * @author Leonardo Bessa
 */
public class NITValidator implements ConstraintValidator<NIT, String> {
	private br.com.caelum.stella.validation.NITValidator stellaValidator;

	public void initialize(NIT nit) {
		AnnotationMessageProducer messageProducer = new AnnotationMessageProducer(
				nit);
		stellaValidator = new br.com.caelum.stella.validation.NITValidator(messageProducer, nit.formatted());
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
