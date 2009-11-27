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

import javax.persistence.Column;

import org.apache.log4j.Logger;

import com.angel.common.helpers.FileHelper;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classNameStrategies.ClassNameStrategy;
import com.angel.object.generator.fileGenerator.impl.JavaClassFileGenerator;
import com.angel.object.generator.java.JavaFile;
import com.angel.object.generator.java.JavaMethod;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorAnnotationMethodBuilder;
import com.angel.object.generator.methodBuilder.impl.ColumnAnnotationMethodBuilder;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class ClassGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(ClassesGenerator.class);
	private String basePackage = "";
	private ClassNameStrategy classNameStrategy;
	private boolean includeSuperClass = false;
	private List<Class<?>> excludesDomains;
	private JavaClassFileGenerator javaClassFileGenerator;
	private Map<Class<? extends Annotation>, MethodBuilder> methodBuilderStrategies;
	
	public ClassGenerator(){
		super();
		this.setExcludesDomains(new ArrayList<Class<?>>());	
		this.setMethodBuilderStrategies(new HashMap<Class<? extends Annotation>, MethodBuilder>());
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorAnnotationMethodBuilder());
		this.getMethodBuilderStrategies().put(Column.class, new ColumnAnnotationMethodBuilder());
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

	public void generateClass(ClassesGenerator generator, Class<?> domainClass) {
		String classGeneratorName = this.buildClassName(domainClass);
		String packageName = generator.getBaseProjectPackage() + "." + this.getBasePackage();
		JavaFile javaFile = new JavaFile(classGeneratorName, packageName);
		javaFile.setSubClass(this.getSubClassForClassGenerator());
		this.generateContentClass(generator, domainClass, javaFile);
		this.createJavaClassFile(generator, javaFile);
	}
	
	protected void createJavaClassFile(ClassesGenerator generator, JavaFile javaFile) {
		String javaFileContent = javaFile.getFileContent();
		String packageName = generator.getBaseProjectPackage() + "." + this.getBasePackage();
		String directory = StringHelper.replaceAll(packageName, ".", "/");
		File javaClassFile = FileHelper.createFile(javaFile.getFileName(), directory);
		try {
			Writer writer = new FileWriter(javaClassFile);
			writer.append(javaFileContent);
		} catch (IOException e) {}
	}

	protected List<Class<?>> getInterfacesClasses(){
		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		this.getInterfacesClasses(interfaces);
		return interfaces;
	}
	
	protected abstract void getInterfacesClasses(List<Class<?>> interfaces);
	
	protected abstract String buildClassName(Class<?> domainClass);
	
	protected abstract void generateContentClass(ClassesGenerator generator, Class<?> domainClass, JavaFile javaFile);

	/**
	 * @return the javaClassFileGenerator
	 */
	public JavaClassFileGenerator getJavaClassFileGenerator() {
		return javaClassFileGenerator;
	}

	/**
	 * @param javaClassFileGenerator the javaClassFileGenerator to set
	 */
	public void setJavaClassFileGenerator(
			JavaClassFileGenerator javaClassFileGenerator) {
		this.javaClassFileGenerator = javaClassFileGenerator;
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

	public MethodBuilder getMethodBuilderFor(Field field) {
		for(Class<? extends Annotation> a: methodBuilderStrategies.keySet()){
			boolean isPresent = field.isAnnotationPresent(a);
			if(isPresent){
				Annotation annotation = field.getAnnotation(a);
				return this.getMethodBuilderStrategies().get(annotation.annotationType());
			}
			LOGGER.warn("Property field [ Name: " + field.getName() + " - Type: " + field.getType() + "]" +
					" wasn't processed because it wasn't specificied with annotations.");
		}
		return null;
	}
	
	protected void buildJavaMethodContent(JavaMethod javaMethod){
		
	}
	
	public Class<?> getSubClassForClassGenerator(){
		return Object.class;
	}
}
