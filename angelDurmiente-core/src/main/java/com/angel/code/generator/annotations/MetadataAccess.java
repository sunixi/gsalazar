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
@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
public @interface MetadataAccess {

	/**
	 * Properties names to create an access strategy.
	 * They can be inherit properties names, or current type properties names.
	 * 
	 * @return properties names to create an access strategy.
	 */
	public String[] properties();

	/**
	 * Test if this access strategy (with these properties names)  have an unique result.
	 * By default it is false.
	 * 
	 * @return true if this access strategy has a unique result, otherwise it returns false.
	 */
	public boolean uniqueResult() default false;

	/**
	 * Test if this access strategy (with these properties names) have an optional result.
	 * By default it is false.
	 * 
	 * @return true if this access strategy has an optional result. Otherwise it returns false.
	 */
	public boolean optionResult() default false;

}
