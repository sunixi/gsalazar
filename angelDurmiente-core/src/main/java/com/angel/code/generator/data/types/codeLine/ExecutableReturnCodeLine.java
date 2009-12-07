/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.List;

import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ExecutableReturnCodeLine extends ExecutableCodeLine {

	private String returnCanonicalName;
	private String returnCollectionCanonicalName;

	/**
	 * Create an executable code line for "this" variable name with a object canonical name.
	 * <pre>
	 * 	... = this.methodName()
	 * </pre>
	 * @param methodName to be executed.
	 */
	public ExecutableReturnCodeLine(String methodName, String returnCanonicalName){
		super(methodName);
		this.setReturnCanonicalName(returnCanonicalName);
	}

	/**
	 * Create an executable code line for "this" variable name with a collection typed canonical name.
	 * <pre>
	 * 	... = CollectionType&lt;Type&gt; this.methodName();
	 * </pre>
	 * @param methodName to be executed.
	 * @param returnCanonicalName collection generic type to be returned. 
	 * @param returnCollectionCanonicalName collection type to be returned.
	 */
	public ExecutableReturnCodeLine(String methodName, String returnCanonicalName, String returnCollectionCanonicalName){
		this(methodName, returnCanonicalName);
		this.setReturnCollectionCanonicalName(returnCollectionCanonicalName);
	}
	
	@Override
	public String convertCode() {
		String convertedCode = "";
		convertedCode += this.converCodeCallingMethod();
		if(this.hasParameters()){			
			convertedCode += "(" + StringHelper.convertToPlainString(this.getParametersNames().toArray(), ", ") + ")";
		} else {
			convertedCode += "()"; 
		}
		return convertedCode;
	}

	protected String converCodeCallingMethod() {
		return this.getVariableName() + "." + this.getMethodName();
	}

	/**
	 * @return the returnCanonicalName
	 */
	public String getReturnCanonicalName() {
		return returnCanonicalName;
	}

	/**
	 * @param returnCanonicalName the returnCanonicalName to set
	 */
	public void setReturnCanonicalName(String returnCanonicalName) {
		this.returnCanonicalName = returnCanonicalName;
	}

	/**
	 * @return the returnCollectionCanonicalName
	 */
	public String getReturnCollectionCanonicalName() {
		return returnCollectionCanonicalName;
	}

	/**
	 * @param returnCollectionCanonicalName the returnCollectionCanonicalName to set
	 */
	public void setReturnCollectionCanonicalName(
			String returnCollectionCanonicalName) {
		this.returnCollectionCanonicalName = returnCollectionCanonicalName;
	}

	public boolean hasCollectionCanonicalName(){
		return StringHelper.isNotEmpty(this.getReturnCollectionCanonicalName());
	}

	public boolean needsCast(String variableCanonicalName, String returnCollectionCanonicalName) {
		return !this.isSameReturnCanonicalName(variableCanonicalName) || !this.isSameReturnCollectionCanonicalName(returnCollectionCanonicalName);
	}

	protected boolean isSameReturnCanonicalName(String canonicalName){
		return StringHelper.isNotEmpty(canonicalName) && this.getReturnCanonicalName().equalsIgnoreCase(canonicalName);
	}

	protected boolean isSameReturnCollectionCanonicalName(String collectionCanonicalName){
		return StringHelper.isNotEmpty(collectionCanonicalName) && this.getReturnCollectionCanonicalName().equalsIgnoreCase(collectionCanonicalName);
	}

	protected String getSimpleReturnCollectionCanonicalName() {
		return StringHelper.isNotEmpty(this.getReturnCollectionCanonicalName()) ? PackageHelper.getClassSimpleName(this.getReturnCollectionCanonicalName()) : "";
	}

	protected String getSimpleReturnCanonicalName() {
		return PackageHelper.getClassSimpleName(this.getReturnCanonicalName());
	}

	@SuppressWarnings("unchecked")
	public <T extends ExecutableReturnCodeLine> T addParameterName(String parameterName) {
		this.getParametersNames().add(parameterName);
		return (T) this;
	}

	@Override
	protected void completeImportsType(List<String> importsType) {
		this.addImportType(importsType, this.getReturnCanonicalName());
		this.addImportType(importsType, this.getReturnCollectionCanonicalName());
	}
}
