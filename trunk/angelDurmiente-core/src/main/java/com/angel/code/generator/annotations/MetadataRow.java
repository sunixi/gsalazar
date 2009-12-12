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
@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
public @interface MetadataRow {

	/**
	 * Property name to add to data generator process.
	 * 
	 * @return a property name from this current object.
	 */
	public String propertyName();

	/**
	 * Aliases added to this property name.
	 * 
	 * @return an aliases array.
	 */
	public String[] aliases() default StringHelper.EMPTY_STRING;

}
