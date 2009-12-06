/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.CodeLine;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaCodeLine extends CodeLine {

	public static final String JAVA_END_OF_LINE = ";";

	public JavaCodeLine(String content){
		this();
		/*
		if(!content.trim().endsWith(JAVA_END_OF_LINE)){
			content +=JAVA_END_OF_LINE;
		}*/
		this.setCode(content);
	}

	public JavaCodeLine(String content, String importType){
		this(content);
		List<String> importsType = new ArrayList<String>();
		importsType.add(importType);
		this.setImportsType(importsType);
	}

	public JavaCodeLine(String content, List<String> importsTypes){
		this(content);
		this.setImportsType(importsTypes);
	}
	
	public JavaCodeLine() {
		super();
		this.setImportsType(new ArrayList<String>());
	}

	public void removeEndTag() {
		if(super.getCode().endsWith(JAVA_END_OF_LINE)){
			this.setCode(this.getCode().substring(0, this.getCode().length() - 1));
		}
	}
}
