/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import java.util.List;

import com.angel.code.generator.data.ClassDataType;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaProperty;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataConstructor;
import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.data.types.impl.DataCommentImpl;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaClassDataType extends ClassDataType {
	
	protected final static String JAVA_FILE_EXTENSION = ".java";
	private String leftGeneric;
	private String rightGeneric;
	
	public JavaClassDataType(){
		super();
		this.setComment(new DataCommentImpl());
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataConstructor createDataConstructor() {
		DataConstructor dataConstructor = new JavaConstructor(this.getSimpleName());
		super.addConstructor(dataConstructor);
		return dataConstructor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createDataAnnotation(String canonicalName) {
		DataAnnotation dataAnnotation = new JavaAnnotation(canonicalName);
		super.addAnnotation(dataAnnotation);
		return (T) dataAnnotation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataInterface> T createDataInterface(String canonicalName) {
		DataInterface dataInterface = new JavaInterfaceDataType(canonicalName);
		super.addInterface(dataInterface);
		return (T) dataInterface;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataMethod> T createDataMethod(String name) {
		DataMethod dataMethod = new JavaDataMethod();
		dataMethod.setMethodName(name);
		super.addMethod(dataMethod);
		return (T) dataMethod;
	}

	@SuppressWarnings("unchecked")
	public <T extends DataMethod> T createDataMethod(String name, List<DataParameter> methodParameters){
		DataMethod dataMethod = new JavaDataMethod();
		dataMethod.setMethodName(name);
		dataMethod.setParameters(methodParameters);
		super.addMethod(dataMethod);
		return (T) dataMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataProperty> T createDataProperty(String name) {
		DataProperty dataProperty = new JavaProperty(name);
		super.addProperty(dataProperty);
		return (T) dataProperty;
	}

	@Override
	public String getDataTypeFileName() {
		return super.getSimpleName() + JAVA_FILE_EXTENSION;
	}

	/**
	 * Convert data type sign (with generics) to a representation code.
	 * 
	 * @return a code representation of data type sign (with generics).
	 */
	@Override
	protected String convertCodeDataTypeSign() {
		String codeConverted = "";
		codeConverted += "\npublic " + this.getDataTypeIdentifierName() + " " + this.getDataTypeSign();
		codeConverted += this.hasGenerics() ? " <" : "";
		codeConverted += this.hasLeftGeneric() ? this.getSimpleLeftGeneric() : (this.hasRightGeneric() && this.hasLeftGeneric() ? ", ": "");
		codeConverted += this.hasRightGeneric() ? this.getSimpleRightGeneric() : "";
		codeConverted += this.hasGenerics() ? "> " : "";
		return codeConverted;
	}

	@Override
	protected String convertCodeInheritSubDataType() {
		String codeConverted = "";
		if (this.hasSubDataType()) {
			JavaClassDataType javaClassDataType = (JavaClassDataType) this.getSubDataType();
			codeConverted += " extends " + javaClassDataType.getSimpleName();
			codeConverted += javaClassDataType.hasGenerics() ? "<" : "";
			codeConverted += javaClassDataType.hasLeftGeneric() ? javaClassDataType.getSimpleLeftGeneric() + (javaClassDataType.hasRightGeneric() && javaClassDataType.hasLeftGeneric() ? ", ": "") : "";
			codeConverted += javaClassDataType.hasRightGeneric() ? javaClassDataType.getSimpleRightGeneric() : "";
			codeConverted += javaClassDataType.hasGenerics() ? "> " : "";
		}
		return codeConverted;
	}
	
	/**
	 * Add all sub data type import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports
	 *            where imports must be added.
	 */
	@Override
	protected void addSubDataTypeImportsType(List<String> typesImports) {
		super.addSubDataTypeImportsType(typesImports);
		if (this.hasSubDataType()) {
			JavaClassDataType javaClassDataType = (JavaClassDataType) this.getSubDataType();
			if (javaClassDataType.hasLeftGeneric()) {
				this.addImport(typesImports, javaClassDataType.getLeftGeneric());
			}
			if (javaClassDataType.hasRightGeneric()) {
				this.addImport(typesImports, javaClassDataType.getRightGeneric());
			}
		}
	}

	@Override
	protected void addTypeImports(List<String> typesImports) {
		super.addTypeImports(typesImports);
		if (this.hasLeftGeneric()) {
			this.addImport(typesImports, this.getLeftGeneric());
		}
		if (this.hasRightGeneric()) {
			this.addImport(typesImports, this.getRightGeneric());
		}
	}

	public boolean hasGenerics() {
		return this.hasLeftGeneric() || this.hasRightGeneric();
	}

	/**
	 * @return the leftGeneric
	 */
	public String getLeftGeneric() {
		return leftGeneric;
	}

	/**
	 * @param leftGeneric the leftGeneric to set
	 */
	public void setLeftGeneric(String leftGeneric) {
		this.leftGeneric = leftGeneric;
	}

	/**
	 * @return the rightGeneric
	 */
	public String getRightGeneric() {
		return rightGeneric;
	}

	/**
	 * @param rightGeneric the rightGeneric to set
	 */
	public void setRightGeneric(String rightGeneric) {
		this.rightGeneric = rightGeneric;
	}

	public boolean hasRightGeneric(){
		return StringHelper.isNotEmpty(this.getRightGeneric());
	}

	public boolean hasLeftGeneric(){
		return StringHelper.isNotEmpty(this.getLeftGeneric());
	}

	public String getSimpleLeftGeneric(){
		return PackageHelper.getClassSimpleName(this.getLeftGeneric());
	}

	public String getSimpleRightGeneric(){
		return PackageHelper.getClassSimpleName(this.getRightGeneric());
	}
}
