/**
 * 
 */
package com.angel.object.generator.java.properties;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.java.JavaAnnotation;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotationMultiValueProperty extends JavaAnnotationProperty {

	private List<String> importsType;
	private List<JavaAnnotationProperty> propertiesValues;
	
	public JavaAnnotationMultiValueProperty(String propertyName){
		super(propertyName, StringHelper.EMPTY_STRING);
		this.setPropertiesValues(new ArrayList<JavaAnnotationProperty>());
		this.setImportsType(new ArrayList<String>());
	}

	public String getSimpleTypeName(){
		return PackageHelper.getClassSimpleName(this.getParameterType());
	}
	
	public String getCanonicalTypeName(){
		return this.getParameterType();
	}

	public String convertPropertiesValues() {
		String codeConverted = "";
		for(JavaAnnotationProperty jap: this.getPropertiesValues()){
			if(this.getPropertiesValues().indexOf(jap) == this.getPropertiesValues().size() - 1){
				codeConverted += jap.getPropertyValue();
			} else {
				codeConverted += jap.getPropertyValue() + ",";
			}
		}
		return codeConverted;
	}

	@Override
	public String convert() {
		return this.getParameterName() + " = {" + this.convertPropertiesValues() + "}";
	}
	
	public boolean hasProperties(){
		return this.getPropertiesValues().size() > 0;
	}

	/**
	 * @return the importsType
	 */
	@Override
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
	 * @return the propertiesValues
	 */
	public List<JavaAnnotationProperty> getPropertiesValues() {
		return propertiesValues;
	}

	/**
	 * @param propertiesValues the propertiesValues to set
	 */
	public void setPropertiesValues(List<JavaAnnotationProperty> propertiesValues) {
		this.propertiesValues = propertiesValues;
	}
	
	protected void addImport(String canonicalClassType){
		this.getImportsType().add(canonicalClassType);
	}
	
	public JavaAnnotationProperty createJavaAnnotationProperty(String propertyName){
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(propertyName);
		this.addPropertyValue(javaAnnotationProperty);
		return javaAnnotationProperty;
	}
	
	public JavaAnnotationMultiValueProperty createJavaAnnotationMultiValueProperty(String propertyName){
		JavaAnnotationMultiValueProperty javaAnnotationProperty = new JavaAnnotationMultiValueProperty(propertyName);
		this.addPropertyValue(javaAnnotationProperty);
		return javaAnnotationProperty;
	}	
	
	protected void addPropertyValue(JavaAnnotationProperty javaAnnotationProperty){
		this.getPropertiesValues().add(javaAnnotationProperty);
	}
	
	@Override
	public void addPropertyValue(String propertyValue){
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(StringHelper.EMPTY_STRING);
		javaAnnotationProperty.setPropertyValue(propertyValue);
		this.addPropertyValue(javaAnnotationProperty);
	}

	@Override
	public void addPropertyValueClass(String classType){
		this.addImport(classType);
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(StringHelper.EMPTY_STRING);
		javaAnnotationProperty.addPropertyValueClass(PackageHelper.getClassSimpleName(classType));
		this.addPropertyValue(javaAnnotationProperty);
	}

	@Override
	public void addPropertyValueString(String stringValue){
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(StringHelper.EMPTY_STRING);
		javaAnnotationProperty.addPropertyValueString(stringValue);
		this.addPropertyValue(javaAnnotationProperty);
	}

	@Override	
	public void addPropertyValueNumber(Number number){
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(StringHelper.EMPTY_STRING);
		javaAnnotationProperty.addPropertyValueString(number.toString());
		this.addPropertyValue(javaAnnotationProperty);
	}
	
	public JavaAnnotationPropertyAnnotation createJavaAnnotationPropertyAnnotation(String canonicalAnnotationType){
		this.addImport(canonicalAnnotationType);
		JavaAnnotation javaAnnotation = new JavaAnnotation(canonicalAnnotationType);
		JavaAnnotationPropertyAnnotation javaAnnotationProperty = new JavaAnnotationPropertyAnnotation(javaAnnotation);
		this.addPropertyValue(javaAnnotationProperty);
		return javaAnnotationProperty;
	}
}
