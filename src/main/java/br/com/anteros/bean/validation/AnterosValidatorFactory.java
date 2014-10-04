package br.com.anteros.bean.validation;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public class AnterosValidatorFactory implements ValidatorFactory, Cloneable {

	public Validator getValidator() {
		return new AnterosValidator();
	}

	public ValidatorContext usingContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageInterpolator getMessageInterpolator() {
		// TODO Auto-generated method stub
		return null;
	}

	public TraversableResolver getTraversableResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConstraintValidatorFactory getConstraintValidatorFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T unwrap(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
