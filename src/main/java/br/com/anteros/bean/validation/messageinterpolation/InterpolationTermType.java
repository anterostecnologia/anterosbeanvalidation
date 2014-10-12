package br.com.anteros.bean.validation.messageinterpolation;

/**
 * Describes the type of the interpolation term.
 *
 */
public enum InterpolationTermType {
	/**
	 * EL message expression, eg ${foo}.
	 */
	EL,

	/**
	 * Message parameter, eg {foo}.
	 */
	PARAMETER
}
