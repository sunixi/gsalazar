/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.types.DataConstructor;
import com.angel.code.generator.exceptions.CodeGeneratorException;




/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ClassDataType extends DataType {

	private List<DataConstructor> constructors;
	
	public ClassDataType(){
		super();
		this.setConstructors(new ArrayList<DataConstructor>());
	}
	
	public ClassDataType(String typeName) {
		super(typeName);
		this.setConstructors(new ArrayList<DataConstructor>());
	}

	public void addConstructor(DataConstructor dataConstructor){
		if(!this.isAnImplementationType()){
			throw new CodeGeneratorException("An not implementation data type [" + this.getCanonicalName() + "]cannot have Data Constructor.");
		}
		this.getConstructors().add(dataConstructor);
	}

	@Override
	public boolean isAnImplementationType() {
		return true;
	}

	@Override
	public String getDataTypeFileName() {
		return this.getSimpleName() + ".java";
	}

	@Override
	public String getDataTypeIdentifierName() {
		return "class";
	}

	/**
	 * @return the constructors
	 */
	public List<DataConstructor> getConstructors() {
		return constructors;
	}

	/**
	 * @param constructors the constructors to set
	 */
	public void setConstructors(List<DataConstructor> constructors) {
		this.constructors = constructors;
	}
	
	@Override
	protected void addTypeImports(List<String> typesImports){
		for(DataConstructor dataConstructor: this.getConstructors()){
			super.addImport(typesImports, dataConstructor.getImportsType());
		}
	}
	
	public boolean hasConstructors(){
		return this.getConstructors().size() > 0;
	}

	@Override
	protected String convertCodeContent() {
		String codeConverted = "\n\n";
		if(this.hasConstructors()){
			for(DataConstructor dataConstructor: this.getConstructors()){
				codeConverted += dataConstructor.convertCode();
			}
		}
		return codeConverted + "\n\n";
	}
}
