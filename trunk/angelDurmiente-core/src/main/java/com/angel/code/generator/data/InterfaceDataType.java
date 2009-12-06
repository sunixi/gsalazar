/**
 * 
 */
package com.angel.code.generator.data;

import java.util.List;

import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.common.helpers.StringHelper;




/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class InterfaceDataType extends DataType implements DataInterface {

	public InterfaceDataType(){
		super();
	}

	public InterfaceDataType(String typeName) {
		super(typeName);
	}

	@Override
	public boolean isAnImplementationType() {
		return false;
	}

	@Override
	public String getDataTypeIdentifierName() {
		return "interface";
	}

	public String getSign() {
		return this.getSimpleName();
	}

	public boolean hasCanonicalName(String canonicalName) {
		return super.getCanonicalName().equalsIgnoreCase(canonicalName);
	}

	@Override
	public void addMethod(DataMethod method) {
		if (this.hasMethod(method.getName(), method
				.getParametersCanonicalTypes())) {
			throw new CodeGeneratorException("Method with name ["
					+ method.getName()
					+ "] and canonical parameters types ["
					+ StringHelper.convertToPlainString(method
							.getParametersCanonicalTypes().toArray(), ", ")
					+ "] exist in data type.");
		}
		if(method.isImplemented()){
			throw new CodeGeneratorException("Method [" + method.getName() + "] cannot be implementation in a interface data type.");
		}
		method.setOwnerType(this);
		this.getMethods().add(method);
	}

	@Override
	public void addProperty(DataProperty dataProperty){
		if(this.hasProperty(dataProperty)){
			throw new CodeGeneratorException("Data type [" + this.getSimpleName() + "] has a data property with name [" + dataProperty.getName() + "].");
		}
		if(dataProperty.isPrivate()){
			throw new CodeGeneratorException("Data property cannot be [" + dataProperty.getVisibility() + "] in a interface data type.");
		}
		this.getProperties().add(dataProperty);
	}
	

	@Override
	public List<String> getImportsType() {
		List<String> importsType = super.getImportsType();
		super.addImport(importsType, this.getCanonicalName());
		return importsType;
	}
}
