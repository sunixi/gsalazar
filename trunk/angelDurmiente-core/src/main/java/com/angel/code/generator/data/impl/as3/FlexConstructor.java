/**
 * 
 */
package com.angel.code.generator.data.impl.as3;

import com.angel.code.generator.data.enums.FlexVisibility;
import com.angel.code.generator.data.types.DataConstructor;


/**
 * 
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexConstructor extends DataConstructor {

	private FlexVisibility visibility;

	public FlexConstructor(String name){
		super(name);
		this.setVisibility(FlexVisibility.PUBLIC);
	}

	/**
	 * @return the visibility
	 */
	public FlexVisibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(FlexVisibility visibility) {
		this.visibility = visibility;
	}

	@Override
	protected String convertCodeConstructorSign() {
		String convertedCode = "\t";
		convertedCode += this.getVisibility().getVisibility();
		convertedCode += " function ";
		convertedCode += this.getSimpleName() + "(";
		convertedCode += this.hasParameters() ? this.getPlainParametersNames() + ")" : ")";		
		return convertedCode;
	}
}
