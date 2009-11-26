/**
 * 
 */
package com.angel.object.generator;

import java.util.ArrayList;
import java.util.List;

import com.angel.object.generator.classGenerator.ClassGenerator;

/**
 * @author Guillermo D. Salazar
 *
 */
public class Generator {

	private String baseProjectPackage;
	private List<Class<?>> domainClasses;
	private List<ClassGenerator> classesGenerators;

	/**
	 * 
	 */
	public Generator() {
		super();
		this.setClassesGenerators(new ArrayList<ClassGenerator>());
		this.setDomainClasses(new ArrayList<Class<?>>());
	}

	public Generator(String baseProjectPackage) {
		this();
		this.setBaseProjectPackage(baseProjectPackage);
	}

	/**
	 * @return the baseProjectPackage
	 */
	public String getBaseProjectPackage() {
		return baseProjectPackage;
	}

	/**
	 * @param baseProjectPackage the baseProjectPackage to set
	 */
	public void setBaseProjectPackage(String baseProjectPackage) {
		this.baseProjectPackage = baseProjectPackage;
	}

	public void addClassGenerator(ClassGenerator classGenerator) {
		this.getClassesGenerators().add(classGenerator);
	}

	public <T> void addDomain(Class<T> domainClass) {
		this.getDomainClasses().add(domainClass);
	}

	public void generateClasses() {
		for(ClassGenerator classGenerator : this.getClassesGenerators()){
			for(Class<?> domainClass : this.getDomainClasses()){
				if(!classGenerator.isExcludeDomainClass(domainClass)){
					this.processDomainClass(domainClass, classGenerator);
				}
			}
		}
	}
	
	protected void processDomainClass(Class<?> domainClass, ClassGenerator classGenerator){
		classGenerator.generateClass(this, domainClass);
	}

	/**
	 * @return the domainClasses
	 */
	protected List<Class<?>> getDomainClasses() {
		return domainClasses;
	}

	/**
	 * @param domainClasses the domainClasses to set
	 */
	protected void setDomainClasses(List<Class<?>> domainClasses) {
		this.domainClasses = domainClasses;
	}

	/**
	 * @return the classesGenerators
	 */
	protected List<ClassGenerator> getClassesGenerators() {
		return classesGenerators;
	}

	/**
	 * @param classesGenerators the classesGenerators to set
	 */
	protected void setClassesGenerators(List<ClassGenerator> classesGenerators) {
		this.classesGenerators = classesGenerators;
	}
}
