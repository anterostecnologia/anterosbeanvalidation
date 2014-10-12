package br.com.anteros.bean.validation.messageinterpolation;

import javax.validation.MessageInterpolator;


public interface TermResolver {

	/**
	 * Interpolates given term based on the constraint validation context.
	 * 
	 * @param term the message to interpolate
	 * @param context contextual information related to the interpolation
	 * 
	 * @return interpolated message
	 */
	String interpolate(MessageInterpolator.Context context, String term);
}
