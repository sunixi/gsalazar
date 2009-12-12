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
public @interface MetadataGenerator {

	/**
	 * DAO name to use to persist objects generated. By default it is ObjectTypeDAO.
	 *
	 * @return a spring DAO name.
	 */
	public String daoName() default StringHelper.EMPTY_STRING;

	/**
	 * Domain object dependencies for this generators. These dependencies
	 * will execute before this generator.
	 *
	 * @return domain object classes dependencies.
	 */
	public Class<?>[] dependencies() default {Class.class};

	/**
	 * Pages numbers to process in the data generator process.
	 * 
	 * @return a pages numbers array.
	 */
	public int[] pages() default {-1};

	public MetadataRow[] rows() default {};
}
