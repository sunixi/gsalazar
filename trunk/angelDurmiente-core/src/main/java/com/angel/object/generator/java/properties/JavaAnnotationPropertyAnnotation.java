/**
 * 
 */
package com.angel.object.generator.java.properties;

import java.util.List;

import com.angel.object.generator.java.JavaAnnotation;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotationPropertyAnnotation extends JavaAnnotationProperty {
	
	private JavaAnnotation javaAnnotation;

	public JavaAnnotationPropertyAnnotation(JavaAnnotation javaAnnotation) {
		super(javaAnnotation.getName());
		this.setJavaAnnotation(javaAnnotation);
	}

	/**
	 * @return the javaAnnotation
	 */
	public JavaAnnotation getJavaAnnotation() {
		return javaAnnotation;
	}

	/**
	 * @param javaAnnotation the javaAnnotation to set
	 */
	public void setJavaAnnotation(JavaAnnotation javaAnnotation) {
		this.javaAnnotation = javaAnnotation;
	}
	
	@Override
	public String convertCode() {
		return this.getJavaAnnotation().convertCode();
	}
	
	@Override
	public List<String> getImportsType() {
		return this.getJavaAnnotation().getImportsType();
	}
	
	public void addAnnotationProperty(JavaAnnotationProperty javaAnnotationProperty){
		this.getJavaAnnotation().addAnnotationProperty(javaAnnotationProperty);
	}
	
	public JavaAnnotationProperty createJavaAnnotationProperty(String propertyName){
		return this.getJavaAnnotation().createJavaAnnotationProperty(propertyName);
	}
	
	public JavaAnnotationProperty createJavaAnnotationProperty(String propertyName, String propertyValue){
		return this.getJavaAnnotation().createJavaAnnotationProperty(propertyName, propertyValue);
	}
	
	public JavaAnnotationMultiValueProperty createJavaAnnotationMultiValueProperty(String propertyName){
		return this.getJavaAnnotation().createJavaAnnotationMultiValueProperty(propertyName);
	}

	public JavaAnnotationMultiValueProperty createJavaAnnotationMultiValuePropertyEmpty(String propertyName) {
		return this.getJavaAnnotation().createJavaAnnotationMultiValuePropertyEmpty(propertyName);
	}
	
	@Override
	public String getPropertyValue(){
		return this.getJavaAnnotation().convertCode();
	}
}
