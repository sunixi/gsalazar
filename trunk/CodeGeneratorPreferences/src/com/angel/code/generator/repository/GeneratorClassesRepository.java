/**
 * 
 */
package com.angel.code.generator.repository;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.beans.CodeGeneratorInfo;
import com.angel.code.generator.codeGenerator.CodeGenerator;
import com.angel.code.generator.codeGenerator.impl.java.AnnotationDataGeneratorClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.AnnotationRowProcessorCommandClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ApplicationAnnotationGeneratorExecutableClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ApplicationBaseTestClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.DAOClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.DAOImplClassGenerator;

//import java.util.List;


/**
 * 
 * @author Guillermo Salazar.
 * @since 29/Noviembre/2009.
 * 
 */
public class GeneratorClassesRepository {

	private static GeneratorClassesRepository INSTANCE;
	
	private List<CodeGeneratorInfo> generatorClasses;

	private GeneratorClassesRepository(){
		super();
		this.setGeneratorClasses(new ArrayList<CodeGeneratorInfo>());
		
		this.addGeneratorClassInfo(
				AnnotationDataGeneratorClassGenerator.class,
				"Description de annotation data generator class generator.");
		this.addGeneratorClassInfo(
				AnnotationRowProcessorCommandClassGenerator.class,
				"Description de annotation row processor command class generator.");
		this.addGeneratorClassInfo(
				ApplicationAnnotationGeneratorExecutableClassGenerator.class,
				"Description de application annotation generator executable class generator.");
		this.addGeneratorClassInfo(
				ApplicationBaseTestClassGenerator.class,
				"Description application base test class generator.");
		this.addGeneratorClassInfo(
				DAOClassGenerator.class,
				"Description dao class generator.");
		this.addGeneratorClassInfo(
				DAOImplClassGenerator.class,
				"Description dao impl class generator.");
	}

	public static synchronized GeneratorClassesRepository getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GeneratorClassesRepository();
		}
		return INSTANCE;
	}

	/**
	 * @return the generatorClasses
	 */
	public List<CodeGeneratorInfo> getGeneratorClasses() {
		return generatorClasses;
	}

	/**
	 * @param generatorClasses the generatorClasses to set
	 */
	protected void setGeneratorClasses(List<CodeGeneratorInfo> generatorClasses) {
		this.generatorClasses = generatorClasses;
	}

	public <T extends CodeGenerator> void addGeneratorClassInfo(Class<T> generatorClass, String description){
		this.getGeneratorClasses().add(new CodeGeneratorInfo(generatorClass, description));
	}

	public CodeGeneratorInfo getCodeGeneratorInfo(String simpleName) {
		for(CodeGeneratorInfo cgi: this.getGeneratorClasses()){
			if(cgi.hasSimpleName(simpleName)){
				return cgi;
			}
		}
		return null;
	}
}
