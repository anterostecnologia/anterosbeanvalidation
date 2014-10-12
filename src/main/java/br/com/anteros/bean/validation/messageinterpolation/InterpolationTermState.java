package br.com.anteros.bean.validation.messageinterpolation;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

/**
 * @author Hardy Ferentschik
 */
public class InterpolationTermState implements ParserState {
	private static final Logger log = LoggerProvider.getInstance().getLogger(InterpolationTermState.class);


	public void start(MessageInterpolationTokenCollector tokenCollector) {
		throw new IllegalStateException( "Parsing of message descriptor cannot start in this state" );
	}

	public void terminate(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		throw new NonTerminatedParameterException("The message descriptor "+tokenCollector.getOriginalMessageDescriptor()+" contains an unbalanced meta character "+MessageInterpolationTokenCollector.BEGIN_TERM+" parameter.");
	}


	public void handleNonMetaCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );
		tokenCollector.next();
	}


	public void handleBeginTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		throw new NestedParameterException("The message descriptor "+tokenCollector.getOriginalMessageDescriptor()+" has nested parameters.");
	}


	public void handleEndTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );
		tokenCollector.terminateToken();
		BeginState beginState = new BeginState();
		tokenCollector.transitionState( beginState );
		tokenCollector.next();
	}


	public void handleEscapeCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );
		ParserState state = new EscapedState( this );
		tokenCollector.transitionState( state );
		tokenCollector.next();

	}

	public void handleELDesignator(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );
		tokenCollector.next();
	}
}


