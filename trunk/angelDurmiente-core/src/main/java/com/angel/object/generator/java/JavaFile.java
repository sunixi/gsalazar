/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaFile {

	private String packageName;
	private String className;
	private List<String> imports;
	private String classComment;
	private List<JavaMethod> methods;
	private Class<?> subClass;
	private List<Class<?>> interfaces;
	
	public JavaFile(String className, String packageName){
		super();
		this.setClassName(className);
		this.setInterfaces(new ArrayList<Class<?>>());
		this.setImports(new ArrayList<String>());
		this.setMethods(new ArrayList<JavaMethod>());
		this.setPackageName(packageName);
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the imports
	 */
	public List<String> getImports() {
		return imports;
	}

	/**
	 * @param imports the imports to set
	 */
	public void setImports(List<String> imports) {
		this.imports = imports;
	}

	/**
	 * @return the classComment
	 */
	public String getClassComment() {
		return classComment;
	}

	/**
	 * @param classComment the classComment to set
	 */
	public void setClassComment(String classComment) {
		this.classComment = classComment;
	}

	/**
	 * @return the methods
	 */
	public List<JavaMethod> getMethods() {
		return methods;
	}

	/**
	 * @param methods the methods to set
	 */
	public void setMethods(List<JavaMethod> methods) {
		this.methods = methods;
	}

	public void addJavaMethod(JavaMethod javaMethod) {
		this.getMethods().add(javaMethod);
	}
	
	public JavaMethod getTemplateInterfaceJavaMethod(){
		return new JavaMethod("methodName", new ArrayList<JavaParameter>(), false);
	}
	
	public JavaMethod getTemplateJavaMethod(){
		return new JavaMethod("methodName", new ArrayList<JavaParameter>(), true);
	}
	
	public void addJavaMethod(String methodName, List<JavaParameter> parameters, JavaParameter javaParameter, String contentMethod){
		this.getMethods().add(new JavaMethod(methodName, parameters, javaParameter, contentMethod));		
	}
	
	public void addJavaMethod(String methodName, JavaParameter javaParameter){
		this.addJavaMethod(methodName, new ArrayList<JavaParameter>(), javaParameter, "");		
	}
	
	public void addJavaMethod(String methodName, String parameterName, Class<?> javaParameterReturnType, String contentMethod){
		this.addJavaMethod(methodName, new ArrayList<JavaParameter>(), new JavaParameter(parameterName, javaParameterReturnType), contentMethod);
	}

	/**
	 * @return the subClass
	 */
	public Class<?> getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass the subClass to set
	 */
	public void setSubClass(Class<?> subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return the interfaces
	 */
	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	/**
	 * @param interfaces the interfaces to set
	 */
	public void setInterfaces(List<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public void addInterface(Class<?> interfaceClass){
		this.getInterfaces().add(interfaceClass);
	}
	
	public String getFileName(){
		return this.getClassName() + ".java";
	}

	public String getFileContent() {
		String fileContent = "package " + this.getPackageName() + "\n\n";
		fileContent += this.getPlainImports() + "\n\n";
		fileContent += this.getClassComment() + "\n";
		fileContent += "public class " + this.getClassName();
		fileContent += this.hasSubClass() ? " extends " + this.getSubClass().getSimpleName() : "" ;
		fileContent += " { \n";
		for(JavaMethod jm: this.getMethods()){
			fileContent += jm.getMethod() + "\n\n";
		}
		return fileContent;
	}
	
	public boolean hasSubClass(){
		return this.getSubClass() != null;
	}
	
	protected String getPlainImports(){
		String plainImport = "";
		for(String i: this.getImports()){
			plainImport += "import " + i + "\n";
		}
		return plainImport;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}
