/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaLineCode implements Importable, CodeConvertible {

	public static final String JAVA_END_OF_LINE = ";";

	private String content;
	private List<String> importsType;

	public JavaLineCode(String content){
		super();
		if(!content.trim().endsWith(JAVA_END_OF_LINE)){
			content +=JAVA_END_OF_LINE;
		}
		this.setContent(content);
		this.setImportsType(new ArrayList<String>());
	}

	public JavaLineCode(String content, String importType){
		this(content);
		List<String> importsType = new ArrayList<String>();
		importsType.add(importType);
		this.setImportsType(importsType);
	}

	public JavaLineCode(String content, List<String> importsTypes){
		this(content);
		this.setImportsType(importsTypes);
	}
	
	public void addImport(String classImport){
		this.getImportsType().add("import " + classImport + JAVA_END_OF_LINE);
	}

	public void addImportsTypes(List<String> importsTypes){
		this.getImportsType().addAll(importsTypes);
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the importsType
	 */
	public List<String> getImportsType() {
		return importsType;
	}

	/**
	 * @param importsType the importsType to set
	 */
	public void setImportsType(List<String> importsType) {
		this.importsType = importsType;
	}

	public String convert() {
		return "\t\t" + this.getContent() + "\n";
	}

	public void addImportsType(JavaLineCode otherJavaLineCode) {
		this.getImportsType().addAll(otherJavaLineCode.getImportsType());
	}
}
