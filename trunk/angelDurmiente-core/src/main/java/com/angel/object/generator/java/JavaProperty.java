/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.java.enums.TypeModifier;
import com.angel.object.generator.java.enums.Visibility;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaProperty implements CodeConvertible, Importable {

	private Visibility visibility;
	private TypeModifier typeModifier;
	private String parameterType;
	private String parameterName;
	private List<JavaAnnotation> annotations;
	
	public JavaProperty(){
		super();
		this.setVisibility(Visibility.PRIVATE);
		this.setTypeModifier(TypeModifier.NONE);
		this.setAnnotations(new ArrayList<JavaAnnotation>());
	}
	
	public JavaProperty(String parameterType, String parameterName){
		this();
		this.setParameterType(parameterType);
		this.setParameterName(parameterName);
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

	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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

	/*
	 * private Visibility visibility;
	private TypeModifier typeModifier;
	 */
	public String convert() {
		String codeConverter = "\t";
		codeConverter += this.convertAnnotations();
		codeConverter += this.getVisibility().getVisibility() + " " + this.getTypeModifier().getTypeModifier() + " ";
		codeConverter += this.getSimpleTypeName() + " " + this.getParameterName() + ";\n";
		return codeConverter;
	}
	
	protected String convertAnnotations(){
		String codeConverter = "";
		for(CodeConvertible ja: this.getAnnotations()){
			codeConverter += ja.convert();
		}
		return codeConverter;
	}

	public List<String> getImportsType() {
		List<String> imports = new ArrayList<String>();
		imports.add(this.getCanonicalTypeName());
		return imports;
	}
}
