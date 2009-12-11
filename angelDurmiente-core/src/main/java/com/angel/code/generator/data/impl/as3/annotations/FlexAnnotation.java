/**
 * 
 */
package com.angel.code.generator.data.impl.as3.annotations;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.Importable;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexAnnotation implements DataAnnotation {

	private String name;
	private List<FlexAnnotationProperty> properties;

	public FlexAnnotation(){
		super();
		this.setProperties(new ArrayList<FlexAnnotationProperty>());
	}

	public FlexAnnotation(String name){
		this();
		this.setName(name);
	}
	
	public FlexAnnotation(String annotationClass, List<FlexAnnotationProperty> properties){
		this(annotationClass);
		this.getProperties().addAll(properties);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the properties
	 */
	public List<FlexAnnotationProperty> getProperties() {
		return properties;
	}

	/**
	 * @return the canonicalType
	 */
	public String getCanonicalType() {
		return this.getName();
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<FlexAnnotationProperty> properties) {
		this.properties = properties;
	}

	public boolean hasProperties(){
		return this.getProperties().size() > 0;
	}

	public String convertCode() {
		String codeConverter = "[";
		codeConverter += this.getName();
		codeConverter += this.hasProperties() ? "(" + this.convertProperties() + ")" : "";
		codeConverter += "]";
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
		//importsType.add(this.getCanonicalType());
		for(Importable i: this.getProperties()){
			for(String imp: i.getImportsType()){
				if(StringHelper.isNotEmpty(imp)){
					importsType.add(imp);
				}
			}
		}
		return importsType;
	}

	public void addAnnotationProperty(FlexAnnotationProperty javaAnnotationProperty) {
		this.getProperties().add(javaAnnotationProperty);
	}
	
	public FlexAnnotationProperty createAnnotationProperty(String propertyName){
		FlexAnnotationProperty javaAnnotationProperty = new FlexAnnotationProperty(propertyName);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public FlexAnnotationProperty createAnnotationProperty(String propertyName, String propertyValue){
		FlexAnnotationProperty javaAnnotationProperty = this.createAnnotationProperty(propertyName);
		javaAnnotationProperty.setPropertyValue(propertyValue);
		return javaAnnotationProperty;
	}

	public FlexAnnotationProperty createAnnotationPropertyClass(String propertyname, String classCanonicalName) {
		FlexAnnotationProperty javaAnnotationProperty = new FlexAnnotationProperty(propertyname);
		javaAnnotationProperty.addPropertyValueClass(classCanonicalName);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public FlexAnnotationProperty createAnnotationPropertyBoolean(String propertyname, Boolean booleanValue) {
		FlexAnnotationProperty javaAnnotationProperty = new FlexAnnotationProperty(propertyname);
		javaAnnotationProperty.addPropertyValue(booleanValue.toString());
		javaAnnotationProperty.setCanonicalType(Boolean.class.getCanonicalName());
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public FlexAnnotationProperty createAnnotationPropertyString(String propertyName, String stringValue) {
		FlexAnnotationProperty javaAnnotationProperty = new FlexAnnotationProperty(propertyName);
		javaAnnotationProperty.addPropertyValueString(stringValue);
		this.addAnnotationProperty(javaAnnotationProperty);
		return javaAnnotationProperty;
	}

	public Object getParameterValue(String name) {
		for(FlexAnnotationProperty jap: this.getProperties()){
			if(jap.hasName(name)){
				return jap;
			}
		}
		return null;
	}

	public List<String> getParametersNames() {
		List<String> parametersNames = new ArrayList<String>();
		for(FlexAnnotationProperty jap: this.getProperties()){
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

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}	
}
