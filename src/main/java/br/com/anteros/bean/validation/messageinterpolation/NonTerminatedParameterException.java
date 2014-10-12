package br.com.anteros.bean.validation.messageinterpolation;

public class NonTerminatedParameterException extends RuntimeException {

	public NonTerminatedParameterException() {
	}

	public NonTerminatedParameterException(String message) {
		super(message);
	}

	public NonTerminatedParameterException(Throwable cause) {
		super(cause);
	}

	public NonTerminatedParameterException(String message, Throwable cause) {
		super(message, cause);
	}

}
