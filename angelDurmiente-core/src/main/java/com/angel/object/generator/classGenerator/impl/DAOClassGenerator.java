/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.util.List;

import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaFile;


/**
 * @author C088347
 *
 */
public class DAOClassGenerator extends ClassGenerator {

	/**
	 * 
	 */
	public DAOClassGenerator(String basePackage) {
		super(basePackage);
	}

	public DAOClassGenerator() {
		super();
	}

	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass, JavaFile javaFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void getInterfacesClasses(List<Class<?>> interfaces) {
		// TODO Auto-generated method stub
		
	}
	
	protected String buildClassName(Class<?> domainClass){
		return domainClass.getSimpleName() + "DAO";
	}
}
