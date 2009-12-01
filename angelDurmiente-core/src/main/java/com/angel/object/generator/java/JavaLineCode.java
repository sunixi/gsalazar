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
	private String lineSeparator;
	private List<String> importsType;

	public JavaLineCode(String content){
		super();
		this.setLineSeparator(JAVA_END_OF_LINE);
		if(!content.trim().endsWith(this.getLineSeparator())){
			content += this.getLineSeparator();
		}
		this.setContent(content);
		this.setImportsType(new ArrayList<String>());
	}
	
	public JavaLineCode(String content, String importType, String lineSeparator){
		this(content, importType);
		this.setLineSeparator(lineSeparator);
	}
	
	public JavaLineCode(String content, String importType){
		this(content);
		List<String> importsType = new ArrayList<String>();
		importsType.add(importType);
		this.setImportsType(importsType);
	}
	
	public JavaLineCode(String content, List<String> importsTypes, String lineSeparator){
		this(content, importsTypes);
		this.setLineSeparator(lineSeparator);
	}
	
	public JavaLineCode(String content, List<String> importsTypes){
		this(content);
		this.setImportsType(importsTypes);
	}
	
	public void addImport(String classImport){
		this.getImportsType().add("import " + classImport + JAVA_END_OF_LINE);
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

	/**
	 * @return the lineSeparator
	 */
	public String getLineSeparator() {
		return lineSeparator;
	}

	/**
	 * @param lineSeparator the lineSeparator to set
	 */
	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
}
