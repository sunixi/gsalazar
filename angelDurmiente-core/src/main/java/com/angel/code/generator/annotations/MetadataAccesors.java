/**
 *
 */
package com.angel.code.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.angel.common.helpers.StringHelper;

/**
 * @author William
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface MetadataAccesors {

	/**
	 * Properties to exclude from access through Services and DAOs implementation.
	 * 
	 * @return properties names to excludes.
	 */
	public String[] excludeProperties() default StringHelper.EMPTY_STRING;

	/**
	 * Properties to include in access through Services and DAOs implementations.
	 * It is used to added inherit super classes properties.
	 * 
	 * @return properties names to include.
	 */
	public String[] includeProperties() default StringHelper.EMPTY_STRING;

	/**
	 * Accessories strategies to add in Services and DAOs implementation.
	 * 
	 * @return metadata access strategies.
	 */
	public MetadataAccess[] accessStrategies() default {};
}
