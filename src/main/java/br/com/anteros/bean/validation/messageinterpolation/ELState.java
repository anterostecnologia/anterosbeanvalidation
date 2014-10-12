package br.com.anteros.bean.validation.messageinterpolation;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;


public class ELState implements ParserState {
	private static final Logger log = LoggerProvider.getInstance().getLogger(ELState.class);

	
	public void start(MessageInterpolationTokenCollector tokenCollector) {
		throw new IllegalStateException( "Parsing of message descriptor cannot start in this state" );
	}


	public void terminate(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( MessageInterpolationTokenCollector.EL_DESIGNATOR );
		tokenCollector.terminateToken();
	}


	public void handleNonMetaCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( MessageInterpolationTokenCollector.EL_DESIGNATOR );
		tokenCollector.appendToToken( character );
		tokenCollector.terminateToken();
		tokenCollector.transitionState( new BeginState() );
		tokenCollector.next();
	}


	public void handleBeginTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.terminateToken();

		tokenCollector.appendToToken( MessageInterpolationTokenCollector.EL_DESIGNATOR );
		tokenCollector.appendToToken( character );
		tokenCollector.makeELToken();
		tokenCollector.transitionState( new InterpolationTermState() );
		tokenCollector.next();
	}

	public void handleEndTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		throw new MessageDescriptorFormatException("The message descriptor "
				+ tokenCollector.getOriginalMessageDescriptor() + " contains an unbalanced meta character " + character
				+ " parameter.");
	}


	public void handleEscapeCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.transitionState( new EscapedState( this ) );
		tokenCollector.next();
	}


	public void handleELDesignator(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		handleNonMetaCharacter( character, tokenCollector );
	}
}


