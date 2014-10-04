package br.com.anteros.bean.validation;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ConfigurationState;

public class AnterosValidationConfigurationImpl implements AnterosValidationConfiguration, ConfigurationState{

	public AnterosValidationConfiguration ignoreXmlConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public AnterosValidationConfiguration messageInterpolator(MessageInterpolator interpolator) {
		// TODO Auto-generated method stub
		return null;
	}

	public AnterosValidationConfiguration traversableResolver(TraversableResolver resolver) {
		// TODO Auto-generated method stub
		return null;
	}

	public AnterosValidationConfiguration constraintValidatorFactory(
			ConstraintValidatorFactory constraintValidatorFactory) {
		// TODO Auto-generated method stub
		return null;
	}

	public AnterosValidationConfiguration addMapping(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

	public AnterosValidationConfiguration addProperty(String name, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageInterpolator getDefaultMessageInterpolator() {
		// TODO Auto-generated method stub
		return null;
	}

	public TraversableResolver getDefaultTraversableResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public ValidatorFactory buildValidatorFactory() {
		return new AnterosValidatorFactory();
	}

	public boolean isIgnoreXmlConfiguration() {
		// TODO Auto-generated method stub
		return false;
	}

	public MessageInterpolator getMessageInterpolator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<InputStream> getMappingStreams() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConstraintValidatorFactory getConstraintValidatorFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public TraversableResolver getTraversableResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

}
