/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaBlockCode implements CodeConvertible, Importable {

	private List<JavaLineCode> linesCode;
	private List<String> importsType;
	
	public JavaBlockCode(){
		super();
		this.setLinesCode(new ArrayList<JavaLineCode>());
		this.setImportsType(new ArrayList<String>());
	}

	/**
	 * @return the linesCode
	 */
	public List<JavaLineCode> getLinesCode() {
		return linesCode;
	}

	/**
	 * @param linesCode the linesCode to set
	 */
	public void setLinesCode(List<JavaLineCode> linesCode) {
		this.linesCode = linesCode;
	}

	/**
	 * @param importsType the importsType to set
	 */
	protected void setImportsType(List<String> importsType) {
		this.importsType = importsType;
	}

	public String convert() {
		String codeConverted = "";
		for(JavaLineCode jlc: this.getLinesCode()){
			codeConverted += jlc.convert();
		}
		return codeConverted;
	}

	public List<String> getImportsType() {
		return this.importsType;
	}
	
	protected void addJavaLineCode(JavaLineCode javaLineCode){
		this.getImportsType().addAll(javaLineCode.getImportsType());
		this.getLinesCode().add(javaLineCode);
	}
	
	/**
	 *	Add a line code with a null assigment to a class type instance.
	 * <pre>
	 *	Type variableName = null;
	 * </pre>
	 * 
	 * @param classType to null assigment.
	 * @param variableName to be assigned.
	 */
	public void addLineCodeNullAssigment(String classType, String variableName){
		String content = PackageHelper.getClassSimpleName(classType) + " " + variableName + " = null;";
		this.addJavaLineCode(new JavaLineCode(content, classType));
	}
	
	/**
	 * Add a line code with an assigment variable as a setter.
	 * <pre>
	 *	this.variableName = variableName;
	 * </pre>
	 * @param variableName to be assigned.
	 */
	public void addLineCodeThisAssigment(String variableName){
		String content = "this." + variableName + " = " + variableName;
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}
	
	protected JavaLineCode buildJavaLineCode(String content){
		JavaLineCode javaLineCode = new JavaLineCode(content);
		return javaLineCode;
	}
	
	/**
	 * Add a line code assigmente to created variable.
	 * <pre>
	 *	variablename = javaLineCode.
	 * </pre>
	 * 
	 * @param variableName to be assigned.
	 * @param javaLineCode to convert code to assign.
	 */
	public void addLineCodeAssigmentVariable(String variableName, JavaLineCode javaLineCode){
		String content = variableName + " = " + javaLineCode.convert();
		this.addJavaLineCode(this.buildJavaLineCode(content, javaLineCode));
	}
	
	/**
	 * Add a line code assigment to a variable typed.
	 * <pre>
	 *	VariableType variableName = javaLineCode;
	 * </pre>
	 * @param variableType to be assigned.
	 * @param variableName to be assigned.
	 * @param javaLineCode to convert code to assign.
	 */
	public void addLineCodeAssigmentTypedVariable(String variableType, String variableName, JavaLineCode javaLineCode){
		String content = PackageHelper.getClassSimpleName(variableType) + " " + variableName + " = " + javaLineCode.convert();
		
		this.addJavaLineCode(this.buildJavaLineCode(content, variableType, javaLineCode));
	}
	
	protected JavaLineCode buildJavaLineCode(String content, JavaLineCode otherJavaLineCode){
		JavaLineCode javaLineCode = this.buildJavaLineCode(content);
		javaLineCode.addImportsType(otherJavaLineCode);
		return javaLineCode;
	}
	
	protected JavaLineCode buildJavaLineCode(String content, String importType, JavaLineCode otherJavaLineCode){
		JavaLineCode javaLineCode = this.buildJavaLineCode(content, otherJavaLineCode);
		javaLineCode.addImport(importType);
		return javaLineCode;
	}
	
	/**
	 * Create a line code with a call to a method in this object. Parameters list must be basic java 
	 * types, because they aren't imported.
	 * 
	 * <pre>
	 *	this.methodName(parametersList);
	 * </pre>
	 * @param methodName
	 * @param parametersNames
	 * @return
	 */
	public JavaLineCode getLineCodeCalledThisMethod(String methodName, List<String> parametersNames){
		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
		String content = "this." + methodName + "(" + parametersPlain + ")";
		return this.buildJavaLineCode(content);
	}

	/**
	 * Add a line code with a call to a method. Parameters list must be basic java types, because
	 * they aren't imported.
	 *	<pre>
	 *		methodName(parametersNames)
	 *	</pre> 
	 * @param methodName to be called.
	 * @param parametersNames to add in call.
	 * @return 
	 */
	public JavaLineCode getLineCodeCalledMethod(String methodName, List<String> parametersNames){
		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
		String content = methodName + "(" + parametersPlain + ")";
		return this.buildJavaLineCode(content);
	}
	
	/**
	 * Add a line code with a call to a static method. Class type's method is imported, but parameters
	 * list must be basic java types, because they aren't imported.
	 *	<pre>
	 *	StaticType.methodName(parametersNames)
	 *	</pre>
	 * @param canonicalStaticTypeName type to be imported.
	 * @param methodName to call in type.
	 * @param parametersNames to be passed in the static method called.
	 * @return a java line code with a call to a static method.
	 */
	public JavaLineCode getLineCodeCalledStaticMethod(String canonicalStaticTypeName, String methodName, List<String> parametersNames){
		String simpleStaticTypeName = PackageHelper.getClassSimpleName(canonicalStaticTypeName);
		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
		String content = simpleStaticTypeName + "." + methodName + "(" + parametersPlain + ")";
		JavaLineCode javaLineCode = this.buildJavaLineCode(content);
		javaLineCode.addImport(canonicalStaticTypeName);
		return javaLineCode;
	}
	
	/**
	 * Add a line code with a variable assigment calling to a method in this object.
	 *  
	 * @param assigmentType to be assigned.
	 * @param variableAssignedName to be assigned.
	 * @param javaLineCode to convert calling method.
	 */
	public void addLineCodeMethodAssigment(String assigmentType, String variableAssignedName, JavaLineCode javaLineCode){
		String content = PackageHelper.getClassSimpleName(assigmentType) + " " + variableAssignedName + " = ";
		content += javaLineCode.convert();
		this.addJavaLineCode(this.buildJavaLineCode(content, assigmentType, javaLineCode));
	}
	
	/**
	 *	Add a line code with a return sentence.
	 *	<pre>
	 *		return variableName;
	 *	</pre>
	 *
	 * @param variableName to be returned.
	 */
	public void addLineCodeReturnVariable(String variableName){
		String content = "return " + variableName + ";";
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}
	
	public void addLineCodeReturnNull(){
		String content = "return null;";
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}
	
	/**
	 *	Add a line code with a return sentence to a variable in this object.
	 *	<pre>
	 *		return this.variableName;
	 *	</pre>
	 *
	 * @param variableName in this object to be returned.
	 */
	public void addLineCodeReturnThisVariable(String variableName){
		this.addLineCodeReturnVariable("this." + variableName);
	}
	
	/**
	 * Add a line code with a return casted sentence.
	 * <pre>
	 *	return (Type) variableName;
	 * </pre>
	 * 
	 * @param typeCast to return object.
	 * @param variableName
	 */
	public void addLineCodeReturnVariableTypeCasted(String typeCast, String variableName){
		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
		String content = "return (" + simpleCastType + ")" + variableName;
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}
	
	/**
	 * Add a line code with a return collection casted sentence.
	 * <pre>
	 *	return (CollectionType&lt;Type&gt;) variableName;
	 * </pre>
	 * 
	 * @param collectionClass
	 * @param typeCast
	 * @param variableName
	 */
	public void addLineCodeReturnVariableCollectionTypeCasted(Class<? extends Collection<?>> collectionClass, String typeCast, String variableName){
		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionClass.getCanonicalName());
		String content = "return (" + collectionSimpleCastType + "<" + simpleCastType + ">)" + variableName;
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}
	
	public void addLineCodeCollectionVariableAssigment(Class<? extends Collection<?>> collectionClass, String typeCast, String variableName, JavaLineCode lineCodeAssigment){
		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionClass.getCanonicalName());
		String content = collectionSimpleCastType + "<" + simpleCastType + "> " + variableName + " = " + lineCodeAssigment.convert();
		this.addJavaLineCode(this.buildJavaLineCode(content, collectionClass.getCanonicalName(), lineCodeAssigment));
	}
	
	/**
	 *	Add a line code with a return {@link List} casted sentence.
	 * <pre>
	 *	return (List&lt;Type&gt;) variableName;	
	 * </pre>
	 * @param typeCast to assign type generic list.
	 * @param variableName to be returned.
	 */
	@SuppressWarnings("unchecked")
	public void addLineCodeReturnVariableListTypeCasted(String typeCast, String variableName){
		this.addLineCodeReturnVariableCollectionTypeCasted((Class<? extends Collection<?>>) List.class, typeCast, variableName);
	}
	
	public void addLineCodeCommented(String comment){
		String content = "/** " + comment + ". */";
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}

	public int quantityLineCodes(){
		return this.getLinesCode().size();
	}
	
	public void replaceBlockCode(JavaBlockCode javaBlockCode){
		this.getLinesCode().clear();
		for(JavaLineCode jlc: javaBlockCode.getLinesCode()){			
			this.addJavaLineCode(jlc);
		}
	}

	/**
	 * Add a casted line code returning another java line code.
	 *	<pre>
	 *	return (Collection<Type>) javaLineCode;
	 *	</pre>
	 * @param collectionType to cast returning.
	 * @param typeClass to add to cast generic collection.
	 * @param javaLineCode to be returned.
	 */
	public void addLineCodeReturnVariableCollectionTypeCasted(Class<? extends Collection<?>> collectionType, String typeClass, JavaLineCode javaLineCode) {
		String simpleCastType = PackageHelper.getClassSimpleName(typeClass);
		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionType.getCanonicalName());
		String content = "return (" + collectionSimpleCastType + "<" + simpleCastType + ">)" + javaLineCode.convert();
		List<String> importTypes = new ArrayList<String>();
		importTypes.add(collectionType.getCanonicalName());
		importTypes.add(typeClass);
		javaLineCode.addImportsTypes(importTypes);
		this.addJavaLineCode(this.buildJavaLineCode(content, javaLineCode));		
	}

	/**
	 * Add a line code calling a method with parameters. Method must be in inherit this object, and
	 * parameters must be basic parameters.
	 * <pre>
	 * 	methodName(parameters...);
	 * </pre>
	 * 
	 * @param methodName to be called.
	 * @param parameters to add in method call.
	 */
	public void addLineCodeCallMethodWithParameters(String methodName, List<String> parameters) {
		String content = methodName + "(" + StringHelper.convertToPlainString(parameters.toArray(), ",") + ")";
		this.addJavaLineCode(this.buildJavaLineCode(content));
	}

	/**
	 * Add a line code with an if condition, and a java block code to the true condition.
	 *	<pre>
	 *	if(condition){
	 *		ifBlockCode
	 *	}
	 *	</pre>
	 * @param condition to add in the if sentence.
	 * @param ifJavaBlockCode to add in the true condition.
	 */
	public void addLineCodeIf(String condition, JavaBlockCode ifJavaBlockCode) {
		String content = "if(" + condition + ") {";
		content += "\n\t\t" + ifJavaBlockCode.convert();
		content += "\n\t\t}";
		this.addJavaLineCode(this.buildJavaLineCode(content));
		this.getImportsType().addAll(ifJavaBlockCode.getImportsType());
	}
	
	protected void addJavaBlockCode(JavaBlockCode javaBlockCode){
		for(JavaLineCode jlc: javaBlockCode.getLinesCode()){
			this.addJavaLineCode(jlc);
		}
	}
	
	/**
	 * Add a line code with an if condition, and a java block code to the true and if condition.
	 *	<pre>
	 *	if(condition){
	 *		ifBlockCode
	 *	} else {
	 *		elseBlockCode
	 *	}
	 *	</pre>
	 * @param condition to add in the if sentence.
	 * @param ifJavaBlockCode to add in the true condition.
	 * @param elseJavaBlockCode to add in the else condition.
	 */
	public void addLineCodeIfElse(String condition, JavaBlockCode ifJavaBlockCode, JavaBlockCode elseJavaBlockCode) {
		String content = "if(" + condition + ") {\n";
		content += "\t\t" + ifJavaBlockCode.convert();
		content += "\n\t\t} else {\n";
		content += "\t\t" + elseJavaBlockCode.convert();
		content += "\n\t\t}\n";
		this.addJavaLineCode(this.buildJavaLineCode(content));
		this.getImportsType().addAll(ifJavaBlockCode.getImportsType());
		this.getImportsType().addAll(elseJavaBlockCode.getImportsType());
	}

	public void throwNewException(String canonicalExceptionType, String exceptionMessage) {
		String simpleExceptionType = PackageHelper.getClassSimpleName(canonicalExceptionType);
		String content = "throw new " + simpleExceptionType + "( \"" + exceptionMessage + "\")";
		JavaLineCode exceptionLineCode = this.buildJavaLineCode(content);
		exceptionLineCode.addImport(canonicalExceptionType);
		this.addJavaLineCode(exceptionLineCode);
	}
}
