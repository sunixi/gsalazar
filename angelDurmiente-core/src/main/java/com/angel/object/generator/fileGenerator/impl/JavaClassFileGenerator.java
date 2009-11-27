/**
 * 
 */
package com.angel.object.generator.fileGenerator.impl;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaClassFileGenerator {

	private Class<?> subClass;

	public JavaClassFileGenerator(){
		super();
	}
	
	public JavaClassFileGenerator(Class<?> subClass){
		this();
		this.setSubClass(subClass);
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
}
