/**
 * 
 */
package com.angel.object.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.classGenerator.ClassGenerator;

/**
 * @author Guillermo D. Salazar
 *
 */
public class ClassesGenerator {

	private final static Logger LOGGER = Logger.getLogger(ClassesGenerator.class);
	private String baseProjectPackage;
	private List<Class<?>> domainClasses;
	private List<ClassGenerator> classesGenerators;
	private Map<String, String> globalImports;
	private Map<String, String> tagsComment;
	private String beanPackageName;

	/**
	 * 
	 */
	public ClassesGenerator() {
		super();
		this.setBaseProjectPackage(StringHelper.EMPTY_STRING);
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
		this.initializeReflectionDomainObjects();
		this.initializeGlobalImports();
		this.generateClassesGeneratorClasses();
	}
	
	protected void initializeReflectionDomainObjects(){
		if(this.hasBeanPackageName()){
			String canonicalBeanPackageName = this.getBaseProjectPackage() + "." + this.getBeanPackageName();
			String sourcesDirectory = System.getProperty("user.dir") + "\\src\\main\\java\\";
			String beanDirectory =	sourcesDirectory + 
									StringHelper.replaceAll(canonicalBeanPackageName, ".", "\\");
			LOGGER.debug("The project directory built is [" + sourcesDirectory + "].");
			LOGGER.debug("Finding java classes in package [" + canonicalBeanPackageName + "]."); 
			File directory = new File(beanDirectory);
			for(String name: directory.list()){
				if(name.endsWith(".java")){
					String simpleClassName = name.replace(".java", "");
					String canonicalBeanClass = canonicalBeanPackageName + "." + simpleClassName;
					Class<?> beanClass = ReflectionHelper.getClassFrom(canonicalBeanClass);
					LOGGER.info("Java class found [" + beanClass.getSimpleName() + "].");
				}
			}
		}
	}
	
	public boolean hasBeanPackageName(){
		return StringHelper.isNotEmpty(this.getBeanPackageName());
	}
	
	protected void generateClassesGeneratorClasses(){
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
	
	protected void initializeGlobalImports(){
		for(ClassGenerator classGenerator : this.getClassesGenerators()){
			if(classGenerator.hasInterfaceClassGenerator()){
				LOGGER.info("Initializing class generator type [" + classGenerator.getInterfaceClassGenerator().getClass().getCanonicalName() + "] import.");
				classGenerator.getInterfaceClassGenerator().initializeClassGeneratorImport(this, this.getDomainClasses());	
			}
			LOGGER.info("Initializing class generator type [" + classGenerator.getClass().getCanonicalName() + "] import.");
			classGenerator.initializeClassGeneratorImport(this, this.getDomainClasses());
		}
	}
	
	protected void addTagComments(ClassGenerator classGenerator){
		LOGGER.info("Adding tags comments for class generator type [" + classGenerator.getClass().getCanonicalName() + "].");
		for(String tag: this.getTagsComment().keySet()){
			String valueTag = this.getTagsComment().get(tag);
			LOGGER.info("Adding tag comment [" + tag + " = " + valueTag + " ] for class generator type [" + classGenerator.getClass().getCanonicalName() + "].");
			classGenerator.addTagComment(tag, valueTag);
		}
	}
	
	protected void processDomainClass(Class<?> domainClass, ClassGenerator classGenerator){
		LOGGER.info("Processing domain class [" + domainClass.getCanonicalName() + "] with class generator type [" + classGenerator.getClass().getCanonicalName() + "].");
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

	/**
	 * @return the beanPackageName
	 */
	public String getBeanPackageName() {
		return beanPackageName;
	}

	/**
	 * @param beanPackageName the beanPackageName to set
	 */
	public void setBeanPackageName(String beanPackageName) {
		this.beanPackageName = beanPackageName;
	}
}
