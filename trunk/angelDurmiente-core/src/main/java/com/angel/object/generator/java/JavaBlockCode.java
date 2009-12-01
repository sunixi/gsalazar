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
	private String lineSeparator;

	public JavaBlockCode(String lineSeparator){
		this();
		this.setLineSeparator(lineSeparator);
	}
	
	public JavaBlockCode(){
		super();
		this.setLineSeparator(JavaLineCode.JAVA_END_OF_LINE);
		this.setLinesCode(new ArrayList<JavaLineCode>());
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

	public void addLineCode(JavaLineCode javaLineCode){
		this.getLinesCode().add(javaLineCode);
	}

	public String convert() {
		String codeConverted = "";
		for(JavaLineCode jlc: this.getLinesCode()){
			codeConverted += jlc.convert();
		}
		return codeConverted;
	}

	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		for(JavaLineCode jlc: this.getLinesCode()){
			importsType.addAll(jlc.getImportsType());
		}
		return importsType;
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
		this.getLinesCode().add(new JavaLineCode(content, classType, this.getLineSeparator()));
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
		this.getLinesCode().add(this.buildJavaLineCode(content));
	}
	
	protected JavaLineCode buildJavaLineCode(String content){
		JavaLineCode javaLineCode = new JavaLineCode(content);
		javaLineCode.setLineSeparator(this.getLineSeparator());
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
		this.getLinesCode().add(this.buildJavaLineCode(content));
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
		this.getLinesCode().add(new JavaLineCode(content, variableType, this.getLineSeparator()));
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
	 * 
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
	 * Add a line code with a variable assigment calling to a method in this object.
	 *  
	 * @param assigmentType to be assigned.
	 * @param variableAssignedName to be assigned.
	 * @param javaLineCode to convert calling method.
	 */
	public void addLineCodeMethodAssigment(String assigmentType, String variableAssignedName, JavaLineCode javaLineCode){
		String content = PackageHelper.getClassSimpleName(assigmentType) + " " + variableAssignedName + " = ";
		content += javaLineCode.convert();
		this.getLinesCode().add(new JavaLineCode(content, assigmentType, this.getLineSeparator()));
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
		this.getLinesCode().add(this.buildJavaLineCode(content));
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
		this.getLinesCode().add(this.buildJavaLineCode(content));
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
		this.getLinesCode().add(this.buildJavaLineCode(content));
	}
	
	public void addLineCodeCollectionVariableAssigment(Class<? extends Collection<?>> collectionClass, String typeCast, String variableName, JavaLineCode lineCodeAssigment){
		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionClass.getCanonicalName());
		String content = collectionSimpleCastType + "<" + simpleCastType + "> " + variableName + " = " + lineCodeAssigment.convert();
		this.getLinesCode().add(new JavaLineCode(content, collectionClass.getCanonicalName(), this.getLineSeparator()));
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
		this.getLinesCode().add(this.buildJavaLineCode(content));
	}

	public int quantityLineCodes(){
		return this.getLinesCode().size();
	}
	
	public void replaceBlockCode(JavaBlockCode javaBlockCode){
		this.getLinesCode().clear();
		this.getLinesCode().addAll(javaBlockCode.getLinesCode());
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
		this.addLineCode(new JavaLineCode(content, importTypes, this.getLineSeparator()));		
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
		this.addLineCode(this.buildJavaLineCode(content));
	}

	/**
	 * @return the lineSeparator
	 */
	public String getLineSeparator() {
		return lineSeparator;
	}

	/**
	 * @param lineSeparator the lineSeparator to set
	 */
	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
}
