package br.com.anteros.bean.validation.constraints.validators;

import static javax.script.ScriptContext.ENGINE_SCOPE;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.anteros.bean.validation.constraints.JsAssert;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.ConstraintValidatorContext;



public class JsAssertValidator implements ConstraintValidator<JsAssert, Object> {

    private final Logger LOG = LoggerFactory.getLogger(JsAssertValidator.class.getName());

    // Acts as a caching mechanism to compile every expression found in the annotation
    protected static Map<String, CompiledScript> cachedExpressions = new HashMap<>();

    protected ScriptEngine engine;
    protected ScriptContext currentContext;
    protected JsAssert annotation;

    @Override
    public void initialize(JsAssert constraintAnnotation) {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
        this.currentContext = engine.getContext();
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext validatorContext) {

        String attributeName = annotation.attributeName();
        String expression = annotation.value();

        CompiledScript expCompiled = cachedExpressions
                                        .get(expression);

        if (null == expCompiled) {
            // Expression is not compiled, it's evaluated for the first time
            TimeActionResponse<CompiledScript> ret = TimeAction.recordTimeAndDo(compile(expression));
            LOG.info("Script expression \"{}\" compiled in {} ms.", expression, ret.getTime());
            expCompiled = ret.getResponse();
            cachedExpressions.put(expression, expCompiled);
        }

        // The value is bind to engine context with the name 'value'
        currentContext
                .getBindings(ENGINE_SCOPE)
                .put(attributeName, value);

        try {
            // Evaluating the boolean expression included in the annotation.
            // Expression is validating the value.
            Object result = expCompiled.eval(currentContext);

            if (result instanceof Boolean) {
                return (Boolean) result;
            }

            throw new IllegalArgumentException("Expression: ```" + expression + "``` is not a boolean expression.");

        } catch (ScriptException e) {
            throw new IllegalArgumentException("Expression: ```" + expression + "``` failed to eval.", e);
        }
    }

    public Supplier<CompiledScript> compile(String expression) {
        return () -> {
            try {
                return ((Compilable) engine).compile(expression);
            } catch (ScriptException e) {
                throw new IllegalArgumentException("Expression: ```" + expression + "``` is invalid. Pre-compilation failed.", e);
            }
        };
    }
}
