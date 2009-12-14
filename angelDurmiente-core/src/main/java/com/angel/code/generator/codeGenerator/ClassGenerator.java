/**
 * 
 */
package com.angel.code.generator.codeGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.common.helpers.FileHelper;
import com.angel.common.helpers.StringHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class ClassGenerator extends CodeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(CodesGenerator.class);
	private DataType dataType;
	private Map<Class<? extends Annotation>, MethodBuilder> methodBuilderStrategies;
	//TODO Eliminar.
	private ClassGenerator interfaceClassGenerator;

	protected abstract DataType buildDataType();

	protected abstract String buildClassName(Class<?> domainClass);
	
	protected abstract void generateContentClass(CodesGenerator generator, Class<?> domainClass);

	public ClassGenerator(){
		super();
		this.setMethodBuilderStrategies(new HashMap<Class<? extends Annotation>, MethodBuilder>());
		this.setDataType(this.buildDataType());
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
		this.setDataType(this.buildDataType());
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
		this.getDataType().setCanonicalName(className);
		this.getDataType().setDomainObject(domainClass);
		this.processGlobalTypesImportsClassGenerator(generator, domainClass);
		this.processJavaTypeInterfaces(generator);
		this.processSubClassForClassGenerator(generator);
		this.updateCurrentJavaType(generator, domainClass);
	}
	
	protected void processGlobalTypesImportsClassGenerator(CodesGenerator generator, Class<?> domainClass){
		if(this.hasInterfaceClassGenerator()){
			String classGeneratorName = this.getInterfaceClassGenerator().buildClassName(domainClass);
			String className = generator.getBaseProjectPackage() + "." + this.getInterfaceClassGenerator().getBasePackage() + "." + classGeneratorName;
			this.getInterfaceClassGenerator().getDataType().setCanonicalName(className);
			this.getInterfaceClassGenerator().getDataType().setDomainObject(domainClass);
			String classCanonicalNameGenerator = this.getInterfaceClassGenerator().getCanonicalClassNameGenerator(generator);
			this.getDataType().addGlobalImport(classCanonicalNameGenerator);
		}
	}
	
	public String getCanonicalClassNameGenerator(CodesGenerator generator){
		String classGeneratorName = this.buildClassName(this.getDataType().getDomainObject());
		String className = generator.getBaseProjectPackage() + "." + this.getBasePackage() + "." + classGeneratorName;
		return className;
	}
	
	protected void processJavaTypeInterfaces(CodesGenerator generator){
		//Do nothing.
	}

	protected void processSubClassForClassGenerator(CodesGenerator generator){
		DataType subDataType = this.buildDataType();
		subDataType = this.buildSubClassForClassGenerator(generator, subDataType);
		if(subDataType != null){
			this.getDataType().setSubDataType(subDataType);
		}
	}
	
	protected abstract void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass);
	
	public void addTagComment(String tag, String comment){
		this.getDataType().addTagComment(tag, comment);
	}

	public void addTagAuthor(String author){
		this.getDataType().addTagCommentAuthor(author);
	}

	protected void createJavaClassFile(CodesGenerator generator) {
		String javaFileContent = this.getDataType().convertCode();
		String packageName = generator.getBaseProjectPackage() + (this.hasBasePackage() ? "." + this.getBasePackage() + "\\": "");
		LOGGER.info("Creating java class file [" + this.getDataType().getSimpleName() + "] at package [" + packageName + "].");
		String directory = System.getProperty("user.dir") + super.getBaseSourcesDirectory() + StringHelper.replaceAll(packageName, ".", "\\");
		File directories = new File(directory);
		boolean directoriesCreated = directories.mkdirs();
		LOGGER.info("Directories [" + directory + "] was created: [" + directoriesCreated + "].");
		String fileName = this.getDataType().getDataTypeFileName();
		File javaClassFile = FileHelper.createFile(directory, fileName);
		try {
			Writer writer = new FileWriter(javaClassFile);
			writer.write(javaFileContent);
			writer.flush();
		} catch (IOException e) {}
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
	 * @throws @{@link CodeGeneratorException} when method builder getted is null.
	 */
	public MethodBuilder getMethodBuilderNullSafeFor(AnnotatedElement annotatedElement) {
		MethodBuilder methodBuilder = this.getMethodBuilderFor(annotatedElement);
		if(methodBuilder != null){
			return methodBuilder;
		}
		String exceptionMessage = "Annotated element [ Class: " + annotatedElement.getClass() + "]" +
			" wasn't processed because it isn't specificied with Accesor annotation.";
		LOGGER.error(exceptionMessage);
		throw new CodeGeneratorException(exceptionMessage);
	}

	/**
	 * Get a method builder for a specific field. It depends on field properties and its annotations.
	 *  
	 * @param field to verify its properties and annotations.
	 * @return a method builder for a field. Otherwise it returns null.
	 */
	public MethodBuilder getMethodBuilderFor(AnnotatedElement annotatedElement) {
		for(Class<? extends Annotation> a: methodBuilderStrategies.keySet()){
			boolean isPresent = annotatedElement.isAnnotationPresent(a);
			if(isPresent){
				Annotation annotation = annotatedElement.getAnnotation(a);
				return this.getMethodBuilderStrategies().get(annotation.annotationType());
			}
		}
		return null;
	}

	/**
	 * Test if field contains a method builder strategy through field's annotations stratategies.
	 * 
	 * @param field to test its annotations.
	 * @return true if an annotation strategy was added. Otherwise it returns false.
	 */
	protected boolean containsMethodBuilderFor(AnnotatedElement annotatedElement){
		for(Class<? extends Annotation> a: methodBuilderStrategies.keySet()){
			boolean isPresent = annotatedElement.isAnnotationPresent(a);
			if(isPresent){
				return true;
			}
		}
		return false;
	}

	/**
	 * Build method for a domain class and an annotated element in domain class. First it tests, if annotated elements
	 * contains some annotation to build method, then if it contains some one, it build data method.
	 * Otherwise it do nothing.
	 * 
	 * @param annotatedElement to inspects its annotations.
	 * @param generator to can build data method.
	 * @param domainClass to can build data method.
	 */
	public void buildMethodFor(AnnotatedElement annotatedElement, CodesGenerator generator, Class<?> domainClass){
		if(this.containsMethodBuilderFor(annotatedElement)){
			MethodBuilder methodBuilder = this.getMethodBuilderFor(annotatedElement);
			methodBuilder.buildDataMethod(generator, this.getDataType(), domainClass, annotatedElement);
		}
	}
	
	/**
	 * Get sub class for class to generate.
	 * 
	 * @return a sub class class.
	 */
	public DataType buildSubClassForClassGenerator(CodesGenerator generator, DataType dataType){
		return null;
	}

	/**
	 * @return the javaType
	 */
	protected DataType getDataType() {
		return this.dataType;
	}

	/**
	 * @param javaType the javaType to set
	 */
	protected void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getDomainObjectCanonicalName(){
		return this.getDataType().getDomainObjectCanonicalName();
	}
	
	public String getDomainObjectSimpleName(){
		return this.getDataType().getDomainObjectSimpleName();
	}
	
	protected void addMethodBuilderStrategies(Class<? extends Annotation> annotation, MethodBuilder methodBuilder){
		this.getMethodBuilderStrategies().put(annotation, methodBuilder);
	}
	
	protected boolean isAnImplementationClassGenerator(){
		return this.getDataType() != null ? this.getDataType().isAnImplementationType() : false;
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
