/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.InterfaceDataType;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaProperty;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.data.types.impl.DataCommentImpl;
import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaInterfaceDataType extends InterfaceDataType {

	protected final static String JAVA_FILE_EXTENSION = ".java";
	private String leftGeneric;
	private String rightGeneric;
	
	public JavaInterfaceDataType(){
		super();
		this.setComment(new DataCommentImpl());
	}
	
	public JavaInterfaceDataType(String canonicalName){
		this();
		this.setCanonicalName(canonicalName);
	}

	@Override
	public String getDataTypeFileName() {
		return super.getSimpleName() + JAVA_FILE_EXTENSION;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createDataAnnotation(String canonicalName) {
		DataAnnotation dataAnnotation = new JavaAnnotation(canonicalName);
		super.addAnnotation(dataAnnotation);
		return (T) dataAnnotation;
	}

	@Override
	public <T extends DataInterface> T createDataInterface(String canonicalName) {
		//TODO Debe poder crear una implements Interface, una interface?????.
		/*DataInterface dataInterface = new JavaInterfaceDataType(canonicalName);
		super.addInterface(dataInterface);
		return (T) dataInterface;*/
		throw new CodeGeneratorException("A interface data type [" + this.getSimpleName() + "] cannot create an interface implementation [" + canonicalName + "].");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataMethod> T createDataMethod(String name) {
		DataMethod dataMethod = this.buildDataMethod(name, new ArrayList<DataParameter>());
		super.addMethod(dataMethod);
		return (T) dataMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataMethod> T createDataMethod(String name, List<DataParameter> dataParameters) {
		DataMethod dataMethod = this.buildDataMethod(name, dataParameters);
		super.addMethod(dataMethod);
		return (T) dataMethod;
	}

	protected DataMethod buildDataMethod(String name, List<DataParameter> dataParameters){
		DataMethod dataMethod = new JavaDataMethod();
		dataMethod.setNotImplemented();
		dataMethod.setMethodName(name);
		dataMethod.setParameters(dataParameters);
		return dataMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataProperty> T createDataProperty(String name, String canonicalType) {
		JavaProperty dataProperty = new JavaProperty();
		dataProperty.setCanonicalType(canonicalType);
		super.addProperty(dataProperty);
		return (T) dataProperty;
	}

	public boolean hasName(String name) {
		return super.getSimpleName().equalsIgnoreCase(name);
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
		codeConverted += this.hasLeftGeneric() ? this.getLeftGeneric() : "";
		codeConverted += this.hasRightGeneric() ? this.getRightGeneric() : "";
		codeConverted += this.hasGenerics() ? "> " : "";
		return codeConverted;
	}

	public boolean hasGenerics() {
		return this.hasLeftGeneric() || this.hasRightGeneric();
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
			JavaInterfaceDataType javaInterfaceDataType = (JavaInterfaceDataType) this.getSubDataType();
			if (javaInterfaceDataType.hasLeftGeneric()) {
				this.addImport(typesImports, javaInterfaceDataType.getLeftGeneric());
			}
			if (javaInterfaceDataType.hasRightGeneric()) {
				this.addImport(typesImports, javaInterfaceDataType.getRightGeneric());
			}
		}
	}

	@Override
	protected void addTypeImports(List<String> typesImports) {
		//super.addTypeImports(typesImports);
		if (this.hasLeftGeneric()) {
			this.addImport(typesImports, this.getLeftGeneric());
		}
		if (this.hasRightGeneric()) {
			this.addImport(typesImports, this.getRightGeneric());
		}
	}

	@Override
	protected String convertCodeInheritSubDataType() {
		String codeConverted = "";
		if (this.hasSubDataType()) {
			JavaInterfaceDataType javaInterfaceDataType = (JavaInterfaceDataType) this.getSubDataType();
			codeConverted += " extends " + javaInterfaceDataType.getSimpleName();
			codeConverted += javaInterfaceDataType.hasGenerics() ? "<" : "";
			codeConverted += javaInterfaceDataType.hasLeftGeneric() ? javaInterfaceDataType.getSimpleLeftGeneric() + (javaInterfaceDataType.hasRightGeneric() && javaInterfaceDataType.hasLeftGeneric() ? ", ": "") : "";
			codeConverted += javaInterfaceDataType.hasRightGeneric() ? javaInterfaceDataType.getSimpleRightGeneric() : "";
			codeConverted += javaInterfaceDataType.hasGenerics() ? "> " : "";
		}
		return codeConverted;
	}

	public String getSimpleLeftGeneric(){
		return PackageHelper.getClassSimpleName(this.getLeftGeneric());
	}

	public String getSimpleRightGeneric(){
		return PackageHelper.getClassSimpleName(this.getRightGeneric());
	}

	@Override
	protected void buildCodeBlockForGetterAccesor(DataProperty dataProperty, DataMethod getterDataMethod) {
		throw new CodeGeneratorException("Cannot implements code for a getter accesorry [" + dataProperty.getName() + "] in a interface data type.");		
	}

	@Override
	protected void buildCodeBlockForSetterAccesor(DataProperty dataProperty, DataMethod setterDataMethod) {
		throw new CodeGeneratorException("Cannot implements code for a setter accesorry [" + dataProperty.getName() + "] in a interface data type.");
	}
}
