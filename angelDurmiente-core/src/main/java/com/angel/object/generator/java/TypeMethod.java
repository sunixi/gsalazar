/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.enums.Visibility;
import com.angel.object.generator.java.types.JavaType;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class TypeMethod {

	private static final String IMPORT_PREFIX = "import ";
	private static final String JAVA_END_OF_LINE = ";";

	private JavaType ownerType;
	private String methodName;
	private List<JavaParameter> parameters;
	private JavaParameter returnType;
	private String contentMethod;
	private boolean implemented = true;
	private Visibility visibility;

	public TypeMethod(String methodName){
		super();
		this.setMethodName(methodName);
		this.setVisibility(Visibility.PUBLIC);
	}
	
	public TypeMethod(String methodName, List<JavaParameter> parameters){
		this(methodName);
		this.setParameters(parameters);
	}

	public TypeMethod(String methodName, List<JavaParameter> parameters, boolean implemented){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setImplemented(implemented);
	}

	public TypeMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType, String contentMethod){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setContentMethod(contentMethod);
	}
	
	public TypeMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType){
		this(methodName, parameters);
		this.setReturnType(returnType);
	}
	
	public TypeMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType, boolean implemented){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setImplemented(implemented);
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

	/**
	 * @return the returnType
	 */
	public JavaParameter getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(JavaParameter returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the contentMethod
	 */
	public String getContentMethod() {
		return contentMethod;
	}

	/**
	 * @param contentMethod the contentMethod to set
	 */
	public void setContentMethod(String contentMethod) {
		this.contentMethod = contentMethod;
	}

	/**
	 * @return the implemented
	 */
	public boolean isImplemented() {
		return implemented;
	}

	/**
	 * @param implemented the implemented to set
	 */
	public void setImplemented(boolean implemented) {
		this.implemented = implemented;
	}
	
	public void setNotImplemented(){
		this.setImplemented(false);
	}
	
	public void setImplemented(){
		this.setImplemented(true);
	}

	public String getMethod(){
		String method = "";
		method += "\t" + this.getVisibility().getVisibility() + " ";
		method += this.hasReturnType() ? this.convertReturnType() + " " : " void ";
		method += this.getMethodName() + "(";
		method += this.hasJavaParameters() ? this.getPlainJavaParametersNames() : "";
		method += ")";
		if(this.isImplemented()){
			method += " {\n";
			method += "\t\t" + this.convertContentMethod();
			method += "\n\t}\n\n";
		} else {
			method += ";\n";
		}
		return method;
	}
	
	protected String convertContentMethod(){
		String content = "";
		if(this.getContentMethod().contains("return")){
			content = this.getContentMethod().replace("return", "");
			content = "return " + this.convertCastReturnType() + content;
		} else {
			content = " " + this.getContentMethod();
		}
		return content;
	}
	
	protected String convertCastReturnType(){
		String convertReturnType = "";
		if(this.isReturnACollection()){
			convertReturnType = "(" + this.getReturnType().getSimpleReturnTypeName() + "<" + this.getSimpleOwnerType() + ">" + ")";
		}
		return convertReturnType;
	}
	
	protected String convertReturnType(){
		String convertReturnType = "";
		if(this.isReturnACollection()){
			convertReturnType = this.getReturnType().getSimpleReturnTypeName() + "<" + this.getSimpleOwnerType() + ">";
		} else {
			convertReturnType = this.getReturnType().getSimpleReturnTypeName();
		}
		return convertReturnType;
	}
	
	protected String getSimpleOwnerType(){
		return this.getOwnerType().getDomainObjectSimpleName();
	}
	
	public boolean isReturnACollection(){
		if(this.hasReturnType()){
			Class<?> returnTypeClass = ReflectionHelper.getClassFrom(this.getReturnType().getCanonicalReturnTypeName());
			if(returnTypeClass != null){
				return Collection.class.isAssignableFrom(returnTypeClass);
			}
		}
		return false;
	}
	
	public boolean hasJavaParameters(){
		return this.getParameters().size() > 0;
	}
	
	public boolean hasReturnType(){
		return this.getReturnType() != null;
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

	public List<String> getImports(){
		List<String> imports = new ArrayList<String>();
		if(this.hasReturnType()){
			if(this.getReturnType().isImportType()){
				this.addImport(imports, this.getReturnType().getCanonicalReturnTypeName());
			}
			if(this.isReturnACollection()){
				this.addImport(imports, this.getOwnerType().getDomainObjectCanonicalName());
			}
		}
		for(JavaParameter jp: this.getParameters()){
			this.addImport(imports, jp.getCanonicalReturnTypeName());
		}
		return imports;
	}

	protected void addImport(List<String> imports, String typeClass){
		String className = typeClass;
		if(!typeClass.startsWith(IMPORT_PREFIX)){
			className = IMPORT_PREFIX + typeClass + JAVA_END_OF_LINE;
		}
		if(!imports.contains(className)){
			imports.add(className);
		}
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
	 * @return the ownerType
	 */
	public JavaType getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(JavaType ownerType) {
		this.ownerType = ownerType;
	}
}
