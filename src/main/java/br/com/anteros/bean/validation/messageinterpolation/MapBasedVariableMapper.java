package br.com.anteros.bean.validation.messageinterpolation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.el.VariableMapper;


public class MapBasedVariableMapper extends VariableMapper {
	private Map<String, ValueExpression> map = Collections.emptyMap();

	@Override
	public ValueExpression resolveVariable(String variable) {
		return map.get( variable );
	}

	@Override
	public ValueExpression setVariable(String variable, ValueExpression expression) {
		if ( map.isEmpty() ) {
			map = new HashMap<String, ValueExpression>();
		}
		return map.put( variable, expression );
	}
}


