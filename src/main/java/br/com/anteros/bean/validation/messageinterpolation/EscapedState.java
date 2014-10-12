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



