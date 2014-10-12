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

import java.util.Formatter;
import java.util.Locale;

/**
 * A wrapper class for {@code java.util.Formatter#format} avoiding lookup problems in EL engines due to
 * ambiguous method resolution for {@code format}.
 *
 * @author Hardy Ferentschik
 */
public class FormatterWrapper {
	private final Formatter formatter;

	public FormatterWrapper(Locale locale) {
		this.formatter = new Formatter( locale );
	}

	public String format(String format, Object... args) {
		return formatter.format( format, args ).toString();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "FormatterWrapper" );
		sb.append( "{}" );
		return sb.toString();
	}
}


