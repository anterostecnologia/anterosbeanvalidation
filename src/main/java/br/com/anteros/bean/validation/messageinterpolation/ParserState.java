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

