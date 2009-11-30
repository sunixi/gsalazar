/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.enums.Visibility;
import com.angel.object.generator.types.CodeConvertible;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaConstructor implements CodeConvertible {

	private String name;
	private List<JavaParameter> parameters;
	private String content;
	private Visibility visibility;

	public JavaConstructor(String name){
		super();
		this.setName(name);
		this.setVisibility(Visibility.PUBLIC);
		this.setParameters(new ArrayList<JavaParameter>());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parameters
	 */
	public List<JavaParameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<JavaParameter> parameters) {
		this.parameters = parameters;
	}

	public boolean hasJavaParameters(){
		return this.getParameters().size() > 0;
	}
	
	public List<String> getJavaParametersNames(){
		List<String> parametersNames = new ArrayList<String>();
		for(JavaParameter jp: this.getParameters()){
			parametersNames.add(jp.getParameter());
		}
		return parametersNames;
	}
	
	public String getPlainJavaParametersNames(){
		List<String> parametersNames = this.getJavaParametersNames();
		return StringHelper.convertToPlainString(parametersNames.toArray(), ",");
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImports(){
		List<String> imports = new ArrayList<String>();
		for(JavaParameter jp: this.getParameters()){
			this.addImport(imports, jp.getCanonicalReturnTypeName());
		}
		return imports;
	}

	protected void addImport(List<String> imports, String importClass){
		String importJavaClass = "import " + importClass + ";";
		if(!imports.contains(importJavaClass)){
			imports.add(importJavaClass);
		}
	}

	public String convert() {
		String method = "\t";
		method += this.getVisibility().getVisibility() + " ";
		method += this.getSimpleName() + "(";
		method += this.hasJavaParameters() ? this.getPlainJavaParametersNames() + ")" : ")";
		method += "\t{\n";
		method += "\t\t" + this.getContent() + "\n";
		method += "\t}\n\n";
		return method;
	}

	public List<String> getParameterTypes() {
		List<String> parametersTypes = new ArrayList<String>();
		for(JavaParameter jp: this.getParameters()){
			parametersTypes.add(jp.getCanonicalReturnTypeName());
		}
		return parametersTypes;
	}
	
	public String getSimpleName(){
		return this.getSimpleNameFor(this.getName());
	}
	
	protected String getSimpleNameFor(String classType){
		String[] packages = classType.split("\\.");
		if(packages != null && packages.length > 0){
			return packages[packages.length - 1];
		}
		return classType;
	}
}
