/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class DataParameter implements CodeConvertible, Importable {

	private String name;
	private String canonicalType;
	private List<DataAnnotation> annotations;
	private boolean isArray;
	
	public abstract <T extends DataAnnotation> T createAnnotation();

	public DataParameter(String name, String canonicalType){
		super();
		this.setName(name);
		this.setCanonicalType(canonicalType);
		this.setAnnotations(new ArrayList<DataAnnotation>());
	}
	
	public DataParameter(String parameterType){
		this(StringHelper.EMPTY_STRING, parameterType);
	}
	
	public DataParameter() {
		this(StringHelper.EMPTY_STRING);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the canonicalType
	 */
	public String getCanonicalType() {
		return canonicalType;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param canonicalType the canonicalType to set
	 */
	public void setCanonicalType(String canonicalType) {
		this.canonicalType = canonicalType;
	}

	public boolean equalsCanonicalType(DataParameter javaParameter){
		return this.getCanonicalType().equals(javaParameter.getCanonicalType());
	}
	/*
	public String getSimpleReturnTypeName(){
		return PackageHelper.getClassSimpleName(this.getCanonicalType());
	}

	public String getParameter() {
		return this.getSimpleReturnTypeName() + " " + (this.hasParameterName() ? this.getName() : "");
	}*/

	public boolean hasParameterName(){
		return StringHelper.isNotEmpty(this.getName());
	}
	
	@Override
	public String toString(){
		return this.getName();
	}

	public List<DataAnnotation> getAnnotations() {
		return this.annotations;
	}


	public String getSimpleType() {
		return PackageHelper.getClassSimpleName(this.getCanonicalType());
	}

	public String convertCode() {
		String convertedCode = "";
		convertedCode += this.convertCodeDataAnnotations();
		convertedCode += this.convertCodeDataParameter();
		return convertedCode;
	}

	/**
	 * Convert data type annotations to a representation code.
	 * 
	 * @return a code representation of data types annotations.
	 */
	protected String convertCodeDataParameter() {
		//return this.getSimpleType() + " " + this.getName();
		return this.getSimpleType() + (this.isArray() ? "[]" : "") + " " + this.getName();
	}

	/**
	 * Convert data type annotations to a representation code.
	 * 
	 * @return a code representation of data types annotations.
	 */
	protected String convertCodeDataAnnotations() {
		String convertedCode = "";
		for(DataAnnotation dataAnnotation: this.getAnnotations()){
			convertedCode += dataAnnotation.convertCode();
		}
		return convertedCode;
	}

	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		importsType.add(this.getCanonicalType());
		importsType.addAll(this.getImportsTypeDataAnnotations());
		return importsType;
	}
	
	/**
	 * Add all imports types used by data annotations.
	 *  
	 * @return a list with imports types used by data annotations.
	 */
	protected List<String> getImportsTypeDataAnnotations(){
		List<String> importsType = new ArrayList<String>();
		for(DataAnnotation dataAnnotation : this.getAnnotations()){
			importsType.addAll(dataAnnotation.getImportsType());
		}
		return importsType;
	}

	public boolean isCanonicalType(String canonicalType) {
		return this.getCanonicalType().equalsIgnoreCase(canonicalType);
	}

	public boolean hasName(String name) {
		return this.getName().equalsIgnoreCase(name);
	}

	public void addAnnotation(DataAnnotation dataAnnotation){
		if(this.hasAnnotation(dataAnnotation)){
			throw new CodeGeneratorException("Data parameter name [" + this.getName() + "] has a data annotation with name [" + dataAnnotation.getName() + "].");
		}
		this.getAnnotations().add(dataAnnotation);
	}

	public boolean hasAnnotation(DataAnnotation dataAnnotation) {
		return this.getAnnotation(dataAnnotation.getCanonicalType()) != null;
	}

	public DataAnnotation getAnnotation(String canonicalType){
		for (DataAnnotation da: this.getAnnotations()) {
			if (da.isCanonicalType(canonicalType)) {
				return da;
			}
		}
		return null;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(List<DataAnnotation> annotations) {
		this.annotations = annotations;
	}


	/**
	 * @return the isArray
	 */
	public boolean isArray() {
		return isArray;
	}

	/**
	 * @param isArray the isArray to set
	 */
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public void setArrayType() {
		this.setArray(true);
	}
}
