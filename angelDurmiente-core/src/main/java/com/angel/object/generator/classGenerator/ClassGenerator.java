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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.angel.common.helpers.FileHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.CodesGenerator;
import com.angel.object.generator.java.JavaConstructor;
import com.angel.object.generator.java.properties.JavaProperty;
import com.angel.object.generator.java.types.JavaInterface;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.MethodBuilder;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class ClassGenerator extends CodeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(CodesGenerator.class);
	private JavaType javaType;
	private Map<Class<? extends Annotation>, MethodBuilder> methodBuilderStrategies;
	private ClassGenerator interfaceClassGenerator;

	protected abstract JavaType buildJavaType();

	protected abstract void buildInterfacesClasses();
	
	protected abstract String buildClassName(Class<?> domainClass);
	
	protected abstract void generateContentClass(CodesGenerator generator, Class<?> domainClass);

	public ClassGenerator(){
		super();
		this.setMethodBuilderStrategies(new HashMap<Class<? extends Annotation>, MethodBuilder>());
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
	
	public void initializeCodeGenerator(CodesGenerator generator, List<Class<?>> domainClasses){
		if(this.hasInterfaceClassGenerator()){
			LOGGER.debug("Initializing interface code generator [" + this.getInterfaceClassGenerator().getClass().getCanonicalName() + "] at implementation class generator type [" + this.getClass().getCanonicalName() + "].");			
			this.getInterfaceClassGenerator().initializeCodeGenerator(generator, domainClasses);
		}
		this.initializeTagsComments(generator, domainClasses);
		for(Class<?> domainClass: domainClasses){
			String simpleClassGeneratorType = this.buildClassName(domainClass);
			generator.addRelativeImport(this.getBasePackage(), simpleClassGeneratorType);
		}
	}
	
	@Override
	public void generateCode(CodesGenerator generator, List<Class<?>> domainClasses) {
		if(this.hasInterfaceClassGenerator()){
			this.getInterfaceClassGenerator().generateCode(generator, domainClasses);
		}
		LOGGER.info("Genereting domain object codes [" + domainClasses.size() + 
				"] with generator [" + generator.getClass().getCanonicalName() + "].");
		super.generateCode(generator, domainClasses);
	}

	protected void finalizeCodeGenerator(CodesGenerator generator, Class<?> domainClass){
		this.setJavaType(this.buildJavaType());
	}
	
	/**
	 * Initialize all tags comments in codes generator global configuration.
	 * 
	 * @param generator which was used to configure domain classes.
	 * @param domainClasses list with all domain object classes added (by user or reflection).
	 */
	protected void initializeTagsComments(CodesGenerator generator, List<Class<?>> domainClasses){
		if(this.hasInterfaceClassGenerator()){
			this.getInterfaceClassGenerator().initializeCodeGenerator(generator, domainClasses);
		}
		LOGGER.info("Adding tags comments for class generator type [" + this.getClass().getCanonicalName() + "].");
		for(String tag: generator.getTagCommentsNames()){
			String valueTag = generator.getTagCommentValue(tag);
			LOGGER.info("Adding tag comment [" + tag + " = " + valueTag + " ] for class generator type [" + this.getClass().getCanonicalName() + "].");
			this.addTagComment(tag, valueTag);
		}
	}

	protected void generateCodeFor(CodesGenerator generator, Class<?> domainClass) {
		this.updateJavaType(generator, domainClass);
		this.generateContentClass(generator, domainClass);
		this.createJavaClassFile(generator);
	}
	
	protected void updateJavaType(CodesGenerator generator, Class<?> domainClass){
		String classGeneratorName = this.buildClassName(domainClass);
		String className = generator.getBaseProjectPackage() + "." + this.getBasePackage() + "." + classGeneratorName;
		this.getJavaType().setTypeName(className);
		this.getJavaType().setDomainObject(domainClass);
		this.processGlobalTypesImportsClassGenerator(generator, domainClass);
		this.processJavaTypeInterfaces();
		this.processSubClassForClassGenerator();
		this.updateCurrentJavaType(generator, domainClass);
	}
	
	protected void processGlobalTypesImportsClassGenerator(CodesGenerator generator, Class<?> domainClass){
		if(this.hasInterfaceClassGenerator()){
			String classGeneratorName = this.getInterfaceClassGenerator().buildClassName(domainClass);
			String className = generator.getBaseProjectPackage() + "." + this.getInterfaceClassGenerator().getBasePackage() + "." + classGeneratorName;
			this.getInterfaceClassGenerator().getJavaType().setTypeName(className);
			this.getInterfaceClassGenerator().getJavaType().setDomainObject(domainClass);
			String classCanonicalNameGenerator = this.getInterfaceClassGenerator().getCanonicalClassNameGenerator(generator);
			this.getJavaType().addImport(classCanonicalNameGenerator);
		}
	}
	
	public String getCanonicalClassNameGenerator(CodesGenerator generator){
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
	
	protected abstract void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass);
	
	public void addTagComment(String tag, String comment){
		this.getJavaType().addTagComment(tag, comment);
	}

	public void addTagAuthor(String author){
		this.getJavaType().addAuthorTagComment(author);
	}

	protected void createJavaClassFile(CodesGenerator generator) {
		String javaFileContent = this.getJavaType().convert();
		String packageName = generator.getBaseProjectPackage() + "." + this.getBasePackage() + "\\";
		LOGGER.info("Creating java class file [" + this.getJavaType().getTypeName() + "] at package [" + packageName + "].");
		String directory = System.getProperty("user.dir") + super.getBaseSourcesDirectory() + StringHelper.replaceAll(packageName, ".", "\\");
		File directories = new File(directory);
		boolean directoriesCreated = directories.mkdirs();
		LOGGER.info("Directories [" + directory + "] was created: [" + directoriesCreated + "].");
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
	
	protected JavaProperty createJavaPropertyWithGetterAndSetter(String propertyName, String propertyType){
		return this.getJavaType().createJavaPropertyWithGetterAndSetter(propertyName, propertyType);
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
