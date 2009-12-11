/**
 * 
 */
package com.angel.code.generator.data.enums;

/**
 * @author William
 *
 */
public enum FlexVisibility {

	PROTECTED("protected"),
	PUBLIC("public"),
	INTERNAL("internal"),
	PRIVATE("private");
	
	private String visibility;

	private FlexVisibility(String visibility){
		this.visibility = visibility;
	}

	public String getVisibility() {
		return visibility;
	}
}
