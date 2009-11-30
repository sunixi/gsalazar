/**
 * 
 */
package com.angel.object.generator.java.enums;

/**
 * @author William
 *
 */
public enum Visibility {

	PROTECTED("protected"),
	PUBLIC("public"),
	PRIVATE("private");
	
	private String visibility;

	private Visibility(String visibility){
		this.visibility = visibility;
	}

	public String getVisibility() {
		return visibility;
	}
}
