package br.com.anteros.bean.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.Comparator;

import br.com.anteros.bean.validation.constraints.validators.LessThanValidator;
import br.com.anteros.validation.api.Constraint;
import br.com.anteros.validation.api.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LessThanValidator.class)
public @interface LessThan {
	
	String message() default "{br.com.anteros.bean.validation.constraints.LessThan.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String lessThan();

    Class<? extends Comparator<Object>> comparator() default DefaultComparator.class;

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        LessThan [] value();
    }
    
    public class DefaultComparator implements Comparator<Object> {

    	@Override
    	public int compare(Object o1, Object o2) {
    		if (o1 instanceof Number) {
    			BigDecimal b1 = new BigDecimal(((Number)o1).doubleValue());
    		    BigDecimal b2 = new BigDecimal(((Number)o2).doubleValue());
    		    return b1.compareTo(b2);
    		} else if (o1 instanceof String) {
    			return ((String)o1).compareTo(((String)o2));
    		}
    		return 0;
    	}
    	
    }
}
