/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.impl.DataCommentImpl;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class DataConstructor {
/*
public List<DataParameter> getParameters();
	
	public int getQuantityParameters();
	
	public CodeBlock getContent();
 */
	private String name;
	private List<DataParameter> parameters;
	private CodeBlock content;
	private DataComment comment;

	public DataConstructor(String name){
		super();
		this.setName(name);
		this.setParameters(new ArrayList<DataParameter>());
		this.setContent(new CodeBlock());
		this.setComment(new DataCommentImpl());
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
	public List<DataParameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<DataParameter> parameters) {
		this.parameters = parameters;
	}

	public boolean hasParameters(){
		return this.getParameters().size() > 0;
	}
	
	public List<String> getParametersNames(){
		List<String> parametersNames = new ArrayList<String>();
		for(DataParameter jp: this.getParameters()){
			parametersNames.add(jp.getName());
		}
		return parametersNames;
	}
	
	public String getPlainParametersNames(){
		List<String> parametersNames = this.getParametersNames();
		return StringHelper.convertToPlainString(parametersNames.toArray(), ",");
	}

	protected void addImport(List<String> imports, String importClass){
		String importDataClass = "import " + importClass + ";";
		if(!imports.contains(importDataClass)){
			imports.add(importDataClass);
		}
	}

	public String convertCode() {
		String convertedCode = "";
		convertedCode += this.convertCodeDataComment();
		convertedCode += this.convertCodeConstructorSign();
		convertedCode += "\t{\n";
		convertedCode += "\t\t" + this.convertCodeContent() + "\n";
		convertedCode += "\t}\n\n";
		return convertedCode;
	}
	
	protected abstract String convertCodeConstructorSign();

	protected String convertCodeContent() {
		return this.getContent().convertCode();
	}
	
	protected String convertCodeDataComment() {
		return this.getComment().convertCode();
	}

	public List<String> getParameterTypes() {
		List<String> parametersTypes = new ArrayList<String>();
		for(DataParameter jp: this.getParameters()){
			parametersTypes.add(jp.getCanonicalType());
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

	public int getQuantityParameters() {
		return this.getParameters().size();
	}

	public List<String> getImportsType() {
		List<String> imports = new ArrayList<String>();
		for(DataParameter jp: this.getParameters()){
			this.addImport(imports, jp.getCanonicalType());
		}
		return imports;
	}

	/**
	 * @return the content
	 */
	public CodeBlock getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(CodeBlock content) {
		this.content = content;
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
}
