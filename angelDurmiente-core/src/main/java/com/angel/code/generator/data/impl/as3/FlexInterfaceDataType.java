/**
 * 
 */
package com.angel.code.generator.data.impl.as3;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.InterfaceDataType;
import com.angel.code.generator.data.impl.as3.annotations.FlexAnnotation;
import com.angel.code.generator.data.impl.as3.converter.FlexDataTypesConverter;
import com.angel.code.generator.data.impl.as3.converter.FlexDataTypesConverter.FlexTypeImport;
import com.angel.code.generator.data.impl.as3.properties.FlexProperty;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.code.generator.helpers.ImportsHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexInterfaceDataType extends InterfaceDataType {
	
	protected final static String ACTION_SCRIPT_FILE_EXTENSION = ".as";
	
	public FlexInterfaceDataType(){
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createDataAnnotation(String canonicalName) {
		FlexAnnotation flexAnnotation = new FlexAnnotation();
		flexAnnotation.setName(canonicalName);
		super.addAnnotation(flexAnnotation);
		return (T) flexAnnotation;
	}

	@Override
	protected void addDomainObjectImportsType(List<String> typesImports) {
		//Do nothing.
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataInterface> T createDataInterface(String canonicalName) {
		DataInterface dataInterface = new DataInterface(canonicalName);
		super.addInterface(dataInterface);
		return (T) dataInterface;
	}

	@Override
	public <T extends DataMethod> T createDataMethod(String name) {
		return this.createDataMethod(name, new ArrayList<DataParameter>());
	}

	@SuppressWarnings("unchecked")
	public <T extends DataMethod> T createDataMethod(String name, List<DataParameter> methodParameters){
		FlexDataMethod flexDataMethod = new FlexDataMethod();
		flexDataMethod.setMethodName(name);
		flexDataMethod.setNotImplemented();
		flexDataMethod.setParameters(methodParameters);
		super.addMethod(flexDataMethod);
		return (T) flexDataMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataProperty> T createDataProperty(String name, String canonicalType) {
		FlexProperty flexProperty = new FlexProperty(name);
		flexProperty.setCanonicalType(canonicalType);
		super.addProperty(flexProperty);
		return (T) flexProperty;
	}

	@Override
	public String getDataTypeFileName() {
		return super.getSimpleName() + ACTION_SCRIPT_FILE_EXTENSION;
	}

	@Override
	protected void addImport(List<String> imports, String canonicalType) {
		FlexTypeImport flexDataType = FlexDataTypesConverter.getInstance().getFlexTypeFrom(canonicalType);
		if(flexDataType != null && flexDataType.isImportType()){
			ImportsHelper.addImport(imports, flexDataType.getCanonicalType());
		}
	}
	
	/**
	 * Convert data type sign to a representation code.
	 * 
	 * @return a code representation of data type sign.
	 */
	@Override
	protected String convertCodeDataTypeSign() {
		return "\npublic " + this.getDataTypeIdentifierName() + " " + this.getDataTypeSign();
	}

	@Override
	protected String convertCodePackage() {
		String basePackage = "";
		if(this.hasBasePackage()){
			 basePackage = this.getBasePackage().endsWith(".") ? this.getBasePackage().substring(0, this.getBasePackage().length() - 1) : this.getBasePackage(); 
		}
		return "package " + basePackage + "{\n\n\t";
	}
/*
codeConverted += this.convertCodePackage();
		codeConverted += this.convertCodeImportsType();
		codeConverted += this.convertCodeDataTypeComment();
		codeConverted += this.convertCodeDataAnnotations();
		codeConverted += this.convertCodeDataTypeSign();
		codeConverted += this.convertCodeInheritSubDataType();
		codeConverted += this.convertCodeImplementsInterfaces();
		codeConverted += this.convertCodeDataProperties();
		codeConverted += this.convertCodeContent();
		codeConverted += this.convertCodeDataMethods();
		codeConverted += this.convertCodeEndOfDataType();
 */
	@Override
	protected String convertCodeInheritSubDataType() {
		String codeConverted = "";
		if (this.hasSubDataType()) {
			FlexInterfaceDataType flexClassDataType = (FlexInterfaceDataType) this.getSubDataType();
			codeConverted += " extends " + flexClassDataType.getSimpleName();
		}
		return codeConverted;
	}

	protected String convertCodeEndOfDataType() {
		return "\n}\n}";
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
			//Do Nothing.
		}
	}

	@Override
	protected void addTypeImports(List<String> typesImports) {
		//Do Nothing.
	}

	@Override
	protected void buildCodeBlockForGetterAccesor(DataProperty dataProperty, DataMethod getterDataMethod) {
		throw new CodeGeneratorException("An interface datat type [" + this.getSimpleName() + "] cannot build code block for an accesory [" + dataProperty.getName() + "].");		
	}

	@Override
	protected void buildCodeBlockForSetterAccesor(DataProperty dataProperty, DataMethod setterDataMethod) {
		throw new CodeGeneratorException("An interface datat type [" + this.getSimpleName() + "] cannot build code block for an accesory [" + dataProperty.getName() + "].");		
	}
}
