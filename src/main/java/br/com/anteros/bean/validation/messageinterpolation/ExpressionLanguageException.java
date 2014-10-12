package br.com.anteros.bean.validation.messageinterpolation;

public class ExpressionLanguageException extends RuntimeException {

	public ExpressionLanguageException() {
	}

	public ExpressionLanguageException(String message) {
		super(message);
	}

	public ExpressionLanguageException(Throwable cause) {
		super(cause);
	}

	public ExpressionLanguageException(String message, Throwable cause) {
		super(message, cause);
	}

}
