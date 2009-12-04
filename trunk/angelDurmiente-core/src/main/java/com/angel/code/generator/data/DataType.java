/**
 * 
 */
package com.angel.code.generator.data;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataComment;
import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.data.types.Importable;
import com.angel.code.generator.helpers.ImportsHelper;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class DataType implements CodeConvertible, Importable {

	private String canonicalName;
	
	private DataType subDataType;
	
	private List<DataMethod> methods;

	private List<DataInterface> interfaces;

	private DataComment comment;
	
	private Class<?> domainObject;
	
	private List<String> globalImports;
	
	private List<DataProperty> properties;
	
	private List<DataAnnotation> annotations;

	public DataType(){
		super();
		this.setMethods(new ArrayList<DataMethod>());
		this.setInterfaces(new ArrayList<DataInterface>());
		this.setGlobalImports(new ArrayList<String>());
		this.setProperties(new ArrayList<DataProperty>());
		this.setAnnotations(new ArrayList<DataAnnotation>());
	}
	
	public DataType(String canonicalDataType){
		this();
		this.setCanonicalName(canonicalDataType);
	}

	/**
	 * Define if it is an interfaces or a class implementation.
	 * 
	 * @return true if it is a concrete class implementation. Otherwise it returns false.
	 */
	public abstract boolean isAnImplementationType();

	/**
	 * Get data type identifier name. It returns class, interfaces, annotations.
	 *  
	 * @return java type identifier name.
	 */
	public abstract String getDataTypeIdentifierName();

	/**
	 * Get data type file name to be created.
	 * 
	 * @return a data type file name.
	 */
	public abstract String getDataTypeFileName();
	
	/**
	 * Add others type imports which this data type needs.
	 * This method is called at the end of {@link DataType#getImportsType()} method invocation.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * 
	 * @param typesImports where all imports are added.
	 */
	protected void addTypeImports(List<String> typesImports){
		//Do nothing.
	}
	
	/**
	 * Add domain object type imports.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports where imports must be added.
	 */
	protected void addDomainObjectImportsType(List<String> typesImports){
		this.addImport(typesImports, this.getDomainObject().getCanonicalName());
	}
	
	protected void addSubDataTypeImportsType(List<String> typesImports){
		if(this.hasSubDataType()){
			this.addImport(typesImports, this.getSubDataType().getCanonicalName());
			/*
			if(this.getSubDataType().hasLeftGeneric()){
				this.addImport(typesImports, this.getSubDataType().getLeftGeneric());
			}
			if(this.getSubDataType().hasRigthGeneric()){
				this.addImport(typesImports, this.getSubDataType().getRightGeneric());
			}*/
		}
	}

	/**
	 * Add all data methods import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports where imports must be added.
	 */
	protected void addMethodsImportsType(List<String> typesImports){
		for(DataMethod dataMethod: this.getMethods()){
			this.addImport(typesImports, dataMethod.getImportsType());			
		}
	}

	/**
	 * Add all data properties import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports where imports must be added.
	 */
	protected void addPropertiesImportsType(List<String> typesImports){
		for(DataProperty dataProperty: this.getProperties()){
			this.addImport(typesImports, dataProperty.getImportsType());			
		}
	}

	/**
	 * Add all data annotations import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports where imports must be added.
	 */
	protected void addAnnotationsImportsType(List<String> typesImports){
		for(DataAnnotation dataAnnotation: this.getAnnotations()){
			this.addImport(typesImports, dataAnnotation.getImportsType());			
		}
	}

	/**
	 * Add all data interfaces import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports where imports must be added.
	 */
	protected void addInterfacesImportsType(List<String> typesImports){
		for(DataInterface dataInterface: this.getInterfaces()){
			this.addImport(typesImports, dataInterface.getImportsType());			
		}
	}

	/**
	 * Add all data interfaces import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports where imports must be added.
	 */
	protected void addGlobalImportsType(List<String> typesImports){
		for(String globalImport: this.getGlobalImports()){
			this.addImport(typesImports, globalImport);			
		}
	}

	public List<String> getImportsType(){
		List<String> typesImports = new ArrayList<String>();
		/** Add domain object type import.*/
		this.addDomainObjectImportsType(typesImports);

		/** Add sub data type import.*/
		this.addSubDataTypeImportsType(typesImports);

		/** Add methods data type imports.*/
		this.addMethodsImportsType(typesImports);
		
		/** Add properties data type imports.*/
		this.addPropertiesImportsType(typesImports);

		/** Add annotations data type imports.*/
		this.addAnnotationsImportsType(typesImports);

		/** Add global data type imports.*/
		this.addGlobalImportsType(typesImports);

		/** Add specifics data types imports.*/
		this.addTypeImports(typesImports);
		return typesImports;
	}
	
	/**
	 * Add a canonical type in a imports list. It tests if canonical type contains import prefix, 
	 * end of line, and if imports list contains this canonical type.
	 * 
	 * @param imports list which contains all imports added. 
	 * @param canonicalType to add.
	 */
	protected void addImport(List<String> imports, String canonicalType){
		ImportsHelper.addImport(imports, canonicalType);
	}

	/**
	 * Add a canonical types in an import list.
	 * 
	 * @see {@link DataType#addImport(List, String)} this method is called for each canonical type.
	 * @param imports list which contains all imports added.
	 * @param canonicalTypes to add.
	 */
	protected void addImport(List<String> imports, List<String> canonicalTypes){
		for(String canonicalType: canonicalTypes){
			this.addImport(imports, canonicalType);
		}
	}

	/**
	 * Test if this data type has methods added.
	 * 
	 * @return true if it has more than one method. Otherwise it returns false.
	 */
	public boolean hasMethods(){
		return this.getMethods().size() > 0;
	}

	/**
	 * Test if this data type has annotations added.
	 * 
	 * @return true if it has more than one annotation. Otherwise it returns false.
	 */
	public boolean hasAnnotations(){
		return this.getAnnotations().size() > 0;
	}

	/**
	 * Test if this data type has properties added.
	 * 
	 * @return true if it has more than one property. Otherwise it returns false.
	 */
	public boolean hasProperties(){
		return this.getProperties().size() > 0;
	}

	/**
	 * Test if this data type has interfaces added.
	 * 
	 * @return true if it has more than one interface. Otherwise it returns false.
	 */
	public boolean hasInterfaces(){
		return this.getInterfaces().size() > 0;
	}

	/**
	 * Test if this data type has a sub type setted.
	 * 
	 * @return true if it has a sub data type setted. Otherwise it returns false.
	 */
	public boolean hasSubDataType(){
		return this.getSubDataType() != null;
	}

	/**
	 * Get base package of this data type. It is without the simple data type name.
	 * 
	 * @return base package name of this data type without simple data type name.
	 */
	public String getBasePackage(){
		return PackageHelper.getPackageOf(this.getCanonicalName());
	}
	
	public String convertCode(){
		String codeConverted = "";
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
		return codeConverted;
	}

	/**
	 * Convert end of data type file to a representation code.
	 *  
	 * @return a code representation of end of data type file.
	 */
	protected String convertCodeEndOfDataType() {
		return "\n}\n";
	}

	/**
	 * Convert data methods to a representation code.
	 * 
	 * @return a code representation of data methods.
	 */
	protected String convertCodeDataMethods() {
		String codeConverted = "";
		if(this.hasMethods()){
			for(DataMethod dataMethod: this.getMethods()){
				codeConverted += dataMethod.convertCode();
			}
		}
		return codeConverted + "\n";
	}

	/**
	 * Convert data type content to a representation code. It can be overwritten by others sub classes.
	 * This implementation return an empty string with two enters.
	 * 
	 * @return a code representation of data type content.
	 */
	protected String convertCodeContent() {
		return "\n\n";
	}

	/**
	 * Convert data properties to a representation code.
	 * 
	 * @return a code representation of data properties.
	 */
	protected String convertCodeDataProperties() {
		String codeConverted = "";
		if(this.hasProperties()){
			for(DataProperty dataProperty: this.getProperties()){
				codeConverted += dataProperty.convertCode();
			}
		}
		return codeConverted + "\n\n";
	}

	/**
	 * Convert interfaces implementation signs to a representation code.
	 * 
	 * @return a code representation of interfaces implementation signs.
	 */
	protected String convertCodeImplementsInterfaces() {
		String codeConverted = "";
		if(this.hasInterfaces()){
			codeConverted += " implements ";
			for(DataInterface dataInterface: this.getInterfaces()){
				if(this.getInterfaces().indexOf(dataInterface) == 0){
					codeConverted += dataInterface.getSign();
				} else {
					codeConverted += ", " + dataInterface.getSign();
				}
			}
		}
		return codeConverted + " {\n\n";
	}

	/**
	 * Convert sub data type inherit to a representation code.
	 * 
	 * @return a code representation of sub data type inherit.
	 */
	protected String convertCodeInheritSubDataType() {
		String codeConverted = "";
		if(this.hasSubDataType()){
			codeConverted += " extends " + this.getSubDataType().getDataTypeSign();
		}
		return codeConverted;
	}

	/**
	 * Convert data type sign to a representation code.
	 * 
	 * @return a code representation of data type sign.
	 */
	protected String convertCodeDataTypeSign() {
		return "public " + this.getDataTypeIdentifierName() + " " + this.getDataTypeSign();
	}

	/**
	 * Get the sign of this data type. It is the simple data type name (without the base package name).
	 * 
	 * @return a data type name without its base package name.
	 */
	protected String getDataTypeSign() {
		return this.getSimpleName();
	}

	/**
	 * Get a simple data type of this data type. It is without of package name.
	 * 
	 * @return a simple name of this data type.
	 */
	public String getSimpleName(){
		return PackageHelper.getClassSimpleName(this.getCanonicalName());
	}

	/**
	 * Convert data type annotations to a representation code.
	 * 
	 * @return a code representation of data types annotations.
	 */
	protected String convertCodeDataAnnotations() {
		String codeConverted = "";
		for(DataAnnotation dataAnnotation: this.getAnnotations()){
			codeConverted += dataAnnotation.convertCode();
		}
		return codeConverted;
	}

	/**
	 * Convert data type comment to a representation code.
	 * 
	 * @return a code representation of this data type comment.
	 */
	protected String convertCodeDataTypeComment() {
		return this.getComment().convertCode();
	}

	/**
	 * Convert imports sentences to a code representation.
	 * 
	 * @return a code representation of imports data types.
	 */
	protected String convertCodeImportsType() {
		return StringHelper.convertToPlainString(this.getImportsType().toArray(), "\n");
	}

	/**
	 * Convert package data type to a code representation.
	 *  
	 * @return a code representation of this data type package.
	 */
	protected String convertCodePackage() {
		return "package " + this.getBasePackage() + ";\n\n";
	}

	/**
	 * @return the domainObject
	 */
	public Class<?> getDomainObject() {
		return domainObject;
	}

	/**
	 * @param domainObject the domainObject to set
	 */
	public void setDomainObject(Class<?> domainObject) {
		this.domainObject = domainObject;
	}	
	
	public String getDomainObjectSimpleName(){
		return this.getDomainObject().getSimpleName();
	}

	public String getDomainObjectCanonicalName() {
		return this.getDomainObject().getCanonicalName();
	}


	public void addGlobalImport(String classNameImport) {
		this.getGlobalImports().add(classNameImport);		
	}

	/**
	 * @return the globalImports
	 */
	protected List<String> getGlobalImports() {
		return globalImports;
	}

	/**
	 * @param globalImports the globalImports to set
	 */
	protected void setGlobalImports(List<String> globalImports) {
		this.globalImports = globalImports;
	}

	/**
	 * @return the canonicalName
	 */
	public String getCanonicalName() {
		return canonicalName;
	}

	/**
	 * @param canonicalName the canonicalName to set
	 */
	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	/**
	 * @return the subDataType
	 */
	public DataType getSubDataType() {
		return subDataType;
	}

	/**
	 * @param subDataType the subDataType to set
	 */
	public void setSubDataType(DataType subDataType) {
		this.subDataType = subDataType;
	}

	/**
	 * @return the methods
	 */
	public List<DataMethod> getMethods() {
		return methods;
	}

	/**
	 * @param methods the methods to set
	 */
	public void setMethods(List<DataMethod> methods) {
		this.methods = methods;
	}

	/**
	 * @return the interfaces
	 */
	public List<DataInterface> getInterfaces() {
		return interfaces;
	}

	/**
	 * @param interfaces the interfaces to set
	 */
	public void setInterfaces(List<DataInterface> interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * @return the comment
	 */
	public DataComment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(DataComment comment) {
		this.comment = comment;
	}

	/**
	 * @return the properties
	 */
	public List<DataProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<DataProperty> properties) {
		this.properties = properties;
	}

	/**
	 * @return the annotations
	 */
	public List<DataAnnotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(List<DataAnnotation> annotations) {
		this.annotations = annotations;
	}
}
