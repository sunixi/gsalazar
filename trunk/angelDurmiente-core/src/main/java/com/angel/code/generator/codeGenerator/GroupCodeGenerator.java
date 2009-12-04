/**
 * 
 */
package com.angel.code.generator.codeGenerator;

import java.util.List;

import org.apache.log4j.Logger;

import com.angel.code.generator.CodesGenerator;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class GroupCodeGenerator extends CodeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(GroupCodeGenerator.class);

	/**
	 * Generate code in one source for all domain object classes.
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClasses all list filtered.
	 */
	protected abstract void generateCodeFor(CodesGenerator generator, List<Class<?>> domainClasses);
	
	public GroupCodeGenerator(){
		super();
	}

	public GroupCodeGenerator(String basePackage){
		super(basePackage);
	}

	/**
	 * Generate code to all domain classes added (by user or by reflection).
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClasses all list added.
	 */
	@Override
	public void generateCode(CodesGenerator generator, List<Class<?>> domainClasses) {
		LOGGER.info("Begin process to generate code in [" + domainClasses.size() + "] domain classes.");
		List<Class<?>> domainObjectFiltered = this.filterDomainObjectClasses(domainClasses);
		this.generateCodeFor(generator, domainObjectFiltered);
	}

	/**
	 * Throw new RuntimeException because this functionality isn't been used in this class.
	 */
	protected void generateCodeFor(CodesGenerator generator, Class<?> domainClass){
		throw new RuntimeException("This functionality isn't been used.");
	}
}
