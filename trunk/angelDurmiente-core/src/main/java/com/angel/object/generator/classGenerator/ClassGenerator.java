/**
 * 
 */
package com.angel.object.generator.classGenerator;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.Generator;
import com.angel.object.generator.classNameStrategies.ClassNameStrategy;
import com.angel.object.generator.fileGenerator.FileGenerator;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class ClassGenerator {

	private String basePackage = "";
	private ClassNameStrategy classNameStrategy;
	private boolean includeSuperClass = false;
	private List<Class<?>> excludesDomains;
	private FileGenerator fileGenerator;
	
	public ClassGenerator(){
		super();
		this.setExcludesDomains(new ArrayList<Class<?>>());		
	}
	
	public ClassGenerator(String basePackage){
		this();
		this.setBasePackage(basePackage);
	}
	
	public ClassGenerator(String basePackage, boolean includeSuperClass){
		this(basePackage);
		this.setIncludeSuperClass(includeSuperClass);
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

	/**
	 * @return the classNameStrategy
	 */
	public ClassNameStrategy getClassNameStrategy() {
		return classNameStrategy;
	}

	/**
	 * @param classNameStrategy the classNameStrategy to set
	 */
	public void setClassNameStrategy(ClassNameStrategy classNameStrategy) {
		this.classNameStrategy = classNameStrategy;
	}

	public <T> void excludeDomain(Class<T> domainClass) {
		this.getExcludesDomains().add(domainClass);
	}

	/**
	 * @return the includeSuperClass
	 */
	public boolean isIncludeSuperClass() {
		return includeSuperClass;
	}

	/**
	 * @param includeSuperClass the includeSuperClass to set
	 */
	public void setIncludeSuperClass(boolean includeSuperClass) {
		this.includeSuperClass = includeSuperClass;
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

	public boolean isExcludeDomainClass(Class<?> domainClass) {
		return this.getExcludesDomains().contains(domainClass);
	}

	public void generateClass(Generator generator, Class<?> domainClass) {
		this.doGenerateClass(generator, domainClass);
	}
	
	protected abstract void doGenerateClass(Generator generator, Class<?> domainClass);

	/**
	 * @return the fileGenerator
	 */
	public FileGenerator getFileGenerator() {
		return fileGenerator;
	}

	/**
	 * @param fileGenerator the fileGenerator to set
	 */
	public void setFileGenerator(FileGenerator fileGenerator) {
		this.fileGenerator = fileGenerator;
	}
}
