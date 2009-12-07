/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import java.util.List;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaCodeLine {//extends CodeLine {

	public static final String JAVA_END_OF_LINE = ";";

	public JavaCodeLine(String content){
		this();
	}

	public JavaCodeLine(String content, String importType){
		this(content);
		/*
		List<String> importsType = new ArrayList<String>();
		importsType.add(importType);
		this.setImportsType(importsType);*/
	}

	public JavaCodeLine(String content, List<String> importsTypes){
		this(content);
		//this.setImportsType(importsTypes);
	}
	
	public JavaCodeLine() {
		super();
		//this.setImportsType(new ArrayList<String>());
	}
}
