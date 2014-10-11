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
package br.com.anteros.bean.validation.constraints.validators;

import java.util.Iterator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import br.com.anteros.bean.validation.constraints.SafeHtml;

/**
 * Validate that the string does not contain malicious code.
 *
 * It uses <a href="http://www.jsoup.org">JSoup</a> as the underlying parser/sanitizer library.
 *
 * @author George Gastaldi
 * @author Hardy Ferentschik
 */
public class SafeHtmlValidator implements ConstraintValidator<SafeHtml, CharSequence> {
	private Whitelist whitelist;


	public void initialize(SafeHtml safeHtmlAnnotation) {
		switch ( safeHtmlAnnotation.whitelistType() ) {
			case BASIC:
				whitelist = Whitelist.basic();
				break;
			case BASIC_WITH_IMAGES:
				whitelist = Whitelist.basicWithImages();
				break;
			case NONE:
				whitelist = Whitelist.none();
				break;
			case RELAXED:
				whitelist = Whitelist.relaxed();
				break;
			case SIMPLE_TEXT:
				whitelist = Whitelist.simpleText();
				break;
		}
		whitelist.addTags( safeHtmlAnnotation.additionalTags() );

		for ( SafeHtml.Tag tag : safeHtmlAnnotation.additionalTagsWithAttributes() ) {
			whitelist.addAttributes( tag.name(), tag.attributes() );
		}
	}



	/**
	 * Returns a document whose {@code <body>} element contains the given HTML fragment.
	 */
	private Document getFragmentAsDocument(CharSequence value) {
		// using the XML parser ensures that all elements in the input are retained, also if they actually are not allowed at the given
		// location; E.g. a <td> element isn't allowed directly within the <body> element, so it would be used by the default HTML parser.
		// we need to retain it though to apply the given white list properly; See HV-873
		Document fragment = Jsoup.parse( value.toString(), "", Parser.xmlParser() );
		Document document = Document.createShell( "" );

		// add the fragment's nodes to the body of resulting document
		Iterator<Element> nodes = fragment.children().iterator();
		while ( nodes.hasNext() ) {
			document.body().appendChild( nodes.next() );
		}

		return document;
	}

	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}

		return new Cleaner( whitelist ).isValid( getFragmentAsDocument( value ) );
	}
}
