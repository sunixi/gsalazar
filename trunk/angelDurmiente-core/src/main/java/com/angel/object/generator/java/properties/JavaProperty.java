/**
 * 
 */
package com.angel.object.generator.java.properties;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.Importable;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.JavaAnnotation;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaProperty extends JavaParameter implements CodeConvertible, Importable {

	private Visibility visibility;
	private TypeModifier typeModifier;
	private String propertyValue;
	private List<JavaAnnotation> annotations;
	
	public JavaProperty(String propertyName){
		super(StringHelper.EMPTY_STRING);
		super.setParameterName(propertyName);
		this.setVisibility(Visibility.PRIVATE);
		this.setTypeModifier(TypeModifier.NONE);
		this.setAnnotations(new ArrayList<JavaAnnotation>());
	}
	
	public JavaProperty(String propertyName, String propertyType){
		this(propertyName);
		this.setParameterType(propertyType);
	}

	/**
	 * @return the visibility
	 */
	public Visibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	
	public String getSimpleTypeName(){
		return PackageHelper.getClassSimpleName(this.getParameterType());
	}
	
	public String getCanonicalTypeName(){
		return this.getParameterType();
	}

	/**
	 * @return the typeModifier
	 */
	public TypeModifier getTypeModifier() {
		return typeModifier;
	}

	/**
	 * @param typeModifier the typeModifier to set
	 */
	public void setTypeModifier(TypeModifier typeModifier) {
		this.typeModifier = typeModifier;
	}

	/**
	 * @return the annotations
	 */
	public List<JavaAnnotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(List<JavaAnnotation> annotations) {
		this.annotations = annotations;
	}

	public String convertCode() {
		String codeConverter = "\t";
		codeConverter += this.convertAnnotations();
		codeConverter += "\t" + this.getVisibility().getVisibility() + " "; 
		codeConverter += this.hasTypeModifier() ? this.getTypeModifier().getTypeModifier() + " " : "";
		codeConverter += this.getSimpleTypeName() + " " + this.getParameterName();
		codeConverter += this.hasPropertyValue() ? " = " + this.getPropertyValue() + ";\n": ";\n";
		return codeConverter;
	}
	
	public boolean hasPropertyValue(){
		return StringHelper.isNotEmpty(this.getPropertyValue());
	}
	
	public boolean hasTypeModifier(){
		return this.getTypeModifier() != null;
	}
	
	protected String convertAnnotations(){
		String codeConverter = "";
		for(CodeConvertible ja: this.getAnnotations()){
			codeConverter += ja.convertCode();
		}
		return codeConverter;
	}

	public List<String> getImportsType() {
		List<String> imports = new ArrayList<String>();
		imports.add(this.getCanonicalTypeName());
		for(JavaAnnotation ja: this.getAnnotations()){
			imports.addAll(ja.getImportsType());
		}
		return imports;
	}

	public void addAnnotation(String canonicalName) {
		this.getAnnotations().add(new JavaAnnotation(canonicalName));
	}

	public void setFinalStaticTypeModifier() {
		this.setTypeModifier(TypeModifier.FINAL_STATIC);
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

	public void setPublicVisibility() {
		this.setVisibility(Visibility.PUBLIC);
	}
}
