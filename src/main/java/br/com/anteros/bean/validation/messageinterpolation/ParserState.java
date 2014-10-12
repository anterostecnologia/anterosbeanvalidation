package br.com.anteros.bean.validation.messageinterpolation;

/**
 * Interface defining the different methods a parser state has to respond to. It is up to the implementing state
 * to decide how to handle the different life cycle and callback methods
 */
public interface ParserState {
	void start(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;

	void terminate(MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;

	void handleNonMetaCharacter(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;

	void handleBeginTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;

	void handleEndTerm(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;

	void handleEscapeCharacter(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;

	void handleELDesignator(char character, MessageInterpolationTokenCollector tokenCollector) throws MessageDescriptorFormatException;
}

