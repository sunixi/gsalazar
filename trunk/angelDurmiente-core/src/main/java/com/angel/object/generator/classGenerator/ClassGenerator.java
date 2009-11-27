/**
 * 
 */
package com.angel.object.generator.classGenerator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.Generator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classNameStrategies.ClassNameStrategy;
import com.angel.object.generator.fileGenerator.impl.JavaClassFileGenerator;
import com.angel.object.generator.java.JavaFile;
import com.angel.object.generator.java.JavaMethod;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;

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
	private JavaClassFileGenerator javaClassFileGenerator;
	private Map<Class<? extends Annotation>, MethodBuilder> methodBuilderStrategies;
	
	public ClassGenerator(){
		super();
		this.setExcludesDomains(new ArrayList<Class<?>>());	
		this.setMethodBuilderStrategies(new HashMap<Class<? extends Annotation>, MethodBuilder>());
		this.getMethodBuilderStrategies().put(Accesor.class, null);
		this.getMethodBuilderStrategies().put(Column.class, null);
		this.getMethodBuilderStrategies().put(OneToOne.class, null);
		this.getMethodBuilderStrategies().put(ManyToOne.class, null);
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
		String classGeneratorName = this.getClassNameStrategy().buildClassName(domainClass);
		JavaFile javaFile = new JavaFile(classGeneratorName);
		this.generateContentClass(generator, domainClass, javaFile);
		
	}
	
	protected abstract void generateContentClass(Generator generator, Class<?> domainClass, JavaFile javaFile);

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
	
	protected void addJavaMethod(JavaFile javaFile, Class<?> domainClass, String contentMethod){
		String domainClassDAO = domainClass.getSimpleName() + "DAO";
		JavaParameter javaParameter = new JavaParameter("", ReflectionHelper.getClassFrom(domainClassDAO));
		String methodName = ReflectionHelper.getGetMethodName(domainClass.getSimpleName() + "DAO");
		JavaMethod javaMethod = new JavaMethod(methodName, new ArrayList<JavaParameter>(), javaParameter);
		javaMethod.setContentMethod(contentMethod);
		javaFile.addJavaMethod(javaMethod);
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

	public MethodBuilder getMethodBuilderFor(Annotation annotation) {
		return this.getMethodBuilderStrategies().get(annotation);
	}	
}
