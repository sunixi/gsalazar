/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import com.angel.code.generator.helpers.PackageHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ExecutableReturnNewInstanceCodeLine extends ExecutableReturnCodeLine {

	/**
	 * Create an executable return new code line instance.
	 * <pre>
	 * 	... new SimpleReturnName()
	 * </pre>
	 * @param returnCanonicalName to be created.
	 */
	public ExecutableReturnNewInstanceCodeLine(String returnCanonicalName){
		super(PackageHelper.getClassSimpleName(returnCanonicalName), returnCanonicalName);
	}

	public ExecutableReturnNewInstanceCodeLine(String returnCanonicalName, String returnCollectionCanonicalName){
		super(PackageHelper.getClassSimpleName(returnCanonicalName), returnCanonicalName, returnCollectionCanonicalName);
	}

	@Override
	protected String converCodeCallingMethod() {
		String convertedCode = "new ";
		convertedCode += this.hasCollectionCanonicalName() ? super.getSimpleReturnCollectionCanonicalName() : "";
		convertedCode += this.hasCollectionCanonicalName() ? "<":"";
		convertedCode += this.getSimpleReturnCanonicalName();
		convertedCode += this.hasCollectionCanonicalName() ? ">":"";
		return convertedCode;
	}
}
