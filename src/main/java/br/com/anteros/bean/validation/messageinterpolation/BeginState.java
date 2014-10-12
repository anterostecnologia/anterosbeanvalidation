/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.bean.validation.messageinterpolation;


public class BeginState implements ParserState {

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
