/**
 * 
 */
package com.angel.object.generator.java.properties;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotationProperty extends JavaParameter implements CodeConvertible, Importable {

	private List<JavaBlockCode> propertiesValues;
	
	public JavaAnnotationProperty(String propertyName){
		super(propertyName, StringHelper.EMPTY_STRING);
		this.setPropertiesValues(new ArrayList<JavaBlockCode>());
	}

	public String getSimpleTypeName(){
		return PackageHelper.getClassSimpleName(this.getParameterType());
	}
	
	public String getCanonicalTypeName(){
		return this.getParameterType();
	}

	public String convertPropertiesValues() {
		String codeConverted = "";
		for(JavaBlockCode jbc: this.getPropertiesValues()){
			if(this.getPropertiesValues().indexOf(jbc) == this.getPropertiesValues().size() - 1){
				codeConverted += jbc.convert();
			} else {
				codeConverted += jbc.convert() + ",";
			}
		}
		return codeConverted;
	}
	
	public String convert() {
		String codeConverter = " ";
		if(this.hasMoreThanOneProperties()){
			codeConverter += this.getParameterName() + " = {" + this.convertPropertiesValues() + "}";
		} else {
			codeConverter += this.getParameterName() + " = " + this.convertPropertiesValues();
		}
		return codeConverter;
	}
	
	public boolean hasProperties(){
		return this.getPropertiesValues().size() > 0;
	}
	
	public boolean hasMoreThanOneProperties(){
		return this.getPropertiesValues().size() > 1;
	}

	public List<String> getImportsType() {
		List<String> imports = new ArrayList<String>();
		imports.add(this.getCanonicalTypeName());
		return imports;
	}

	/**
	 * @return the propertiesValues
	 */
	public List<JavaBlockCode> getPropertiesValues() {
		return propertiesValues;
	}

	/**
	 * @param propertiesValues the propertiesValues to set
	 */
	public void setPropertiesValues(List<JavaBlockCode> propertiesValues) {
		this.propertiesValues = propertiesValues;
	}
	
	protected void addJavaBlockCode(JavaBlockCode javaBlockCode){
		this.getPropertiesValues().add(javaBlockCode);
	}

	//@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Usuario"} ),
	public void addPropertyValueAnnotation(String annotationCanonicalType, List<JavaAnnotationProperty> properties){
		List<String> typesImports = new ArrayList<String>();
		typesImports.add(annotationCanonicalType);
		JavaBlockCode javaBlockCode = new JavaBlockCode();
		javaBlockCode.setLineSeparator(",");
		String content = "@" + PackageHelper.getClassSimpleName(annotationCanonicalType);
		for(JavaAnnotationProperty jap: properties){
			if(properties.indexOf(jap) == properties.size() - 1){
				content += jap.convert();				
			} else {
				content += jap.convert() + ",";
			}
		}
		JavaLineCode javaLineCode = new JavaLineCode(content, typesImports);
		javaBlockCode.addLineCode(javaLineCode);
		this.addJavaBlockCode(javaBlockCode);
	}

	public void addPropertyValueCode(String code) {
		JavaBlockCode javaBlockCode = new JavaBlockCode();
		JavaLineCode javaLineCode = new JavaLineCode(code);
		javaBlockCode.setLineSeparator(",");
		javaBlockCode.addLineCode(javaLineCode);
		this.addJavaBlockCode(javaBlockCode);
	}
}
