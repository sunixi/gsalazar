/**
 * 
 */
package com.angel.object.generator.java.enums;

/**
 * @author William
 *
 */
public enum TypeModifier {

	NONE(""),
	STATIC("static"),
	FINAL_STATIC("final static"),
	FINAL("final");
	
	private String typeModifier;

	private TypeModifier(String typeModifier){
		this.typeModifier = typeModifier;
	}

	public String getTypeModifier() {
		return typeModifier;
	}
}
