/**
 * 
 */
package com.angel.code.generator.beans;

//import java.util.List;


/**
 * 
 * @author Guillermo Salazar.
 * @since 29/Noviembre/2009.
 * 
 */
public class CodeGeneratorInfo {

	private Class<?> codeGeneratorClass;
	private String description;
	private String basePackageGenerator;
	
	public CodeGeneratorInfo(Class<?> codeGeneratorClass, String description, String basePackageGenerator){
		super();
		this.setCodeGeneratorClass(codeGeneratorClass);
		this.setDescription(description);
		this.setBasePackageGenerator(basePackageGenerator);
	}
	
	/**
	 * @return the codeGeneratorClass
	 */
	public Class<?> getCodeGeneratorClass() {
		return codeGeneratorClass;
	}



	/**
	 * @param codeGeneratorClass the codeGeneratorClass to set
	 */
	public void setCodeGeneratorClass(Class<?> codeGeneratorClass) {
		this.codeGeneratorClass = codeGeneratorClass;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	public String getSimpleName(){
		return this.getCodeGeneratorClass().getSimpleName();
	}

	public boolean hasSimpleName(String simpleName) {
		return this.getSimpleName().equalsIgnoreCase(simpleName);
	}

	/**
	 * @return the basePackageGenerator
	 */
	public String getBasePackageGenerator() {
		return basePackageGenerator;
	}

	/**
	 * @param basePackageGenerator the basePackageGenerator to set
	 */
	public void setBasePackageGenerator(String basePackageGenerator) {
		this.basePackageGenerator = basePackageGenerator;
	}
}
