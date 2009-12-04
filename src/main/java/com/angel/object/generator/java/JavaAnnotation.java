/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.java.properties.JavaAnnotationMultiValueProperty;
import com.angel.object.generator.java.properties.JavaAnnotationProperty;
import com.angel.object.generator.java.properties.JavaAnnotationPropertyAnnotation;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotation implements CodeConvertible, Importable {
	
	private String name;
	private String annotationType;
	private List<JavaAnnotationProperty> properties;
	
	public JavaAnnotation(String annotationClass){
		super();
		this.setName("@" + PackageHelper.getClassSimpleName(annotationClass) + "\n");
		this.setAnnotationType(annotationClass);
		this.setProperties(new ArrayList<JavaAnnotationProperty>());
	}
	
	public JavaAnnotation(String annotationClass, List<JavaAnnotationProperty> properties){
		this(annotationClass);
		this.getProperties().addAll(properties);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the properties
	 */
	public List<JavaAnnotationProperty> getProperties() {
		return properties;
	}

	/**
	 * @return the annotationType
	 */
	public String getAnnotationType() {
		return annotationType;
	}

	/**
	 * @param annotationType the annotationType to set
	 */
	public void setAnnotationType(String annotationType) {
		this.annotationType = annotationType;
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

	public String convert() {
		String codeConverter = "";
		codeConverter += this.getName();
		codeConverter += this.hasProperties() ? "(\n\t" + this.convertProperties() + "\n)\n" : "";
		return codeConverter;
	}

	public String convertProperties() {
		String codeConverter = "";
		for(CodeConvertible cc: this.getProperties()){
			if(this.getProperties().indexOf(cc) == 0){
				codeConverter += cc.convert();	
			} else {
				codeConverter += "," + cc.convert();
			}
		}
		return codeConverter;
	}

	/**
	 * @return the importsType
	 */
	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		importsType.add(this.getAnnotationType());
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
		javaAnnotationProperty.setParameterType(Boolean.class.getCanonicalName());
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public JavaAnnotationProperty createJavaAnnotationPropertyString(String propertyName, String stringValue) {
		JavaAnnotationProperty javaAnnotationProperty = new JavaAnnotationProperty(propertyName);
		javaAnnotationProperty.addPropertyValueString(stringValue);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}
}
