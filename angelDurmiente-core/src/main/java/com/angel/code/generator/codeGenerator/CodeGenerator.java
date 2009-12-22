/**
 * 
 */
package com.angel.code.generator.codeGenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.angel.code.generator.CodesGenerator;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class CodeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(CodesGenerator.class);
	protected static final String DEFAULT_BASE_SOURCES_DIRECTORY = "\\src\\main\\java\\";
	private String basePackage = "";
	private List<Class<?>> excludesDomains;
	private String baseSourcesDirectory;

	/**
	 * Generate all content code for a domain class.
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClass to generate its code.
	 */
	protected abstract void generateCodeFor(CodesGenerator generator, Class<?> domainClass);
	
	/**
	 * Initialize code generator to generate domain object code.
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClasses list with all domain object classes added (by user or reflection).
	 */
	public abstract void initializeCodeGenerator(CodesGenerator generator, List<Class<?>> domainClasses);
	
	/**
	 * Finalize code generator for domain class.
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClass which code was generated.
	 */
	protected abstract void finalizeCodeGenerator(CodesGenerator generator, Class<?> domainClass);

	public CodeGenerator(){
		super();
		this.setBaseSourcesDirectory(DEFAULT_BASE_SOURCES_DIRECTORY);
		this.setExcludesDomains(new ArrayList<Class<?>>());
	}

	public CodeGenerator(String basePackage){
		this();
		this.setBasePackage(basePackage);
	}
	
	/**
	 * @return the basePackage
	 */
	public String getBasePackage() {
		return basePackage;
	}

	/**
	 * @param basePackage the basePackage to set
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void excludeDomain(String domainClassName) {
		Class<?> domainClass = ReflectionHelper.getClassFrom(domainClassName);
		if(domainClass != null){
			this.getExcludesDomains().add(domainClass);
		}
	}

	public <T> void excludeDomain(Class<T> domainClass) {
		this.getExcludesDomains().add(domainClass);
	}

	/**
	 * @return the excludesDomains
	 */
	protected List<Class<?>> getExcludesDomains() {
		return excludesDomains;
	}

	/**
	 * @param excludesDomains the excludesDomains to set
	 */
	protected void setExcludesDomains(List<Class<?>> excludesDomains) {
		this.excludesDomains = excludesDomains;
	}

	protected boolean isExcludeDomainClass(Class<?> domainClass) {
		return this.getExcludesDomains().contains(domainClass);
	}

	/**
	 * Generate code to all domain classes added (by user or by reflection).
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClasses all list added.
	 */
	public void generateCode(CodesGenerator generator, List<Class<?>> domainClasses) {
		LOGGER.info("Begin process to generate code in [" + domainClasses.size() + "] domain classes with generator [" + this.getClass().getCanonicalName() + "].");
		List<Class<?>> domainObjectFiltered = this.filterDomainObjectClasses(domainClasses);
		for(Class<?> domainClass: domainObjectFiltered){
			LOGGER.info("Generating code for domain class [" + domainClass.getCanonicalName() + "].");
			this.generateCodeFor(generator, domainClass);
			LOGGER.info("Finishing genereting code for domain class [" + domainClass.getCanonicalName() + "].");
			this.finalizeCodeGenerator(generator, domainClass);
		}
	}

	/**
	 * Filter domain object classes, testing if this one is contained in exclude domain object classes.
	 * 
	 * @param domainClasses to test if applies filter.
	 * @return a domain object classes to generate its code.
	 */
	protected List<Class<?>> filterDomainObjectClasses(List<Class<?>> domainClasses) {
		LOGGER.info("Filtering domain object classes.");
		List<Class<?>> domainClassesFiltered = new ArrayList<Class<?>>();
		for(Class<?> domainClass: domainClasses){
			boolean notAppliesFilter = !this.appliesFilterFor(domainClass);
			LOGGER.info("Domain object class [" + domainClass.getSimpleName() + "] was " + 
					(notAppliesFilter ? "added to generate code." : "exclude to generate code."));
			if(notAppliesFilter){
				domainClassesFiltered.add(domainClass);
			}
		}
		return domainClassesFiltered;
	}

	/**
	 * Test if domain class is in exclude domain classes. 
	 * 
	 * @param domainClass to test if it applies filter.
	 * @return true if domain class is in exclude domain classes. Otherwise it returns false.
	 */
	protected boolean appliesFilterFor(Class<?> domainClass){
		return this.isExcludeDomainClass(domainClass);
	}

	/**
	 * @return the baseJavaSourcesDirectory
	 */
	public String getBaseSourcesDirectory() {
		return baseSourcesDirectory;
	}

	/**
	 * @param baseJavaSourcesDirectory the baseJavaSourcesDirectory to set
	 */
	public void setBaseSourcesDirectory(String baseSourcesDirectory) {
		this.baseSourcesDirectory = baseSourcesDirectory;
	}
	
	public boolean hasBasePackage(){
		return StringHelper.isNotEmpty(this.getBasePackage());
	}

}
