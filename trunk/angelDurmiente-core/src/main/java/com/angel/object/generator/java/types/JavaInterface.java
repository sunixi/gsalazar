/**
 * 
 */
package com.angel.object.generator.java.types;

import java.util.List;

import com.angel.object.generator.java.JavaConstructor;




/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaInterface extends JavaType {

	public JavaInterface(){
		super();
	}

	public JavaInterface(String typeName) {
		super(typeName);
	}

	@Override
	public boolean isAnImplementationType() {
		return false;
	}

	@Override
	public String getJavaTypeIdentifierName() {
		return "interface";
	}

	@Override
	public String convertContentJavaType() {
		return "";
	}

	@Override
	public void addTypesImports(List<String> imports) {
		// TODO.		
	}

	public JavaConstructor createJavaConstructor(){
		return null;
	}

	@Override
	public List<String> getImportsType() {
		List<String> importsTypes = super.getImportsType();
		super.addImport(importsTypes, this.getTypeName());
		return importsTypes;
	}
}
