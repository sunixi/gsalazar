/**
 * 
 */
package com.angel.object.generator.java.properties;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotationProperty extends JavaParameter implements CodeConvertible, Importable {

	private String propertyValue;

	public JavaAnnotationProperty(String propertyName){
		super(propertyName, StringHelper.EMPTY_STRING);
	}

	public JavaAnnotationProperty(String propertyName, String propertyValue){
		this(propertyName);
	}
	
	public String convert() {
		return this.getParameterName() + " = " + this.getPropertyValue();
	}
	
	public void addPropertyValueClass(String classType){
		super.setParameterType(classType);
		this.setPropertyValue(PackageHelper.getClassSimpleName(classType) + ".class");
	}
	
	public void addPropertyValue(String value){
		this.setPropertyValue(value);
	}
	
	public void addPropertyValue(String importType, String value){
		this.setPropertyValue(value);
		this.setParameterType(importType);
	}
	
	public void addPropertyValueString(String stringValue){
		this.setPropertyValue("\"" + stringValue + "\"");
	}
	
	public void addPropertyValueNumber(Number number){
		this.setPropertyValue(number.toString());
	}

	public List<String> getImportsType() {
		List<String> imports = new ArrayList<String>();
		imports.add(super.getCanonicalReturnTypeName());
		return imports;
	}

	/**
	 * @return the propertyValue
	 */
	public String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
}
