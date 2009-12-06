/**
 * 
 */
package com.angel.code.generator.data.impl.java.properties;

import java.util.List;

import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaProperty extends JavaParameter implements DataProperty {

	private Visibility visibility;
	private TypeModifier typeModifier;
	private String propertyValue;
	
	public JavaProperty(String name){
		super(StringHelper.EMPTY_STRING);
		super.setName(name);
		this.setVisibility(Visibility.PRIVATE);
		this.setTypeModifier(TypeModifier.NONE);
	}
	
	public JavaProperty(String name, String canonicalType){
		this(name);
		this.setCanonicalType(canonicalType);
	}

	public JavaProperty() {
		this(StringHelper.EMPTY_STRING);
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
		return PackageHelper.getClassSimpleName(this.getCanonicalType());
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

	@Override
	public String convertCode() {
		String codeConverter = "";
		codeConverter += this.convertCodeDataAnnotations();
		codeConverter += this.convertCodeDataProperty();
		
		return codeConverter;
	}
	
	protected String convertCodeDataProperty() {
		String codeConverter = "";
		codeConverter += "\t" + this.getVisibility().getVisibility() + " "; 
		codeConverter += this.hasTypeModifier() ? this.getTypeModifier().getTypeModifier() + " " : "";
		codeConverter += this.getSimpleTypeName() + " " + this.getName();
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

	@Override
	public List<String> getImportsType() {
		List<String> imports = super.getImportsType();
		imports.add(this.getCanonicalType());
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

	public boolean hasName(String name) {
		return this.getName().equalsIgnoreCase(name);
	}

	public boolean isPrivate() {
		return this.isVisibility(Visibility.PRIVATE);
	}

	public boolean isPublic() {
		return this.isVisibility(Visibility.PUBLIC);
	}
	
	public boolean isVisibility(Visibility visibility){
		return this.getVisibility() == visibility;
	}

	public boolean isProtected() {
		return this.isVisibility(Visibility.PROTECTED);
	}
	
	public boolean isFinal() {
		return this.isTypeModifier(TypeModifier.FINAL);
	}
	
	public boolean isTypeModifier(TypeModifier typeModifier){
		return this.getTypeModifier() == typeModifier;
	}

	public boolean isFinalStatic() {
		return this.isTypeModifier(TypeModifier.FINAL_STATIC);
	}

	public boolean isStatic() {
		return this.isTypeModifier(TypeModifier.STATIC);
	}
}
