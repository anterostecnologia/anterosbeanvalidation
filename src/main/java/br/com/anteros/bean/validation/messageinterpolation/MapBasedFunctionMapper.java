package br.com.anteros.bean.validation.messageinterpolation;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.el.FunctionMapper;

/**
 * @author Hardy Ferentschik
 */
public class MapBasedFunctionMapper extends FunctionMapper {
	private static final String FUNCTION_NAME_SEPARATOR = ":";
	private Map<String, Method> map = Collections.emptyMap();

	@Override
	public Method resolveFunction(String prefix, String localName) {
		return map.get( prefix + FUNCTION_NAME_SEPARATOR + localName );
	}

	public void setFunction(String prefix, String localName, Method method) {
		if ( map.isEmpty() ) {
			map = new HashMap<String, Method>();
		}
		map.put( prefix + FUNCTION_NAME_SEPARATOR + localName, method );
	}
}

