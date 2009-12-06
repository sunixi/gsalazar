/**
 * 
 */
package com.angel.code.generator.data.impl.java.annotations;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.Importable;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotation implements DataAnnotation {

	private String canonicalType;
	private List<JavaAnnotationProperty> properties;

	public JavaAnnotation(){
		super();
		this.setProperties(new ArrayList<JavaAnnotationProperty>());
	}

	public JavaAnnotation(String canonicalType){
		this();
		this.setCanonicalType(canonicalType);
	}
	
	public JavaAnnotation(String annotationClass, List<JavaAnnotationProperty> properties){
		this(annotationClass);
		this.getProperties().addAll(properties);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return "@" + PackageHelper.getClassSimpleName(this.getCanonicalType());
	}

	/**
	 * @return the properties
	 */
	public List<JavaAnnotationProperty> getProperties() {
		return properties;
	}

	/**
	 * @return the canonicalType
	 */
	public String getCanonicalType() {
		return canonicalType;
	}

	/**
	 * @param canonicalType the canonicalType to set
	 */
	public void setCanonicalType(String canonicalType) {
		this.canonicalType = canonicalType;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<JavaAnnotationProperty> properties) {
		this.properties = properties;
	}

	public boolean hasProperties(){
		return this.getProperties().size() > 0;
	}

	public String convertCode() {
		String codeConverter = "";
		codeConverter += this.getName();
		codeConverter += this.hasProperties() ? "(\n\t" + this.convertProperties() + "\n)\n" : "";
		return codeConverter;
	}

	public String convertProperties() {
		String codeConverter = "";
		for(CodeConvertible cc: this.getProperties()){
			if(this.getProperties().indexOf(cc) == 0){
				codeConverter += cc.convertCode();	
			} else {
				codeConverter += "," + cc.convertCode();
			}
		}
		return codeConverter;
	}

	/**
	 * @return the importsType
	 */
	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		importsType.add(this.getCanonicalType());
		for(Importable i: this.getProperties()){
			for(String imp: i.getImportsType()){
				if(StringHelper.isNotEmpty(imp)){
					importsType.add(imp);
				}
			}
		}
		return importsType;
	}

	public void addAnnotationProperty(JavaAnnotationProperty javaAnnotationProperty) {
		this.getProperties().add(javaAnnotationProperty);
	}
	
	public JavaAnnotationProperty createJavaAnnotationProperty(String propertyName){
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(propertyName);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public JavaAnnotationProperty createJavaAnnotationProperty(String propertyName, String propertyValue){
		JavaAnnotationProperty javaAnnotationProperty = this.createJavaAnnotationProperty(propertyName);
		javaAnnotationProperty.setPropertyValue(propertyValue);
		return javaAnnotationProperty;
	}
	
	public JavaAnnotationMultiValueProperty createJavaAnnotationMultiValueProperty(String propertyName){
		JavaAnnotationMultiValueProperty javaAnnotationProperty = new JavaAnnotationMultiValueProperty(propertyName);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}
	
	public JavaAnnotationPropertyAnnotation createJavaAnnotationPropertyAnnotation(String canonicalAnnotationType){
		JavaAnnotation javaAnnotation = new JavaAnnotation(canonicalAnnotationType);
		JavaAnnotationPropertyAnnotation javaAnnotationProperty = new JavaAnnotationPropertyAnnotation(javaAnnotation);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}
	
	public JavaAnnotationMultiValueProperty createJavaAnnotationMultiValuePropertyEmpty(String propertyName){
		JavaAnnotationMultiValueProperty javaAnnotationProperty = new JavaAnnotationMultiValueProperty(propertyName);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public JavaAnnotationProperty createJavaAnnotationPropertyClass(String propertyname, String classCanonicalName) {
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(propertyname);
		javaAnnotationProperty.addPropertyValueClass(classCanonicalName);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public JavaAnnotationProperty createjavaAnnotationPropertyBoolean(String propertyname, Boolean booleanValue) {
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(propertyname);
		javaAnnotationProperty.addPropertyValue(booleanValue.toString());
		javaAnnotationProperty.setCanonicalType(Boolean.class.getCanonicalName());
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public JavaAnnotationProperty createJavaAnnotationPropertyString(String propertyName, String stringValue) {
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(propertyName);
		javaAnnotationProperty.addPropertyValueString(stringValue);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public Object getParameterValue(String name) {
		for(JavaAnnotationProperty jap: this.getProperties()){
			if(jap.hasName(name)){
				return jap;
			}
		}
		return null;
	}

	public List<String> getParametersNames() {
		List<String> parametersNames = new ArrayList<String>();
		for(JavaAnnotationProperty jap: this.getProperties()){
			parametersNames.add(jap.getName());
		}
		return parametersNames;
	}

	public int getQuantityParameters() {
		return this.getProperties().size();
	}

	public boolean hasName(String name) {
		return this.getName().equalsIgnoreCase(name);
	}

	public boolean isCanonicalType(String canonicalType) {
		return this.getCanonicalType().equalsIgnoreCase(canonicalType);
	}
}
