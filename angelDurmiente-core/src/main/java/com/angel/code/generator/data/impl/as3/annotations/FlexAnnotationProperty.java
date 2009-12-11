/**
 * 
 */
package com.angel.code.generator.data.impl.as3.annotations;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.Importable;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexAnnotationProperty extends JavaParameter implements CodeConvertible, Importable {

	private String propertyValue;

	public FlexAnnotationProperty(String propertyName){
		super(propertyName, StringHelper.EMPTY_STRING);
	}

	public FlexAnnotationProperty(String propertyName, String propertyValue){
		this(propertyName);
		this.setPropertyValue(propertyValue);
	}
	
	public String convertCode() {
		return this.getName() + " = " + this.getPropertyValue();
	}
	
	public void addPropertyValueClass(String canonicalClassType){
		super.setCanonicalType(canonicalClassType);
		this.setPropertyValue(PackageHelper.getClassSimpleName(canonicalClassType) + ".class");
	}
	
	public void addPropertyValue(String value){
		this.setPropertyValue(value);
	}
	
	public void addPropertyValue(String canonicalType, String value){
		this.setPropertyValue(value);
		this.setCanonicalType(canonicalType);
	}
	
	public void addPropertyValueString(String stringValue){
		this.setPropertyValue("\"" + stringValue + "\"");
	}
	
	public void addPropertyValueNumber(Number number){
		this.setPropertyValue(number.toString());
	}

	public List<String> getImportsType() {
		List<String> imports = new ArrayList<String>();
		imports.add(super.getCanonicalType());
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

	public boolean hasName(String name) {
		return this.getName().equalsIgnoreCase(name);
	}
}
