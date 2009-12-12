/**
 * 
 */
package com.angel.code.generator.data.impl.as3.properties;

import com.angel.code.generator.data.enums.FlexTypeModifier;
import com.angel.code.generator.data.enums.FlexVisibility;
import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexProperty extends DataParameter implements DataProperty {

	private FlexVisibility visibility;
	private FlexTypeModifier typeModifier;
	private String propertyValue;
	private String propertyArrayValue;
	
	public FlexProperty(String name){
		super(StringHelper.EMPTY_STRING);
		this.setVisibility(FlexVisibility.PUBLIC);
		this.setTypeModifier(FlexTypeModifier.VAR);
		super.setName(name);
	}
	
	public FlexProperty(String name, String canonicalType){
		this(name);
		this.setCanonicalType(canonicalType);
	}

	public FlexProperty() {
		this(StringHelper.EMPTY_STRING);
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
		codeConverter += this.getName() + ":" + this.getFlexSimpleType();
		codeConverter += this.hasPropertyValue() ? " = " + this.getPropertyValue() + ";\n": ";\n";
		return codeConverter;
	}

	private String getFlexSimpleType(){
		return PackageHelper.getFlexClassSimpleName(this.getCanonicalType());
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

	public void addAnnotation(String canonicalName) {
		this.getAnnotations().add(new JavaAnnotation(canonicalName));
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
	
	public boolean isTypeModifier(Object typeModifier){
		return this.getTypeModifier() == typeModifier;
	}

	public boolean isFinalStatic() {
		return this.isTypeModifier(TypeModifier.FINAL_STATIC);
	}

	public boolean isStatic() {
		return this.isTypeModifier(TypeModifier.STATIC);
	}

	@Override
	public <T extends DataAnnotation> T createAnnotation(String canonicalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPrivate() {
		return this.isVisibility(FlexVisibility.PRIVATE);
	}

	public boolean isVisibility(Object visibility) {
		return this.getVisibility() == ((FlexVisibility) visibility);
	}

	/**
	 * @return the visibility
	 */
	public FlexVisibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(FlexVisibility visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the typeModifier
	 */
	public FlexTypeModifier getTypeModifier() {
		return typeModifier;
	}

	/**
	 * @param typeModifier the typeModifier to set
	 */
	public void setTypeModifier(FlexTypeModifier typeModifier) {
		this.typeModifier = typeModifier;
	}

	/**
	 * @return the propertyArrayValue
	 */
	public String getPropertyArrayValue() {
		return propertyArrayValue;
	}

	/**
	 * @param propertyArrayValue the propertyArrayValue to set
	 */
	public void setPropertyArrayValue(String propertyArrayValue) {
		this.propertyArrayValue = propertyArrayValue;
	}
}
