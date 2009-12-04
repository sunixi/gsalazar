/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.types.CodeConvertible;
import com.angel.code.generator.data.types.Importable;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.properties.JavaParameter;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class TypeMethod implements CodeConvertible, Importable{

	private static final String IMPORT_PREFIX = "import ";
	private static final String JAVA_END_OF_LINE = ";";

	private JavaType ownerType;
	private String methodName;
	private List<JavaParameter> parameters;
	private JavaParameter returnType;
	private JavaBlockCode content;
	private boolean implemented = true;
	private TypeModifier typeModifier;
	private Visibility visibility;
	private JavaTypeComment comment;
	private List<JavaAnnotation> annotations;

	public TypeMethod(String methodName){
		super();
		this.setContent(new JavaBlockCode());
		this.setAnnotations(new ArrayList<JavaAnnotation>());
		this.setMethodName(methodName);
		this.setVisibility(Visibility.PUBLIC);
		this.setTypeModifier(TypeModifier.NONE);
		this.setComment(new JavaTypeComment("TODO Comentar método " + methodName + "."));
		this.getComment().clearTags();
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

	public TypeMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType, JavaBlockCode content){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setContent(content);
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
	
	protected String convertContent(){
		return this.getContent().convertCode();
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

	/**
	 * @return the content
	 */
	public JavaBlockCode getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(JavaBlockCode content) {
		this.content = content;
	}

	protected String convertComment(){
		return this.getComment().convertCode();
	}

	protected String convertAnnotations(){
		String codeConverted = "";
		for(CodeConvertible cc: this.getAnnotations()){
			codeConverted += cc.convertCode();
		}
		return codeConverted;
	}
	
	protected String convertVisibility(){
		return this.getVisibility().getVisibility();
	}
	
	protected String convertTypeModifier(){
		return this.getTypeModifier().getTypeModifier();
	}
	
	public String convertCode() {
		String method = this.convertComment();
		method += "\t" + this.convertAnnotations();
		method += "\t" + this.convertVisibility() + " " + this.convertTypeModifier();
		method += this.hasReturnType() ? " " + this.convertReturnType() + " ": " void ";
		method += this.getMethodName() + "(";
		method += this.hasJavaParameters() ? this.getPlainJavaParametersNames() : "";
		method += ")";
		if(this.isImplemented()){
			method += " {\n";
			method += this.convertContent();
			method += "\t}\n";
		} else {
			method += ";\n";
		}
		return method;
	}

	public List<String> getImportsType() {
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
		for(String i: this.getContent().getImportsType()){
			this.addImport(imports, i);
		}
		for(Importable i: this.getAnnotations()){
			for(String importClass: i.getImportsType()){
				this.addImport(imports, importClass);
			}
		}
		return imports;
	}

	/**
	 * @return the comment
	 */
	public JavaTypeComment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(JavaTypeComment comment) {
		this.comment = comment;
	}

	/**
	 * @return the annotations
	 */
	public List<JavaAnnotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(List<JavaAnnotation> annotations) {
		this.annotations = annotations;
	}
	
	/**
	 * @return the typeModifier
	 */
	public TypeModifier getTypeModifier() {
		return typeModifier;
	}

	/**
	 * @param typeModifier the typeModifier to set
	 */
	public void setTypeModifier(TypeModifier typeModifier) {
		this.typeModifier = typeModifier;
	}

	protected void addJavaAnnotation(JavaAnnotation javaAnnotation){
		this.getAnnotations().add(javaAnnotation);
	}
	
	public JavaAnnotation addJavaAnnotation(String canonicalAnnotationClass){
		JavaAnnotation javaAnnotation = new JavaAnnotation(canonicalAnnotationClass);
		this.addJavaAnnotation(javaAnnotation);
		return javaAnnotation;
	}
	
	public void setStaticTypeModifier(){
		this.setTypeModifier(TypeModifier.STATIC);
	}
	
	public void setFinalStaticTypeModifier(){
		this.setTypeModifier(TypeModifier.FINAL_STATIC);
	}

	public void setNoneModifier(){
		this.setTypeModifier(TypeModifier.NONE);
	}

	public void setFinalTypeModifier(){
		this.setTypeModifier(TypeModifier.FINAL);
	}
}
