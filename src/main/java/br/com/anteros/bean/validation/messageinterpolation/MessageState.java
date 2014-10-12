package br.com.anteros.bean.validation.messageinterpolation;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;


public class MessageState implements ParserState {
	private static final Logger log = LoggerProvider.getInstance().getLogger(MessageState.class);


	public void start(MessageInterpolationTokenCollector tokenCollector) {
		throw new IllegalStateException( "The parsing of the message descriptor cannot start in this state." );
	}

	public void terminate(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.terminateToken();
	}

	public void handleNonMetaCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );
		tokenCollector.next();
	}

	public void handleBeginTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.terminateToken();

		tokenCollector.appendToToken( character );
		if ( tokenCollector.getInterpolationType().equals( InterpolationTermType.PARAMETER ) ) {
			tokenCollector.makeParameterToken();
		}
		tokenCollector.transitionState( new InterpolationTermState() );
		tokenCollector.next();
	}


	public void handleEndTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		throw new NonTerminatedParameterException("The message descriptor "+tokenCollector.getOriginalMessageDescriptor()+" contains an unbalanced meta character "+character+" parameter.");
	}


	public void handleEscapeCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );

		tokenCollector.transitionState( new EscapedState( this ) );
		tokenCollector.next();
	}

	public void handleELDesignator(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		if ( tokenCollector.getInterpolationType().equals( InterpolationTermType.PARAMETER ) ) {
			handleNonMetaCharacter( character, tokenCollector );
		}
		else {
			tokenCollector.transitionState( new ELState() );
			tokenCollector.next();
		}
	}
}


