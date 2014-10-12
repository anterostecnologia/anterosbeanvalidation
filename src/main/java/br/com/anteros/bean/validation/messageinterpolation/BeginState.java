package br.com.anteros.bean.validation.messageinterpolation;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

public class BeginState implements ParserState {
	private static final Logger log = LoggerProvider.getInstance().getLogger(BeginState.class);

	public void terminate(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
	}

	public void start(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException {
		tokenCollector.next();
	}

	public void handleNonMetaCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken(character);
		tokenCollector.transitionState(new MessageState());
		tokenCollector.next();
	}

	public void handleBeginTerm(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		// terminate a potential current token prior to the beginning of a new
		// term
		tokenCollector.terminateToken();

		tokenCollector.appendToToken(character);
		if (tokenCollector.getInterpolationType().equals(InterpolationTermType.PARAMETER)) {
			tokenCollector.makeParameterToken();
		}
		tokenCollector.transitionState(new InterpolationTermState());
		tokenCollector.next();
	}

	public void handleEndTerm(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		throw new MessageDescriptorFormatException("The message descriptor "
				+ tokenCollector.getOriginalMessageDescriptor() + " contains an unbalanced meta character " + character
				+ " parameter.");
	}

	public void handleEscapeCharacter(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		tokenCollector.appendToToken(character);
		tokenCollector.transitionState(new EscapedState(this));
		tokenCollector.next();
	}

	public void handleELDesignator(char character, MessageInterpolationTokenCollector tokenCollector)
			throws MessageDescriptorFormatException {
		if (tokenCollector.getInterpolationType().equals(InterpolationTermType.PARAMETER)) {
			handleNonMetaCharacter(character, tokenCollector);
		} else {
			ParserState state = new ELState();
			tokenCollector.transitionState(state);
			tokenCollector.next();
		}
	}
}
