/**
 * 
 */
package com.angel.object.generator.classGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.angel.common.helpers.FileHelper;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.java.JavaConstructor;
import com.angel.object.generator.java.JavaProperty;
import com.angel.object.generator.java.types.JavaInterface;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.MethodBuilder;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class ClassGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(ClassesGenerator.class);
	private static final String DEFAULT_BASE_SOURCS_DIRECTORY = "\\src\\main\\java\\";
	private String basePackage = "";
	private List<Class<?>> excludesDomains;
	private JavaType javaType;
	private String baseJavaSourcesDirectory;
	private Map<Class<? extends Annotation>, MethodBuilder> methodBuilderStrategies;
	private ClassGenerator interfaceClassGenerator;

	protected abstract JavaType buildJavaType();
	
	public ClassGenerator(){
		super();
		this.setExcludesDomains(new ArrayList<Class<?>>());
		this.setMethodBuilderStrategies(new HashMap<Class<? extends Annotation>, MethodBuilder>());
		this.setBaseJavaSourcesDirectory(DEFAULT_BASE_SOURCS_DIRECTORY);
		this.setJavaType(this.buildJavaType());
	}
	
	
	public ClassGenerator(String basePackage){
		this();
		this.setBasePackage(basePackage);
	}

	public ClassGenerator(String basePackage, ClassGenerator interfaceClassGenerator){
		this(basePackage);
		this.setInterfaceClassGenerator(interfaceClassGenerator);
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

	public boolean isExcludeDomainClass(Class<?> domainClass) {
		return this.getExcludesDomains().contains(domainClass);
	}

	public void generateClass(ClassesGenerator generator, Class<?> domainClass) {
		this.updateJavaType(generator, domainClass);
		this.generateContentClass(generator, domainClass);
		this.createJavaClassFile(generator);
	}
	
	protected void updateJavaType(ClassesGenerator generator, Class<?> domainClass){
		String classGeneratorName = this.buildClassName(domainClass);
		String className = generator.getBaseProjectPackage() + "." + this.getBasePackage() + "." + classGeneratorName;
		this.getJavaType().setTypeName(className);
		this.getJavaType().setDomainObject(domainClass);
		this.processGlobalTypesImportsClassGenerator(generator);
		this.processJavaTypeInterfaces();
		this.processSubClassForClassGenerator();
		this.updateCurrentJavaType(generator, domainClass);
	}
	
	protected void processGlobalTypesImportsClassGenerator(ClassesGenerator generator){
		if(this.hasInterfaceClassGenerator()){
			String classCanonicalNameGenerator = this.getInterfaceClassGenerator().getCanonicalClassNameGenerator(generator);
			this.getJavaType().addImport(classCanonicalNameGenerator);
		}
	}
	
	public String getCanonicalClassNameGenerator(ClassesGenerator generator){
		String classGeneratorName = this.buildClassName(this.getJavaType().getDomainObject());
		String className = generator.getBaseProjectPackage() + "." + this.getBasePackage() + "." + classGeneratorName;
		return className;
	}
	
	protected void processJavaTypeInterfaces(){
		this.getInterfacesClasses();
	}

	protected void processSubClassForClassGenerator(){
		JavaType subJavaType = this.buildJavaType();
		subJavaType = this.buildSubClassForClassGenerator(subJavaType);
		if(subJavaType != null){
			this.getJavaType().setSubJavaType(subJavaType);
		}
	}
	
	protected abstract void updateCurrentJavaType(ClassesGenerator generator, Class<?> domainClass);
	
	public void addTagComment(String tag, String comment){
		this.getJavaType().addTagComment(tag, comment);
	}

	public void addTagAuthor(String author){
		this.getJavaType().addAuthorTagComment(author);
	}

	protected void createJavaClassFile(ClassesGenerator generator) {
		String javaFileContent = this.getJavaType().convert();
		String packageName = generator.getBaseProjectPackage() + "." + this.getBasePackage() + "\\";
		String directory = System.getProperty("user.dir") + this.getBaseJavaSourcesDirectory() + StringHelper.replaceAll(packageName, ".", "\\");
		String fileName = this.getJavaType().getName();
		File javaClassFile = FileHelper.createFile(directory, fileName);
		try {
			Writer writer = new FileWriter(javaClassFile);
			writer.write(javaFileContent);
			writer.flush();
		} catch (IOException e) {}
	}

	protected void getInterfacesClasses(){
		this.buildInterfacesClasses();
	}
	
	protected abstract void buildInterfacesClasses();
	
	protected abstract String buildClassName(Class<?> domainClass);
	
	protected abstract void generateContentClass(ClassesGenerator generator, Class<?> domainClass);


	/**
	 * @return the methodBuilderStrategies
	 */
	protected Map<Class<? extends Annotation>, MethodBuilder> getMethodBuilderStrategies() {
		return methodBuilderStrategies;
	}

	/**
	 * @param methodBuilderStrategies the methodBuilderStrategies to set
	 */
	protected void setMethodBuilderStrategies(
			Map<Class<? extends Annotation>, MethodBuilder> methodBuilderStrategies) {
		this.methodBuilderStrategies = methodBuilderStrategies;
	}

	/**
	 * Get a method builder for a specific field. It depends on field properties and its annotations.
	 *  
	 * @param field to verify its properties and annotations.
	 * @return a method builder for a field. Otherwise it returns null.
	 */
	public MethodBuilder getMethodBuilderFor(Field field) {
		for(Class<? extends Annotation> a: methodBuilderStrategies.keySet()){
			boolean isPresent = field.isAnnotationPresent(a);
			if(isPresent){
				Annotation annotation = field.getAnnotation(a);
				return this.getMethodBuilderStrategies().get(annotation.annotationType());
			}
		}
		LOGGER.warn("Property field [ Name: " + field.getName() + " - Type: " + field.getType() + "]" +
		" wasn't processed because it wasn't specificied with annotations.");
		return null;
	}
	
	/**
	 * Get sub class for class to generate.
	 * 
	 * @return a sub class class.
	 */
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		return null;
	}

	/**
	 * @return the javaType
	 */
	protected JavaType getJavaType() {
		return javaType;
	}

	/**
	 * @param javaType the javaType to set
	 */
	protected void setJavaType(JavaType javaType) {
		this.javaType = javaType;
	}

	/**
	 * @return the baseJavaSourcesDirectory
	 */
	public String getBaseJavaSourcesDirectory() {
		return baseJavaSourcesDirectory;
	}

	/**
	 * @param baseJavaSourcesDirectory the baseJavaSourcesDirectory to set
	 */
	public void setBaseJavaSourcesDirectory(String baseJavaSourcesDirectory) {
		this.baseJavaSourcesDirectory = baseJavaSourcesDirectory;
	}

	public String getDomainObjectCanonicalName(){
		return this.getJavaType().getDomainObjectCanonicalName();
	}
	
	public String getDomainObjectSimpleName(){
		return this.getJavaType().getDomainObjectSimpleName();
	}
	
	protected JavaInterface createJavaInterface(){
		return this.getJavaType().createJavaInterface();
	}

	protected JavaConstructor createJavaConstructor() {
		return this.getJavaType().createJavaConstructor();
	}
	
	protected JavaProperty createJavaProperty(String propertyName, String propertyType){
		return this.getJavaType().createJavaProperty(propertyName, propertyType);
	}

	protected JavaProperty createJavaProperty(){
		return this.getJavaType().createJavaProperty();
	}
	
	protected void addMethodBuilderStrategies(Class<? extends Annotation> annotation, MethodBuilder methodBuilder){
		this.getMethodBuilderStrategies().put(annotation, methodBuilder);
	}
	
	protected boolean isAnImplementationClassGenerator(){
		return this.getJavaType() != null ? this.getJavaType().isAnImplementationType() : false;
	}

	/**
	 * @return the interfaceClassGenerator
	 */
	public ClassGenerator getInterfaceClassGenerator() {
		return interfaceClassGenerator;
	}

	/**
	 * @param interfaceClassGenerator the interfaceClassGenerator to set
	 */
	public void setInterfaceClassGenerator(ClassGenerator interfaceClassGenerator) {
		this.interfaceClassGenerator = interfaceClassGenerator;
	}
	
	public boolean hasInterfaceClassGenerator(){
		return this.getInterfaceClassGenerator() != null;
	}
}
