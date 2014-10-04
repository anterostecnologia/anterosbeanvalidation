package br.com.anteros.bean.validation;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

public class AnterosValidationProvider implements ValidationProvider<AnterosValidationConfiguration>{

	public AnterosValidationConfiguration createSpecializedConfiguration(BootstrapState state) {
		// TODO Auto-generated method stub
		return null;
	}

	public Configuration<?> createGenericConfiguration(BootstrapState state) {
		return new AnterosValidationConfigurationImpl();
	}

	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return new AnterosValidatorFactory();
	}

}
