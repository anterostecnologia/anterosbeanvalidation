/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *******************************************************************************/
package br.com.anteros.bean.validation;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.MessageInterpolator;

import br.com.anteros.bean.validation.constraints.ELAssert;
import br.com.anteros.bean.validation.messageinterpolation.InterpolationTerm;
import br.com.anteros.bean.validation.messageinterpolation.InterpolationTermType;
import br.com.anteros.bean.validation.messageinterpolation.MessageDescriptorFormatException;
import br.com.anteros.bean.validation.messageinterpolation.MessageInterpolationToken;
import br.com.anteros.bean.validation.messageinterpolation.MessageInterpolationTokenCollector;
import br.com.anteros.bean.validation.messageinterpolation.MessageInterpolationTokenIterator;
import br.com.anteros.bean.validation.resource.messages.AnterosValidationMessages;
import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;
import br.com.anteros.core.resource.messages.AnterosBundle;
import br.com.anteros.core.resource.messages.AnterosResourceBundle;
import br.com.anteros.core.utils.ArrayUtils;

/**
 * Description: Resource bundle backed message interpolator. This message resolver resolve message descriptors into
 * human-readable messages. It uses ResourceBundles to find the messages. This class is threadsafe.<br/>
 */
public class DefaultMessageInterpolator implements MessageInterpolator {
	private static final Logger log = LoggerProvider.getInstance().getLogger(DefaultMessageInterpolator.class.getName());
	public static final String DEFAULT_VALIDATION_MESSAGES = "ANTEROS_VALIDATION";
	public static final String USER_VALIDATION_MESSAGES = "USER_VALIDATION_MESSAGES";

	/** Regular expression used to do message interpolation. */
	private static final Pattern messageParameterPattern = Pattern.compile("(\\{[\\w\\.]+\\})");

	/** The default locale for the current user. */
	private Locale defaultLocale;

	/** User specified resource bundles hashed against their locale. */
	private final Map<Locale, AnterosBundle> userBundlesMap = new ConcurrentHashMap<Locale, AnterosBundle>();

	/** Builtin resource bundles hashed against their locale. */
	private final Map<Locale, AnterosBundle> defaultBundlesMap = new ConcurrentHashMap<Locale, AnterosBundle>();

	/**
	 * Create a new DefaultMessageInterpolator instance.
	 */
	public DefaultMessageInterpolator() {
		this(null);
	}

	/**
	 * Create a new DefaultMessageInterpolator instance.
	 * 
	 * @param resourceBundle
	 */
	public DefaultMessageInterpolator(AnterosBundle resourceBundle) {

		defaultLocale = Locale.getDefault();

		if (resourceBundle == null) {
			AnterosBundle bundle = AnterosResourceBundle.getBundle(USER_VALIDATION_MESSAGES, defaultLocale);
			if (bundle != null) {
				userBundlesMap.put(defaultLocale, bundle);
			}

		} else {
			userBundlesMap.put(defaultLocale, resourceBundle);
		}

		defaultBundlesMap.put(defaultLocale, AnterosResourceBundle.getBundle(DEFAULT_VALIDATION_MESSAGES, defaultLocale, AnterosValidationMessages.class));
	}

	/** {@inheritDoc} */
	public String interpolate(String message, Context context) {
		// probably no need for caching, but it could be done by parameters
		// since the map
		// is immutable and uniquely built per Validation definition, the
		// comparison has to be based on == and not equals though
		return interpolate(message, context, defaultLocale);
	}

	/** {@inheritDoc} */
	public String interpolate(String message, Context context, Locale locale) {
		return interpolateMessage(message, context.getConstraintDescriptor().getAttributes(), locale, context);
	}

	/**
	 * Runs the message interpolation according to algorithm specified in JSR 303. <br/>
	 * Note: <br/>
	 * Lookups in user bundles are recursive whereas lookups in default bundle are not!
	 *
	 * @param message
	 *            the message to interpolate
	 * @param annotationParameters
	 *            the parameters of the annotation for which to interpolate this message
	 * @param locale
	 *            the <code>Locale</code> to use for the resource bundle.
	 * @return the interpolated message.
	 */
	private String interpolateMessage(String message, Map<String, Object> annotationParameters, Locale locale, Context context) {
		AnterosBundle userResourceBundle = findUserResourceBundle(locale);
		AnterosBundle defaultResourceBundle = findDefaultResourceBundle(locale);

		String userBundleResolvedMessage;
		String resolvedMessage = message;
		boolean evaluatedDefaultBundleOnce = false;
		do {
			// search the user bundle recursive (step1)
			userBundleResolvedMessage = replaceVariables(resolvedMessage, userResourceBundle, locale, true);

			// exit condition - we have at least tried to validate against the
			// default bundle and there were no
			// further replacements
			if (evaluatedDefaultBundleOnce && !hasReplacementTakenPlace(userBundleResolvedMessage, resolvedMessage)) {
				break;
			}

			// search the default bundle non recursive (step2)
			resolvedMessage = replaceVariables(userBundleResolvedMessage, defaultResourceBundle, locale, false);

			evaluatedDefaultBundleOnce = true;
		} while (true);

		// resolve annotation attributes (step 4)
		resolvedMessage = replaceAnnotationAttributes(resolvedMessage, annotationParameters);

		// resolve custom message parameters (step 5)
		if (context instanceof GroupValidationContext) {
			GroupValidationContext<?> validationContext = ((GroupValidationContext<?>) context);
			resolvedMessage = replaceCustomMessageParameters(resolvedMessage, validationContext.getMessageParameters());
		}

		if (!(context.getConstraintDescriptor().getAnnotation() instanceof ELAssert)) {
			// resolve EL expressions (step 6)
			List<MessageInterpolationToken> tokens = null;
			MessageInterpolationTokenCollector tokenCollector;
			tokenCollector = new MessageInterpolationTokenCollector(resolvedMessage, InterpolationTermType.EL);
			tokens = tokenCollector.getTokenList();
			resolvedMessage = interpolateExpression(new MessageInterpolationTokenIterator(tokens), context, locale);
		}

		// curly braces need to be scaped in the original msg, so unescape them
		// now
		resolvedMessage = resolvedMessage.replace("\\{", "{").replace("\\}", "}").replace("\\\\", "\\");

		return resolvedMessage.trim();
	}

	private String interpolateExpression(MessageInterpolationTokenIterator tokenIterator, Context context, Locale locale)
			throws MessageDescriptorFormatException {
		while (tokenIterator.hasMoreInterpolationTerms()) {
			String term = tokenIterator.nextInterpolationTerm();

			String resolvedExpression = interpolate(context, locale, term);
			tokenIterator.replaceCurrentInterpolationTerm(resolvedExpression);
		}
		return tokenIterator.getInterpolatedMessage();
	}

	public String interpolate(Context context, Locale locale, String term) {
		InterpolationTerm expression = new InterpolationTerm(term, locale);
		return expression.interpolate(context);
	}

	private boolean hasReplacementTakenPlace(String origMessage, String newMessage) {
		return !origMessage.equals(newMessage);
	}

	private String replaceVariables(String message, AnterosBundle bundle, Locale locale, boolean recurse) {
		Matcher matcher = messageParameterPattern.matcher(message);
		StringBuffer sb = new StringBuffer(64);
		String resolvedParameterValue;
		while (matcher.find()) {
			String parameter = matcher.group(1);
			resolvedParameterValue = resolveParameter(parameter, bundle, locale, recurse);

			matcher.appendReplacement(sb, sanitizeForAppendReplacement(resolvedParameterValue));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String replaceAnnotationAttributes(String message, Map<String, Object> annotationParameters) {
		Matcher matcher = messageParameterPattern.matcher(message);
		StringBuffer sb = new StringBuffer(64);
		while (matcher.find()) {
			String resolvedParameterValue;
			String parameter = matcher.group(1);
			Object variable = annotationParameters.get(removeCurlyBrace(parameter));
			if (variable != null) {
				if (variable.getClass().isArray()) {
					resolvedParameterValue = ArrayUtils.toString((Object[]) variable);
				} else {
					resolvedParameterValue = variable.toString();
				}
			} else {
				resolvedParameterValue = parameter;
			}
			matcher.appendReplacement(sb, sanitizeForAppendReplacement(resolvedParameterValue));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String replaceCustomMessageParameters(String message, Map<String, Object> messageParameters) {
		Matcher matcher = messageParameterPattern.matcher(message);
		StringBuffer sb = new StringBuffer(64);
		while (matcher.find()) {
			String resolvedParameterValue;
			String parameter = matcher.group(1);
			Object variable = messageParameters.get(removeCurlyBrace(parameter));
			if (variable != null) {
				if (variable.getClass().isArray()) {
					resolvedParameterValue = ArrayUtils.toString((Object[]) variable);
				} else {
					resolvedParameterValue = variable.toString();
				}
			} else {
				resolvedParameterValue = parameter;
			}
			matcher.appendReplacement(sb, sanitizeForAppendReplacement(resolvedParameterValue));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String resolveParameter(String parameterName, AnterosBundle bundle, Locale locale, boolean recurse) {
		String parameterValue;
		try {
			if (bundle != null) {
				parameterValue = bundle.getMessage(removeCurlyBrace(parameterName));
				if (recurse) {
					parameterValue = replaceVariables(parameterValue, bundle, locale, recurse);
				}
			} else {
				parameterValue = parameterName;
			}
		} catch (MissingResourceException e) {
			// return parameter itself
			parameterValue = parameterName;
		}
		return parameterValue;
	}

	private String removeCurlyBrace(String parameter) {
		return parameter.substring(1, parameter.length() - 1);
	}

	private AnterosBundle findDefaultResourceBundle(Locale locale) {
		AnterosBundle bundle = defaultBundlesMap.get(locale);
		if (bundle == null) {
			bundle = AnterosResourceBundle.getBundle(DEFAULT_VALIDATION_MESSAGES, defaultLocale, AnterosValidationMessages.class);
			defaultBundlesMap.put(locale, bundle);
		}
		return bundle;
	}

	private AnterosBundle findUserResourceBundle(Locale locale) {
		AnterosBundle bundle = userBundlesMap.get(locale);
		if (bundle == null) {
			bundle = AnterosResourceBundle.getBundle(USER_VALIDATION_MESSAGES, defaultLocale);
			if (bundle != null) {
				userBundlesMap.put(locale, bundle);
			}
		}
		return bundle;
	}

	/**
	 * Set the default locale used by this {@link DefaultMessageInterpolator}.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		defaultLocale = locale;
	}

	/**
	 * Escapes the string to comply with {@link Matcher#appendReplacement(StringBuffer, String)} requirements.
	 *
	 * @param src
	 *            The original string.
	 * @return The sanitized string.
	 */
	private String sanitizeForAppendReplacement(String src) {
		return src.replace("\\", "\\\\").replace("$", "\\$");
	}

}
