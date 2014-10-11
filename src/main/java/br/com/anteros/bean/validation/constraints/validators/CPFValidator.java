package br.com.anteros.bean.validation.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.anteros.bean.validation.constraints.CPF;

/**
 * Valida a cadeia gerada através do método {@linkplain #toString()} para
 * verificar se ela está de acordo com o padrão de CPF.
 * 
 * @author Fabio Kung
 * @author Leonardo Bessa
 * @author David Paniz
 */
public class CPFValidator implements ConstraintValidator<CPF, String> {
	private br.com.caelum.stella.validation.CPFValidator stellaValidator;


	public void initialize(CPF cpf) {
		AnnotationMessageProducer messageProducer = new AnnotationMessageProducer(cpf);
		stellaValidator = new br.com.caelum.stella.validation.CPFValidator(messageProducer, cpf.formatted(),cpf.ignoreRepeated());
	}

	public boolean isValid(String cpf, ConstraintValidatorContext context) {
		if (cpf != null) {
			if (cpf.trim().length() == 0) {
				return true;
			} else {
				return stellaValidator.invalidMessagesFor(cpf).isEmpty();
			}
		} else {
			return true;
		}
	}
}
