/**
 * 
 */
package com.angel.object.generator.java.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.JavaConstructor;




/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaClass extends JavaType {

	private List<JavaConstructor> constructors;
	
	public JavaClass(){
		super();
		this.setConstructors(new ArrayList<JavaConstructor>());
	}
	
	public JavaClass(String typeName) {
		super(typeName);
		this.setConstructors(new ArrayList<JavaConstructor>());
		this.initializeDefaultConstructor(typeName);
	}
	
	protected void initializeDefaultConstructor(String typeName){
		JavaConstructor javaConstructor = new JavaConstructor(typeName);
		javaConstructor.setContent("super();");
		this.addConstructor(javaConstructor);
	}

	/**
	 * @return the constructors
	 */
	public List<JavaConstructor> getConstructors() {
		return constructors;
	}

	/**
	 * @param constructors the constructors to set
	 */
	public void setConstructors(List<JavaConstructor> constructors) {
		this.constructors = constructors;
	}

	public void addConstructor(JavaConstructor javaConstructor){
		this.getConstructors().add(javaConstructor);
	}

	@Override
	public boolean isAnImplementationType() {
		return true;
	}

	@Override
	public String getJavaTypeIdentifierName() {
		return "class";
	}

	protected List<String> getConstructorsContent(){
		List<String> constructorContent = new ArrayList<String>();
		for(JavaConstructor jc: this.getConstructors()){
			constructorContent.add(jc.convert());
		}
		return constructorContent;
	}
	@Override
	public String convertContentJavaType() {
		List<String> constructorsContent = this.getConstructorsContent();
		return StringHelper.convertToPlainString(constructorsContent.toArray(), "\n\n");
	}

	@Override
	public void addTypesImports(List<String> imports) {
		for(JavaConstructor jc: this.getConstructors()){
			for(String pt: jc.getParameterTypes()){
				super.addImport(imports, pt);
			}
		}
	}
	
	public JavaConstructor createJavaConstructor(){
		JavaConstructor javaConstructor = new JavaConstructor(this.getTypeName());
		this.addConstructor(javaConstructor);
		return javaConstructor;
	}
}
