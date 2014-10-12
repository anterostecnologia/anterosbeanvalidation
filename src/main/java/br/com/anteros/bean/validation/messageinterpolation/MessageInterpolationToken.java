package br.com.anteros.bean.validation.messageinterpolation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageInterpolationToken {
	private static final Pattern ESCAPED_OPENING_CURLY_BRACE = Pattern.compile( "\\\\\\{" );
	private static final Pattern ESCAPED_CLOSING_CURLY_BRACE = Pattern.compile( "\\\\\\}" );

	private boolean isParameter;
	private boolean isEL;
	private boolean terminated;
	private String value;

	private StringBuilder builder;

	public MessageInterpolationToken(String tokenStart) {
		builder = new StringBuilder();
		builder.append( tokenStart );
	}

	public MessageInterpolationToken(char tokenStart) {
		this( String.valueOf( tokenStart ) );
	}

	public void append(char character) {
		builder.append( character );
	}

	public void makeParameterToken() {
		isParameter = true;
	}

	public void makeELToken() {
		makeParameterToken();
		isEL = true;
	}

	public void terminate() {
		value = builder.toString();
		if ( isEL ) {
			Matcher matcher = ESCAPED_OPENING_CURLY_BRACE.matcher( value );
			value = matcher.replaceAll( "{" );

			matcher = ESCAPED_CLOSING_CURLY_BRACE.matcher( value );
			value = matcher.replaceAll( "}" );
		}
		builder = null;
		terminated = true;
	}

	public boolean isParameter() {
		return isParameter;
	}

	public String getTokenValue() {
		if ( !terminated ) {
			throw new IllegalStateException( "Trying to retrieve token value for unterminated token" );
		}
		return value;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "Token{" );
		sb.append( "value='" ).append( value ).append( '\'' );
		sb.append( ", terminated=" ).append( terminated );
		sb.append( ", isEL=" ).append( isEL );
		sb.append( ", isParameter=" ).append( isParameter );
		sb.append( '}' );
		return sb.toString();
	}
}


