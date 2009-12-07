/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.Importable;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class CodeLine implements CodeConvertible, Importable{

	private List<String> globalImports;

	protected abstract void completeImportsType(List<String> importsType);

	public CodeLine(){
		super();
		this.setGlobalImports(new ArrayList<String>());
	}

	/**
	 * @return the importsType
	 */
	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		this.addImportsType(importsType, this.getGlobalImports());
		this.completeImportsType(importsType);
		return importsType;
	}

	protected void addImportType(List<String> importsType, String importType){
		if(StringHelper.isNotEmpty(importType)){
			importsType.add(importType);
		}
	}

	protected void addImportsType(List<String> importsType, List<String> importsTypeToAdd){
		for(String importType: importsTypeToAdd){
			this.addImportType(importsType, importType);
		}
	}

	/**
	 * Test if this line code has a return value like
	 * <pre>
	 * 	return ...
	 * </pre>
	 * @return true if this line code has a return value. Otherwise it returns false.
	 */
	public boolean isReturn(){
		return false;
	}

	/**
	 * Test if this line must have an end of line delimiter. By default code line
	 * MUST have a end of line delimiter.
	 * 
	 * @return true if this line must have an end of line delimiter. Otherwise it returns false. 
	 */
	public boolean hasEndOfLine(){
		return true;
	}

	/**
	 * @return the globalImports
	 */
	protected List<String> getGlobalImports() {
		return globalImports;
	}

	/**
	 * @param globalImports the globalImports to set
	 */
	protected void setGlobalImports(List<String> globalImports) {
		this.globalImports = globalImports;
	}

	public void addGlobalImport(String canonicalImportType){
		this.getGlobalImports().add(canonicalImportType);
	}
	/*
	public String convertCode(){
		return this.getCode();
	}

	public void setCodeCommentSimple(String comment){
		this.setCode("// " + comment);
	}

	public void setCodeComment(String comment){
		this.setCode("/* " + comment + "*");
	}
	public void setCodeCommentDoc(String comment){
		this.setCode("/** " + comment + "*");
	}

	public void setCodeReturnNull(){
		this.setCode("return null");
	}

	public void setCodeReturnVariable(String variableName){
		this.setCode("return " + variableName);
	}

	public void setCodeReturnCastTypeVariable(String variableName, String canonicalType){
		this.setCode("return (" + PackageHelper.getClassSimpleName(canonicalType) + ") " + variableName);
		this.addImportType(canonicalType);
	}

	public void setCodeReturnCastCollectionTypeVariable(String variableName, String collectionCanonicalType, String canonicalType){
		this.setCode("return (" + PackageHelper.getClassSimpleName(collectionCanonicalType) + "<" + PackageHelper.getClassSimpleName(canonicalType) + ">) " + variableName);
		this.addImportsType(canonicalType, collectionCanonicalType);
	}

	public void setCodeReturnCallingVariableMethod(String variableName, String variableMethod, String ...parametersNames){
		if(parametersNames != null ){
			this.setCode("return " + variableName + "." + variableMethod + "(" + StringHelper.convertToPlainString(parametersNames, ", ") + ")");
		} else {
			this.setCode("return " + variableName + "." + variableMethod + "()");
		}
	}

	public void setCodeCallingVariableMethod(String variableName, String variableMethod, String ...parametersNames){
		this.setCode(variableName + "." + variableMethod + "(" + StringHelper.convertToPlainString(parametersNames, ", ") + ")");
	}

	public void setCodeCallingStaticMethod(String variableMethod, String ...parametersNames){
		this.setCode(variableMethod + "(" + StringHelper.convertToPlainString(parametersNames, ", ") + ")");
	}

	public void setCodeCallingStaticMethod(String canonicalClass, String classMethod, String ...parametersNames){
		if(parametersNames != null){
			this.setCode(PackageHelper.getClassSimpleName(canonicalClass) + "." + classMethod + "(" + StringHelper.convertToPlainString(parametersNames, ", ") + ")");
		} else {
			this.setCode(PackageHelper.getClassSimpleName(canonicalClass) + "." + classMethod + "()");
		}
	}*/
}
