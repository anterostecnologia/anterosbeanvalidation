package br.com.anteros.bean.validation.messageinterpolation;

public class NestedParameterException extends RuntimeException {

	public NestedParameterException() {
	}

	public NestedParameterException(String message) {
		super(message);
	}

	public NestedParameterException(Throwable cause) {
		super(cause);
	}

	public NestedParameterException(String message, Throwable cause) {
		super(message, cause);
	}

}
