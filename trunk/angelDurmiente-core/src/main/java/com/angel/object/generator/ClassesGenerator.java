/**
 * 
 */
package com.angel.object.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.angel.object.generator.classGenerator.ClassGenerator;

/**
 * @author Guillermo D. Salazar
 *
 */
public class ClassesGenerator {

	private String baseProjectPackage;
	private List<Class<?>> domainClasses;
	private List<ClassGenerator> classesGenerators;
	private Map<String, String> globalImports;
	private Map<String, String> tagsComment;

	/**
	 * 
	 */
	public ClassesGenerator() {
		super();
		this.setClassesGenerators(new ArrayList<ClassGenerator>());
		this.setDomainClasses(new ArrayList<Class<?>>());
		this.setGlobalImports(new HashMap<String, String>());
		this.setTagsComment(new HashMap<String, String>());
	}

	public ClassesGenerator(String baseProjectPackage) {
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
					if(classGenerator.hasInterfaceClassGenerator()){
						this.addTagComments(classGenerator.getInterfaceClassGenerator());
						this.processDomainClass(domainClass, classGenerator.getInterfaceClassGenerator());	
					}
					this.addTagComments(classGenerator);
					this.processDomainClass(domainClass, classGenerator);
				}
			}
		}
	}
	
	protected void addTagComments(ClassGenerator classGenerator){
		for(String tag: this.getTagsComment().keySet()){
			classGenerator.addTagComment(tag, this.getTagsComment().get(tag));
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
	
	public void addRelativeImport(String basePackage, String beanClassName){
		String packageName = this.getBaseProjectPackage() + "." + basePackage + "." + beanClassName;
		this.getGlobalImports().put(packageName, beanClassName);
	}
	
	public void addGlobalImport(String basePackage, String beanClassName){
		String packageName = basePackage + "." + beanClassName;
		this.getGlobalImports().put(packageName, beanClassName);
	}
	
	public boolean containsImportForClassName(String beanClassName){
		for(String packageName: this.getGlobalImports().keySet()){
			String beanName = this.getGlobalImports().get(packageName);
			if(beanClassName.equalsIgnoreCase(beanName)){
				return true;
			}
		}
		return false;
	}
	
	public String getImportForClassName(String beanClassName){
		for(String packageName: this.getGlobalImports().keySet()){
			String beanName = this.getGlobalImports().get(packageName);
			if(beanClassName.equalsIgnoreCase(beanName)){
				return packageName;
			}
		}
		return null;
	}

	/**
	 * @return the globalImports
	 */
	protected Map<String, String> getGlobalImports() {
		return globalImports;
	}

	/**
	 * @param globalImports the globalImports to set
	 */
	protected void setGlobalImports(Map<String, String> globalImports) {
		this.globalImports = globalImports;
	}

	/**
	 * @return the tagsComment
	 */
	public Map<String, String> getTagsComment() {
		return tagsComment;
	}

	/**
	 * @param tagsComment the tagsComment to set
	 */
	public void setTagsComment(Map<String, String> tagsComment) {
		this.tagsComment = tagsComment;
	}

	public void addTagComment(String tag, String comment){
		this.getTagsComment().put("@" + tag, comment);
	}
	
	public void addAuthorTag(String comment){
		this.getTagsComment().put("@author", comment);
	}
}
