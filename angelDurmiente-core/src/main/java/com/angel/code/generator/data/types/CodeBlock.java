/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.CodeLine;
import com.angel.code.generator.data.types.codeLine.CommentCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ReturnableCodeLine;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class CodeBlock implements CodeConvertible, Importable{
	
	private List<CodeLine> codeLines;
	
	public CodeBlock(){
		super();
		this.setCodeLines(new ArrayList<CodeLine>());
	}

	@SuppressWarnings("unchecked")
	public <T extends AssignableCodeLine> T createAssignableCodeLine(String variableName, ExecutableReturnCodeLine executableReturnCodeLine, String variableCanonicalName){
		return (T) this.addCodeLine(new AssignableCodeLine(variableName, executableReturnCodeLine, variableCanonicalName));
	}

	@SuppressWarnings("unchecked")
	public <T extends ExecutableCodeLine> T createExecutableCodeLine(String methodName){
		return (T) this.addCodeLine(new ExecutableCodeLine(methodName));
	}

	@SuppressWarnings("unchecked")
	public <T extends ExecutableReturnCodeLine> T createExecutableReturnCodeLine(String methodName, String returnCanonicalName){
		return (T) this.addCodeLine(new ExecutableReturnCodeLine(methodName, returnCanonicalName));
	}

	@SuppressWarnings("unchecked")
	public <T extends ReturnableCodeLine> T createReturnableCodeLine(String returnCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine){
		return (T) this.addCodeLine(new ReturnableCodeLine(returnCanonicalName, executableReturnCodeLine));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CommentCodeLine> T createCommentCodeLine(){
		return (T) this.addCodeLine(new CommentCodeLine());
	}

	@SuppressWarnings("unchecked")
	public <T extends CommentCodeLine> T createCommentCodeLine(String comment){
		return (T) this.addCodeLine(new CommentCodeLine(comment));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CommentCodeLine> T createCommentCodeLineTODO(String todoComment){
		CommentCodeLine commentCodeLine = new CommentCodeLine();
		commentCodeLine.setTODOComment(todoComment);
		return (T) this.addCodeLine(commentCodeLine);
	}
	
	/**
	 * Add a line code to this code block.
	 * 
	 * @param codeLine to add.
	 * @return code line added.
	 */
	public CodeLine addCodeLine(CodeLine codeLine){
		/*
		CodeLine lastCodeLine = this.getLastCodeLine();
		TODO ver con los catch if(lastCodeLine != null && lastCodeLine.isReturn()){
			throw new CodeGeneratorException("Cannot add a code line after a return code line [" + lastCodeLine.convertCode() + "].");
		}*/
		this.getCodeLines().add(codeLine);
		return codeLine;
	}

	/**
	 * Get last code line added.
	 * 
	 * @return last code line added. Or if code block hasn't got code lines added, it returns null.
	 */
	protected CodeLine getLastCodeLine(){
		return this.hasCodeLines() ? this.getCodeLines().get(this.getCodeLines().size() - 1) : null;
	}

	public boolean hasCodeLines(){
		return this.getCodeLines().size() > 0;
	}
	
	public String convertCode() {
		String codeConverted = "";
		for(CodeLine codeLine: this.getCodeLines()){
			if(codeLine.hasEndOfLine()){
				codeConverted += codeLine.convertCode() + ";\n";
			} else {
				codeConverted += codeLine.convertCode() + "\n";				
			}
		}
		return codeConverted;
	}

	/**
	 * @return the codeLines
	 */
	public List<CodeLine> getCodeLines() {
		return codeLines;
	}

	/**
	 * @param codeLines the codeLines to set
	 */
	public void setCodeLines(List<CodeLine> codeLines) {
		this.codeLines = codeLines;
	}

	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		for(CodeLine codeLine : this.getCodeLines()){
			importsType.addAll(codeLine.getImportsType());
		}
		return importsType;
	}

//	/**
//	 *	Add a line code with a null assigment to a class type instance.
//	 * <pre>
//	 *	Type variableName = null;
//	 * </pre>
//	 * 
//	 * @param classType to null assigment.
//	 * @param variableName to be assigned.
//	 */
//	public void addLineCodeNullAssigment(String classType, String variableName){
//		String content = PackageHelper.getClassSimpleName(classType) + " " + variableName + " = null;";
//		this.addCodeLine(new JavaCodeLine(content, classType));
//	}
//	
//	/**
//	 * Add a line code with an assigment variable as a setter.
//	 * <pre>
//	 *	this.variableName = variableName;
//	 * </pre>
//	 * @param variableName to be assigned.
//	 */
//	public void addLineCodeThisAssigment(String variableName){
//		String content = "this." + variableName + " = " + variableName;
//		this.addCodeLine(this.buildJavaLineCode(content));
//	}
//	
//	protected JavaCodeLine buildJavaLineCode(String content){
//		JavaCodeLine javaLineCode = new JavaCodeLine(content);
//		return javaLineCode;
//	}
//	
//	/**
//	 * Add a line code assigment to created variable.
//	 * <pre>
//	 *	variableName = javaLineCode.
//	 * </pre>
//	 * 
//	 * @param variableName to be assigned.
//	 * @param javaLineCode to convert code to assign.
//	 */
//	public void addLineCodeAssigmentVariable(String variableName, JavaCodeLine javaLineCode){
//		String content = variableName + " = " + javaLineCode.convertCode();
//		this.addCodeLine(this.buildJavaLineCode(content, javaLineCode));
//	}
//	
//	/**
//	 * Add a line code assigment to a variable typed.
//	 * <pre>
//	 *	VariableType variableName = javaLineCode;
//	 * </pre>
//	 * @param variableType to be assigned.
//	 * @param variableName to be assigned.
//	 * @param javaLineCode to convert code to assign.
//	 */
//	public void addLineCodeAssigmentTypedVariable(String variableType, String variableName, JavaCodeLine javaLineCode){
//		String content = PackageHelper.getClassSimpleName(variableType) + " " + variableName + " = " + javaLineCode.convertCode();
//		
//		this.addCodeLine(this.buildJavaLineCode(content, variableType, javaLineCode));
//	}
//	
//	protected JavaCodeLine buildJavaLineCode(String content, JavaCodeLine otherJavaLineCode){
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(otherJavaLineCode.getImportsType());
//		return javaLineCode;
//	}
//	
//	protected JavaCodeLine buildJavaLineCode(String content, String importType, JavaCodeLine otherJavaLineCode){
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content, otherJavaLineCode);
//		javaLineCode.addImportsType(importType);
//		return javaLineCode;
//	}
//	
//	/**
//	 * Create a line code with a call to a method in this object. Parameters list must be basic java 
//	 * types, because they aren't imported.
//	 * 
//	 * <pre>
//	 *	this.methodName(parametersList);
//	 * </pre>
//	 * @param methodName
//	 * @param parametersNames
//	 * @return
//	 */
//	public JavaCodeLine getLineCodeCalledThisMethod(String methodName, List<String> parametersNames){
//		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
//		String content = "this." + methodName + "(" + parametersPlain + ")";
//		return this.buildJavaLineCode(content);
//	}
//
//	/**
//	 * Add a line code with a call to a method. Parameters list must be basic java types, because
//	 * they aren't imported.
//	 *	<pre>
//	 *		methodName(parametersNames)
//	 *	</pre> 
//	 * @param methodName to be called.
//	 * @param parametersNames to add in call.
//	 * @return 
//	 */
//	public JavaCodeLine getLineCodeCalledMethod(String methodName, List<String> parametersNames){
//		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
//		String content = methodName + "(" + parametersPlain + ")";
//		return this.buildJavaLineCode(content);
//	}
//	
//	/**
//	 * Add a line code with a call to a static method. Class type's method is imported, but parameters
//	 * list must be basic java types, because they aren't imported.
//	 *	<pre>
//	 *	StaticType.methodName(parametersNames)
//	 *	</pre>
//	 * @param canonicalStaticTypeName type to be imported.
//	 * @param methodName to call in type.
//	 * @param parametersNames to be passed in the static method called.
//	 * @return a java line code with a call to a static method.
//	 */
//	public JavaCodeLine getLineCodeCalledStaticMethod(String canonicalStaticTypeName, String methodName, List<String> parametersNames){
//		String simpleStaticTypeName = PackageHelper.getClassSimpleName(canonicalStaticTypeName);
//		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
//		String content = simpleStaticTypeName + "." + methodName + "(" + parametersPlain + ")";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(canonicalStaticTypeName);
//		return javaLineCode;
//	}
//	
//	public JavaCodeLine getLineCodeCalledStaticMethod(String canonicalStaticTypeName, String methodName, Object[] parametersValues){
//		String simpleStaticTypeName = PackageHelper.getClassSimpleName(canonicalStaticTypeName);
//		String parametersPlain = StringHelper.convertToPlainString(parametersValues, ",");
//		String content = simpleStaticTypeName + "." + methodName + "(" + parametersPlain + ")";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(canonicalStaticTypeName);
//		return javaLineCode;
//	}
//	
//	/**
//	 * Add a line code with a variable assigment calling to a method in this object.
//	 *  
//	 * @param assigmentType to be assigned.
//	 * @param variableAssignedName to be assigned.
//	 * @param javaLineCode to convert calling method.
//	 */
//	public void addLineCodeMethodAssigment(String assigmentType, String variableAssignedName, JavaCodeLine javaLineCode){
//		String content = PackageHelper.getClassSimpleName(assigmentType) + " " + variableAssignedName + " = ";
//		content += javaLineCode.convertCode();
//		this.addCodeLine(this.buildJavaLineCode(content, assigmentType, javaLineCode));
//	}
//	
//	/**
//	 *	Add a line code with a return sentence.
//	 *	<pre>
//	 *		return variableName;
//	 *	</pre>
//	 *
//	 * @param variableName to be returned.
//	 */
//	public void addLineCodeReturnVariable(String variableName){
//		String content = "return " + variableName + ";";
//		this.addCodeLine(this.buildJavaLineCode(content));
//	}
//	
//	public void addLineCodeReturnNull(){
//		String content = "return null;";
//		this.addCodeLine(this.buildJavaLineCode(content));
//	}
//	
//	/**
//	 *	Add a line code with a return sentence to a variable in this object.
//	 *	<pre>
//	 *		return this.variableName;
//	 *	</pre>
//	 *
//	 * @param variableName in this object to be returned.
//	 */
//	public void addLineCodeReturnThisVariable(String variableName){
//		this.addLineCodeReturnVariable("this." + variableName);
//	}
//	
//	/**
//	 * Add a line code with a return casted sentence.
//	 * <pre>
//	 *	return (Type) variableName;
//	 * </pre>
//	 * 
//	 * @param typeCast to return object.
//	 * @param variableName
//	 */
//	public void addLineCodeReturnVariableTypeCasted(String typeCast, String variableName){
//		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
//		String content = "return (" + simpleCastType + ")" + variableName;
//		this.addCodeLine(this.buildJavaLineCode(content));
//	}
//	
//	/**
//	 * Add a line code with a return collection casted sentence.
//	 * <pre>
//	 *	return (CollectionType&lt;Type&gt;) variableName;
//	 * </pre>
//	 * 
//	 * @param collectionClass
//	 * @param typeCast
//	 * @param variableName
//	 */
//	public void addLineCodeReturnVariableCollectionTypeCasted(Class<? extends Collection<?>> collectionClass, String typeCast, String variableName){
//		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
//		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionClass.getCanonicalName());
//		String content = "return (" + collectionSimpleCastType + "<" + simpleCastType + ">)" + variableName;
//		this.addCodeLine(this.buildJavaLineCode(content));
//	}
//	
//	public void addLineCodeCollectionVariableAssigment(Class<? extends Collection<?>> collectionClass, String typeCast, String variableName, JavaCodeLine lineCodeAssigment){
//		String simpleCastType = PackageHelper.getClassSimpleName(typeCast);
//		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionClass.getCanonicalName());
//		String content = collectionSimpleCastType + "<" + simpleCastType + "> " + variableName + " = " + lineCodeAssigment.convertCode();
//		this.addCodeLine(this.buildJavaLineCode(content, collectionClass.getCanonicalName(), lineCodeAssigment));
//	}
//	
//	/**
//	 *	Add a line code with a return {@link List} casted sentence.
//	 * <pre>
//	 *	return (List&lt;Type&gt;) variableName;	
//	 * </pre>
//	 * @param typeCast to assign type generic list.
//	 * @param variableName to be returned.
//	 */
//	@SuppressWarnings("unchecked")
//	public void addLineCodeReturnVariableListTypeCasted(String typeCast, String variableName){
//		this.addLineCodeReturnVariableCollectionTypeCasted((Class<? extends Collection<?>>) List.class, typeCast, variableName);
//	}
//	
//	public void addLineCodeCommented(String comment){
//		String content = "/** " + comment + ". */";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.removeEndTag();
//		this.addCodeLine(javaLineCode);
//	}
//
//	public int quantityLineCodes(){
//		return this.getCodeLines().size();
//	}
//	
//	public void replaceBlockCode(CodeBlock codeBlock){
//		this.getCodeLines().clear();
//		for(CodeLine jlc: codeBlock.getLines()){			
//			this.addCodeLine(jlc);
//		}
//	}
//
//	/**
//	 * Add a casted line code returning another java line code.
//	 *	<pre>
//	 *	return (Collection<Type>) javaLineCode;
//	 *	</pre>
//	 * @param collectionType to cast returning.
//	 * @param typeClass to add to cast generic collection.
//	 * @param javaLineCode to be returned.
//	 */
//	public void addLineCodeReturnVariableCollectionTypeCasted(Class<? extends Collection<?>> collectionType, String typeClass, JavaCodeLine javaLineCode) {
//		String simpleCastType = PackageHelper.getClassSimpleName(typeClass);
//		String collectionSimpleCastType = PackageHelper.getClassSimpleName(collectionType.getCanonicalName());
//		String content = "return (" + collectionSimpleCastType + "<" + simpleCastType + ">)" + javaLineCode.convertCode();
//		List<String> importTypes = new ArrayList<String>();
//		importTypes.add(collectionType.getCanonicalName());
//		importTypes.add(typeClass);
//		javaLineCode.addImportsType(importTypes);
//		this.addCodeLine(this.buildJavaLineCode(content, javaLineCode));		
//	}
//
//	/**
//	 * Add a line code calling a method with parameters. Method must be in inherit this object, and
//	 * parameters must be basic parameters.
//	 * <pre>
//	 * 	methodName(parameters...);
//	 * </pre>
//	 * 
//	 * @param methodName to be called.
//	 * @param parameters to add in method call.
//	 */
//	public void addLineCodeCallMethodWithParameters(String methodName, List<String> parameters) {
//		String content = methodName + "(" + StringHelper.convertToPlainString(parameters.toArray(), ",") + ")";
//		this.addCodeLine(this.buildJavaLineCode(content));
//	}
//
//	/**
//	 * Add a line code with an if condition, and a java block code to the true condition.
//	 *	<pre>
//	 *	if(condition){
//	 *		ifBlockCode
//	 *	}
//	 *	</pre>
//	 * @param condition to add in the if sentence.
//	 * @param ifJavaBlockCode to add in the true condition.
//	 */
//	public void addLineCodeIf(String condition, CodeBlock ifJavaBlockCode) {
//		String content = "if(" + condition + ") {";
//		content += "\n\t\t" + ifJavaBlockCode.convertCode();
//		content += "\n\t\t}";
//		this.addCodeLine(this.buildJavaLineCode(content));
//		this.getImportsType().addAll(ifJavaBlockCode.getImportsType());
//	}
//	
//	protected void addJavaBlockCode(CodeBlock codeBlock){
//		for(CodeLine jlc: codeBlock.getLines()){
//			this.addCodeLine(jlc);
//		}
//	}
//	
//	/**
//	 * Add a line code with an if condition, and a java block code to the true and if condition.
//	 *	<pre>
//	 *	if(condition){
//	 *		ifBlockCode
//	 *	} else {
//	 *		elseBlockCode
//	 *	}
//	 *	</pre>
//	 * @param condition to add in the if sentence.
//	 * @param ifJavaBlockCode to add in the true condition.
//	 * @param elseJavaBlockCode to add in the else condition.
//	 */
//	public void addLineCodeIfElse(String condition, CodeBlock ifJavaBlockCode, CodeBlock elseJavaBlockCode) {
//		String content = "if(" + condition + ") {\n";
//		content += "\t\t" + ifJavaBlockCode.convertCode();
//		content += "\n\t\t} else {\n";
//		content += "\t\t" + elseJavaBlockCode.convertCode();
//		content += "\n\t\t}\n";
//		this.addCodeLine(this.buildJavaLineCode(content));
//		this.getImportsType().addAll(ifJavaBlockCode.getImportsType());
//		this.getImportsType().addAll(elseJavaBlockCode.getImportsType());
//	}
//
//	public void throwNewException(String canonicalExceptionType, String exceptionMessage) {
//		String simpleExceptionType = PackageHelper.getClassSimpleName(canonicalExceptionType);
//		String content = "throw new " + simpleExceptionType + "( \"" + exceptionMessage + "\")";
//		JavaCodeLine exceptionLineCode = this.buildJavaLineCode(content);
//		exceptionLineCode.addImportsType(canonicalExceptionType);
//		this.addCodeLine(exceptionLineCode);
//	}
//	
//	public JavaCodeLine getLineCodethrowNewException(String canonicalExceptionType, String exceptionMessage) {
//		String simpleExceptionType = PackageHelper.getClassSimpleName(canonicalExceptionType);
//		String content = "throw new " + simpleExceptionType + "( \"" + exceptionMessage + "\")";
//		JavaCodeLine exceptionLineCode = this.buildJavaLineCode(content);
//		exceptionLineCode.addImportsType(canonicalExceptionType);
//		return exceptionLineCode;
//	}
//
//	/**
//	 * Add a line code with a return sentence calling to a java line code.
//	 *	<pre>
//	 *		return javaLineCode
//	 *	</pre>
//	 * @param newImportFileAnnotationProcessorRunner
//	 */
//	public void addLineCodeReturn(JavaCodeLine newImportFileAnnotationProcessorRunner) {
//		String content = "return " + newImportFileAnnotationProcessorRunner.convertCode();
//		JavaCodeLine javaCodeLine = this.buildJavaLineCode(content);
//		javaCodeLine.getImportsType().addAll(newImportFileAnnotationProcessorRunner.getImportsType());
//		this.addCodeLine(javaCodeLine);
//	}
//
//	/**
//	 * Add a line code with a create instance without constructor parameters.
//	 * 
//	 *	<pre>
//	 *	Type variableName = new Type()
//	 *	</pre>
//	 * 
//	 * @param variableName to assigns the new object instance.
//	 * @param canonicalTypeName to create its instance.
//	 * @return a java line code with a variable assigned a new instance object.
//	 */
//	public JavaCodeLine getLineCodeCreateObject(String variableName, String canonicalTypeName) {
//		String simpleTypeName = PackageHelper.getClassSimpleName(canonicalTypeName);
//		String content = simpleTypeName + " " + variableName + " = new " + simpleTypeName + "();";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(canonicalTypeName);
//		return javaLineCode;
//	}
//	
//	public JavaCodeLine getLineCodeCreateInstanceObject(String canonicalTypeName) {
//		String simpleTypeName = PackageHelper.getClassSimpleName(canonicalTypeName);
//		String content = " new " + simpleTypeName + "()";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(canonicalTypeName);
//		javaLineCode.removeEndTag();
//		return javaLineCode;
//	}
//
//	public JavaCodeLine getLineCodeNewInstanceObject(
//			String newInstanceObjectType, List<String> parametersNames) {
//		String simpleTypeName = PackageHelper.getClassSimpleName(newInstanceObjectType);
//		String content = " new " + simpleTypeName + "(" + StringHelper.convertToPlainString(parametersNames.toArray(), ", ") + ");";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(newInstanceObjectType);
//		return javaLineCode;
//	}
//
//	public void addLineCodeTryCatch(JavaCodeLine tryJavaLineCode,
//			String canonicalExceptionType, JavaCodeLine catchJavaLineCode) {
//		String exceptionSimpleName = PackageHelper.getClassSimpleName(canonicalExceptionType);
//		String content = "try {\n\t\t";
//		content += "\t\t\t" + tryJavaLineCode.convertCode() + ";";
//		content += "\t\t} catch ( " + exceptionSimpleName + " e) {\n";
//		content += "\t" + catchJavaLineCode.convertCode() + ";";
//		content += "\t\t}";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		//javaLineCode.removeEndTag();
//		javaLineCode.addImportType(canonicalExceptionType);
//		javaLineCode.addImportsType(tryJavaLineCode.getImportsType());
//		javaLineCode.addImportsType(catchJavaLineCode.getImportsType());
//		this.addCodeLine(javaLineCode);
//	}
//
//	/**
//	 * Add a line code returning a calling to a static method with parameters. Parameters list must be 
//	 * basic java types, because they aren't imported.
//	 *	<pre>
//	 *		return StaticType.methodName(parametersList)
//	 *	</pre> 
//	 * 
//	 * @param canonicalStaticTypeName
//	 * @param methodName
//	 * @param parametersNames
//	 * @return
//	 */
//	public JavaCodeLine getLineCodeReturnCalledStaticMethod(String canonicalStaticTypeName, String methodName, List<String> parametersNames) {
//		String simpleStaticTypeName = PackageHelper.getClassSimpleName(canonicalStaticTypeName);
//		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
//		String content = "return " + simpleStaticTypeName + "." + methodName + "(" + parametersPlain + ")";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(canonicalStaticTypeName);
//		return javaLineCode;
//	}
//
//	/**
//	 * Add a line code calling to a variable method with parameters. Parameters list must be basic java types, because they
//	 * aren't imported.
//	 *	<pre>
//	 *		variableName.methodName(parametersNames)
//	 *	</pre>
//	 * @param variableName
//	 * @param methodName
//	 * @param parametersNames
//	 * @return
//	 */
//	public JavaCodeLine getLineCodeCalledVariableMethod(String variableName, String methodName, List<String> parametersNames) {
//		String parametersPlain = StringHelper.convertToPlainString(parametersNames.toArray(), ",");
//		String content = variableName + "." + methodName + "(" + parametersPlain + ")";
//		return this.buildJavaLineCode(content);
//	}
//
//	public void addLineCodeVariableSetValue(String variableName, String setterMethodName, JavaCodeLine javaLineCode) {
//		javaLineCode.removeEndTag();
//		String content = variableName + "." + setterMethodName;
//		content += "(" + javaLineCode.convertCode() + ")";
//		JavaCodeLine lineCode = this.buildJavaLineCode(content);
//		this.addCodeLine(lineCode);
//		this.getImportsType().addAll(javaLineCode.getImportsType());		
//	}
//
//	public JavaCodeLine getLineCodeNull() {
//		return this.buildJavaLineCode("null");
//	}
//
//	public void addLineCodeWithFor(String forCondition, JavaCodeLine forContent) {
//		String content = "for(" + forCondition + "){\n";
//		content += forContent.convertCode();
//		content += "}\n";
//		JavaCodeLine lineCode = this.buildJavaLineCode(content);
//		this.addCodeLine(lineCode);
//		this.getImportsType().addAll(forContent.getImportsType());
//	}
//
//	/**
//	 * Add a line code with a creation collection type with generics.
//	 * 
//	 *	<pre>
//	 *		new CollectionType<TypeGeneric>
//	 *	</pre>
//	 * @param canonicalName
//	 * @param canonicalName2
//	 * @return
//	 */
//	public JavaCodeLine getLineCodeCreateCollectionTyped(String canonicalCollectionType, String canonicalTypeGeneric) {
//		List<String> importsTypes = new ArrayList<String>();
//		importsTypes.add(canonicalCollectionType);
//		importsTypes.add(canonicalTypeGeneric);
//		String simpleCollectionType = PackageHelper.getClassSimpleName(canonicalCollectionType);
//		String simpleGenericType = PackageHelper.getClassSimpleName(canonicalTypeGeneric);
//		String content = "new " + simpleCollectionType + "<" + simpleGenericType + ">()";
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(importsTypes);
//		return javaLineCode;
//	}
//
//	/**
//	 * Add a line code with a assigment to a collection generic typed.
//	 *	<pre>
//	 *	CollectionType<Type> variableName = assigmenteLineCode
//	 *	</pre>
//	 * @param collectionCanonicalName
//	 * @param genericCanonicalType
//	 * @param variableName
//	 * @param collectionTypedAssigment
//	 */
//	public void addLineCodeAssigmentCollectionTypedVariable(String collectionCanonicalName, String genericCanonicalType, String variableName,
//			JavaCodeLine collectionTypedAssigment) {
//		String singleCollectionType = PackageHelper.getClassSimpleName(collectionCanonicalName);
//		String singleGenericType = PackageHelper.getClassSimpleName(genericCanonicalType);
//		String content = singleCollectionType + "<" + singleGenericType + "> " + variableName + " = " + collectionTypedAssigment.convertCode();
//		JavaCodeLine javaLineCode = this.buildJavaLineCode(content);
//		javaLineCode.addImportsType(collectionTypedAssigment.getImportsType());
//		this.addCodeLine(javaLineCode);
//	}
//
//	public JavaCodeLine getLineCodeCalledVariableMethodWithTypeParameters(String variableName, String methodName, JavaCodeLine javaLineCode) {
//		String content = variableName + "." + methodName + "(" + javaLineCode.convertCode() + ")";
//		JavaCodeLine resultJavaLineCode = this.buildJavaLineCode(content);
//		resultJavaLineCode.addImportsType(javaLineCode.getImportsType());
//		return resultJavaLineCode;
//	}
//
//	public JavaCodeLine getLineCodeCalledStaticClassMethod(String classCanonicalType) {
//		String content = PackageHelper.getClassSimpleName(classCanonicalType) + ".class";
//		JavaCodeLine resultJavaLineCode = this.buildJavaLineCode(content);
//		resultJavaLineCode.removeEndTag();
//		resultJavaLineCode.addImportsType(classCanonicalType);
//		return resultJavaLineCode;
//	}
//
//	public List<CodeLine> getLines() {
//		return this.getCodeLines();
//	}
//
//	public List<String> getImportsType() {
//		List<String> importsType = new ArrayList<String>();
//		for(CodeLine codeLine : this.getCodeLines()){
//			importsType.addAll(codeLine.getImportsType());
//		}
//		return importsType;
//	}
//
//	public CodeLine createCodeLine() {
//		CodeLine codeLine = new JavaCodeLine();
//		this.addCodeLine(codeLine);
//		return codeLine;
//	}
}
