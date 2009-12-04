/**
 *
 */
package com.angel.code.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author William
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Accesor {

	/**
	 * Indicates if this property has an unique value for each object.
	 * By default it is false.
	 * 
	 * @return if this property has an unique value for each object. Otherwise it returns false.
	 */
	public boolean unique() default false;

	/**
	 * Indicates if this property can be null. By default it is false.
	 *  
	 * @return if this property can be null or not.
	 */
	public boolean optional() default false;
}
