/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import com.angel.common.helpers.StringHelper;


/**
 * 
 * 
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ElseControlCodeLine extends ControlStructureCodeLine {


	/**
	 * Create an else control structure.
	 * 
	 */
	public ElseControlCodeLine(){
		super(StringHelper.EMPTY_STRING, "else");
	}

	@Override
	protected String convertCodeControlStructure() {
		return this.getContent();
	}
}
