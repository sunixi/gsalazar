/**
 * 
 */
package com.angel.object.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.classGenerator.CodeGenerator;

/**
 * @author Guillermo D. Salazar
 *
 */
public class CodesGenerator {

	private final static Logger LOGGER = Logger.getLogger(CodesGenerator.class);
	private String baseProjectPackage;
	private List<Class<?>> domainClasses;
	private List<CodeGenerator> codesGenerators;
	private Map<String, String> globalImports;
	private Map<String, String> tagsComment;
	private List<Class<?>> excludesDomains;
	private String beanPackageName;

	/**
	 * 
	 */
	public CodesGenerator() {
		super();
		this.setBaseProjectPackage(StringHelper.EMPTY_STRING);
		this.setCodesGenerators(new ArrayList<CodeGenerator>());
		this.setDomainClasses(new ArrayList<Class<?>>());
		this.setExcludesDomains(new ArrayList<Class<?>>());
		this.setGlobalImports(new HashMap<String, String>());
		this.setTagsComment(new HashMap<String, String>());
	}

	public CodesGenerator(String baseProjectPackage) {
		this();
		this.setBaseProjectPackage(baseProjectPackage);
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
	
	public void excludeDomain(String domainClassName) {
		Class<?> domainClass = ReflectionHelper.getClassFrom(domainClassName);
		if(domainClass != null){
			this.getExcludesDomains().add(domainClass);
		}
	}

	public <T> void excludeDomain(Class<T> domainClass) {
		this.getExcludesDomains().add(domainClass);
	}
	
	protected boolean isExcludeDomainClass(Class<?> domainClass) {
		return this.getExcludesDomains().contains(domainClass);
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

	public void addCodeGenerator(CodeGenerator codeGenerator) {
		this.getCodesGenerators().add(codeGenerator);
	}

	public <T> void addDomain(Class<T> domainClass) {
		this.getDomainClasses().add(domainClass);
	}

	/**
	 * Generate code for all domain object classed added (by user or reflection). Code will create 
	 * depending on the generator class configuration used.
	 */
	public void generateCode() {
		this.initializeReflectionDomainObjects();
		this.initializeCodeGenerators();
		this.generateCodeForDomainClasses();
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
			String[] fileNames = directory.list();
			if(fileNames != null){
				for(String name: fileNames){
					if(name.endsWith(".java")){
						String simpleClassName = name.replace(".java", "");
						String canonicalBeanClass = canonicalBeanPackageName + "." + simpleClassName;
						Class<?> beanClass = ReflectionHelper.getClassFrom(canonicalBeanClass);
						LOGGER.info("Java class found [" + beanClass.getSimpleName() + "].");
					}
				}
			} else {
				throw new RuntimeException("Beans directory [" + beanDirectory + "] wasn't found. Please check base directory [" 
						+ sourcesDirectory + "] and package source [" + StringHelper.replaceAll(canonicalBeanPackageName, ".", "\\") + "].");
			}
		}
	}
	
	public boolean hasBeanPackageName(){
		return StringHelper.isNotEmpty(this.getBeanPackageName());
	}
	
	protected void generateCodeForDomainClasses(){
		List<Class<?>> domainClassesFiltered = this.filterDomainObjectClasses();
		for(CodeGenerator codeGenerator : this.getCodesGenerators()){
			LOGGER.info("Generating code for domain object classes [" + domainClassesFiltered.size() + "].");
			codeGenerator.generateCode(this, domainClassesFiltered);
		}
	}
	
	protected List<Class<?>> filterDomainObjectClasses(){
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
	 * Initialize code generators. It is called before generate domain object classes.
	 */
	protected void initializeCodeGenerators(){
		for(CodeGenerator codeGenerator : this.getCodesGenerators()){
			LOGGER.info("Initializing code generator type [" + codeGenerator.getClass().getCanonicalName() + "].");
			codeGenerator.initializeCodeGenerator(this, this.getDomainClasses());
		}
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

	/**
	 * @return the codesGenerators
	 */
	public List<CodeGenerator> getCodesGenerators() {
		return codesGenerators;
	}

	/**
	 * @param codesGenerators the codesGenerators to set
	 */
	public void setCodesGenerators(List<CodeGenerator> codesGenerators) {
		this.codesGenerators = codesGenerators;
	}
	
	public Set<String> getTagCommentsNames(){
		return this.getTagsComment().keySet();
	}

	public String getTagCommentValue(String tag) {
		return this.getTagsComment().get(tag);
	}
}
