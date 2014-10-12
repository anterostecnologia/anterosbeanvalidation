package br.com.anteros.bean.validation.messageinterpolation;


public class EscapedState implements ParserState {
	ParserState previousState;

	public EscapedState(ParserState previousState) {
		this.previousState = previousState;
	}


	public void start(MessageInterpolationTokenCollector tokenCollector) {
		throw new IllegalStateException( "Parsing of message descriptor cannot start in this state" );
	}

	public void terminate(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.terminateToken();
	}


	public void handleNonMetaCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		handleEscapedCharacter( character, tokenCollector );
	}


	public void handleBeginTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		handleEscapedCharacter( character, tokenCollector );
	}


	public void handleEndTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		handleEscapedCharacter( character, tokenCollector );
	}


	public void handleEscapeCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		handleEscapedCharacter( character, tokenCollector );
	}


	public void handleELDesignator(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		handleEscapedCharacter( character, tokenCollector );
	}

	private void handleEscapedCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken( character );
		tokenCollector.transitionState( previousState );
		tokenCollector.next();
	}
}



