/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataComment;
import com.angel.code.generator.data.types.DataConstructor;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaConstructor implements DataConstructor {

	private String name;
	private List<DataParameter> parameters;
	private CodeBlock content;
	private Visibility visibility;
	private DataComment comment;

	public JavaConstructor(String name){
		super();
		this.setName(name);
		this.setVisibility(Visibility.PUBLIC);
		this.setParameters(new ArrayList<DataParameter>());
		this.setContent(new CodeBlock());
		this.setComment(new JavaDataComment());
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

	public boolean hasJavaParameters(){
		return this.getParameters().size() > 0;
	}
	
	public List<String> getJavaParametersNames(){
		List<String> parametersNames = new ArrayList<String>();
		for(DataParameter jp: this.getParameters()){
			parametersNames.add(jp.getName());
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

	protected void addImport(List<String> imports, String importClass){
		String importJavaClass = "import " + importClass + ";";
		if(!imports.contains(importJavaClass)){
			imports.add(importJavaClass);
		}
	}

	public String convertCode() {
		String method = "";
		method += this.convertCodeDataComment();
		method += "\t" + this.getVisibility().getVisibility() + " ";
		method += this.getSimpleName() + "(";
		method += this.hasJavaParameters() ? this.getPlainJavaParametersNames() + ")" : ")";
		method += "\t{\n";
		method += "\t\t" + this.convertCodeContent() + "\n";
		method += "\t}\n\n";
		return method;
	}

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

	public void setPrivateVisibility() {
		this.setVisibility(Visibility.PRIVATE);
	}
	
	public void setProtectedVisibility() {
		this.setVisibility(Visibility.PROTECTED);
	}
	
	public void setPublicVisibility() {
		this.setVisibility(Visibility.PUBLIC);
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
