/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.types.DataConstructor;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaConstructor extends DataConstructor {

	private Visibility visibility;

	public JavaConstructor(String name){
		super(name);
		this.setVisibility(Visibility.PUBLIC);
	}
	
	/**
	 * @return the visibility
	 */
	public Visibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public void setPrivateVisibility() {
		this.setVisibility(Visibility.PRIVATE);
	}
	
	public void setProtectedVisibility() {
		this.setVisibility(Visibility.PROTECTED);
	}
	
	public void setPublicVisibility() {
		this.setVisibility(Visibility.PUBLIC);
	}

	@Override
	protected String convertCodeConstructorSign() {
		String convertedCode = "\t";
		convertedCode += this.getVisibility().getVisibility() + " ";
		convertedCode += this.getSimpleName() + "(";
		convertedCode += this.hasParameters() ? this.getPlainParametersNames() + ")" : ")";		
		return convertedCode;
	}
}
