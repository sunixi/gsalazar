/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.List;

import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class CodeLine implements CodeConvertible, Importable{

	private List<String> importsType;
	private String code;
	
	public CodeLine(){
		super();
	}

	/**
	 * @return the importsType
	 */
	public List<String> getImportsType() {
		return importsType;
	}

	/**
	 * @param importsType the importsType to set
	 */
	public void setImportsType(List<String> importsType) {
		this.importsType = importsType;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public void addImportType(String importType){
		this.getImportsType().add(importType);
	}

	public void addImportsType(String ...importsType){
		if(importsType != null && importsType.length > 0){
			for(String importType: importsType){
				this.addImportType(importType);
			}
		}
	}

	public void addImportsType(List<String> importsType){
		for(String importType: importsType){
			this.addImportType(importType);
		}
	}

	public String convertCode(){
		return this.getCode();
	}

	public void setCodeCommentSimple(String comment){
		this.setCode("// " + comment);
	}

	public void setCodeComment(String comment){
		this.setCode("/* " + comment + "*/");
	}

	public void setCodeCommentDoc(String comment){
		this.setCode("/** " + comment + "*/");
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
	}
}
