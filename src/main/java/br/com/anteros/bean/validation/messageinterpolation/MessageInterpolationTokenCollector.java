package br.com.anteros.bean.validation.messageinterpolation;

import java.util.Collections;
import java.util.List;

import br.com.anteros.core.utils.CollectionUtils;

/**
 * Used to creates a list of tokens from a message descriptor.
 *
 * @author Hardy Ferentschik
 * @see MessageInterpolationToken
 */
public class MessageInterpolationTokenCollector {
	public static final char BEGIN_TERM = '{';
	public static final char END_TERM = '}';
	public static final char EL_DESIGNATOR = '$';
	public static final char ESCAPE_CHARACTER = '\\';

	private final String originalMessageDescriptor;
	private final InterpolationTermType interpolationTermType;

	private List<MessageInterpolationToken> tokenList;
	private ParserState currentParserState;
	private int currentPosition;
	private MessageInterpolationToken currentToken;

	public MessageInterpolationTokenCollector(String originalMessageDescriptor, InterpolationTermType interpolationTermType)
			throws MessageDescriptorFormatException {
		this.originalMessageDescriptor = originalMessageDescriptor;
		this.interpolationTermType = interpolationTermType;
		this.currentParserState = new BeginState();
		this.tokenList = CollectionUtils.newArrayList();

		parse();
	}

	public void terminateToken() {
		if ( currentToken == null ) {
			return;
		}
		currentToken.terminate();
		tokenList.add( currentToken );
		currentToken = null;
	}

	public void appendToToken(char character) {
		if ( currentToken == null ) {
			currentToken = new MessageInterpolationToken( character );
		}
		else {
			currentToken.append( character );
		}
	}

	public void makeParameterToken() {
		currentToken.makeParameterToken();
	}

	public void makeELToken() {
		currentToken.makeELToken();
	}

	public void next() throws MessageDescriptorFormatException {
		if ( currentPosition == originalMessageDescriptor.length() ) {
			// give the current context the chance to complete
			currentParserState.terminate( this );
			return;
		}
		char currentCharacter = originalMessageDescriptor.charAt( currentPosition );
		currentPosition++;
		switch ( currentCharacter ) {
			case BEGIN_TERM: {
				currentParserState.handleBeginTerm( currentCharacter, this );
				break;
			}
			case END_TERM: {
				currentParserState.handleEndTerm( currentCharacter, this );
				break;
			}
			case EL_DESIGNATOR: {
				currentParserState.handleELDesignator( currentCharacter, this );
				break;
			}
			case ESCAPE_CHARACTER: {
				currentParserState.handleEscapeCharacter( currentCharacter, this );
				break;
			}
			default: {
				currentParserState.handleNonMetaCharacter( currentCharacter, this );
			}
		}
		// make sure the last token is terminated
		terminateToken();
	}

	public void parse() throws MessageDescriptorFormatException {
		currentParserState.start( this );
	}

	public void transitionState(ParserState newState) {
		currentParserState = newState;
	}

	public InterpolationTermType getInterpolationType() {
		return interpolationTermType;
	}

	public List<MessageInterpolationToken> getTokenList() {
		return Collections.unmodifiableList( tokenList );
	}

	public String getOriginalMessageDescriptor() {
		return originalMessageDescriptor;
	}
}

