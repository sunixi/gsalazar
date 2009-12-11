/**
 * 
 */
package com.angel.code.generator.data.enums;

/**
 * @author William
 *
 */
public enum FlexTypeModifier {

	NONE(""),
	STATIC("static"),
	STATIC_VAR("static var"),
	STATIC_CONST("static const"),
	VAR("var"),
	CONSTANT("const"),;
	
	private String typeModifier;

	private FlexTypeModifier(String typeModifier){
		this.typeModifier = typeModifier;
	}

	public String getTypeModifier() {
		return typeModifier;
	}
}
