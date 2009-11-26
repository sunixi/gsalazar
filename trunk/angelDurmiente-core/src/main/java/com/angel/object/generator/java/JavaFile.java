/**
 * 
 */
package com.angel.object.generator.java;

import java.util.List;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaFile {

	private String className;
	private List<String> imports;
	private String classComment;
	private List<JavaMethod> methods;
	
	public JavaFile(String className){
		super();
		this.setClassName(className);
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
}
