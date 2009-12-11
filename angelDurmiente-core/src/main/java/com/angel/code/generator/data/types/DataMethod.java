/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.types.impl.DataCommentImpl;
import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class DataMethod implements CodeConvertible, Importable{

	private DataType ownerType;
	private String methodName;
	private List<DataParameter> parameters;
	private DataParameter returnType;
	private CodeBlock content;
	private boolean implemented = true;
	private TypeModifier typeModifier;
	private Visibility visibility;
	private DataComment comment;
	private List<DataAnnotation> annotations;
	private List<DataException> exceptions;

	public abstract <T extends DataAnnotation> T createAnnotation(String canonicalName);

	public abstract <T extends DataException> T createException(String canonicalName);

	public abstract <T extends DataParameter> T createParameter(String name);
	
	public abstract <T extends DataParameter> T createReturnParameter(String canonicalName);
	
	public abstract <T extends CodeBlock> T createCodeBlock();
	
	public DataMethod(){
		super();
		this.setContent(new CodeBlock());
		this.setComment(new DataCommentImpl());
		this.setAnnotations(new ArrayList<DataAnnotation>());
		this.setParameters(new ArrayList<DataParameter>());
		this.setExceptions(new ArrayList<DataException>());
		this.setVisibility(Visibility.PUBLIC);
		this.setTypeModifier(TypeModifier.NONE);
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

	protected String convertReturnType(){
		String convertReturnType = "";
		if(this.isReturnACollection()){
			convertReturnType = this.getReturnType().getSimpleType() + "<" + this.getSimpleOwnerType() + ">";
		} else {
			if(this.getReturnParameter().isArray()){
				convertReturnType = this.getReturnParameter().getSimpleType() + "[] " + this.getReturnParameter().getName();
			} else {
				convertReturnType = this.getReturnType().getSimpleType();
			}
		}
		return convertReturnType;
	}
	
	protected String getSimpleOwnerType(){
		return this.getOwnerType().getDomainObjectSimpleName();
	}
	
	public boolean isReturnACollection(){
		if(this.hasReturnType()){
			Class<?> returnTypeClass = ReflectionHelper.getClassFrom(this.getReturnType().getCanonicalType());
			if(returnTypeClass != null){
				return Collection.class.isAssignableFrom(returnTypeClass);
			}
		}
		return false;
	}
	
	public boolean hasParameters(){
		return this.getParameters().size() > 0;
	}
	
	public boolean hasReturnType(){
		return this.getReturnType() != null;
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
		String convertedCode = "";
		convertedCode += this.convertCodeDataComment();
		convertedCode += this.convertCodeDataAnnotations();
		convertedCode += this.convertCodeDataMethodSign();
		convertedCode += this.convertCodeBeginDataMethod();
		convertedCode += this.convertCodeCodeBlock();
		convertedCode += this.convertCodeEndDataMethod();
		return convertedCode;
	}
	
	protected String convertCodeEndDataMethod() {
		return this.isImplemented() ? "\t\t}\n\n" : ";\n\n";
	}

	protected String convertCodeCodeBlock() {
		if(this.isImplemented()){
			return this.getContent().convertCode();
		}
		return StringHelper.EMPTY_STRING;
	}

	protected String convertCodeBeginDataMethod() {
		return this.isImplemented() ? " {\n": "";
	}

	protected String convertCodeDataParameters() {
		String convertedCode = "(";
		if(this.hasParameters()){
			for(DataParameter dataParameter: this.getParameters()){
				if(this.getParameters().indexOf(dataParameter) == 0){
					convertedCode += dataParameter.convertCode();
				} else {
					convertedCode += ", " + dataParameter.convertCode();
				}
			}
		}
		return convertedCode + ")";
	}

	protected String convertCodeDataMethodSign() {
		String convertedCode = this.hasAnnotations() ? "\n": "";
		convertedCode += "\t" + this.convertVisibility() + " " + this.convertTypeModifier();
		convertedCode += this.hasReturnType() ? " " + this.convertReturnType() + " ": " void ";
		convertedCode += this.getMethodName();
		convertedCode += this.convertCodeDataParameters();
		convertedCode += this.convertCodeDataExceptions();
		return convertedCode;
	}
	
	public boolean hasAnnotations(){
		return this.getAnnotations().size() > 0;
	}

	protected String convertCodeDataExceptions() {
		String convertedCode = "";
		if(this.hasExceptions()){
			convertedCode += " throws ";
			for(DataException dataException: this.getExceptions()){
				if(this.getExceptions().indexOf(dataException) == 0){
					convertedCode += dataException.convertCode();
				} else {
					convertedCode += ", " + dataException.convertCode();
				}
			}
		}
		return convertedCode;
	}

	public boolean hasExceptions(){
		return this.getExceptions().size() > 0;
	}

	protected String convertCodeDataAnnotations() {
		String codeConverted = "";
		for(DataAnnotation dataAnnotation: this.getAnnotations()){
			codeConverted += dataAnnotation.convertCode();
		}
		return codeConverted;
	}

	protected String convertCodeDataComment() {
		return this.getComment().convertCode();
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

	/**
	 * @return the ownerType
	 */
	public DataType getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(DataType ownerType) {
		this.ownerType = ownerType;
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
	public List<DataParameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<DataParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the returnType
	 */
	public DataParameter getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(DataParameter returnType) {
		this.returnType = returnType;
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

	/**
	 * @return the annotations
	 */
	public List<DataAnnotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(List<DataAnnotation> annotations) {
		this.annotations = annotations;
	}

	public String getName() {
		return this.getMethodName();
	}

	public DataParameter getReturnParameter() {
		return this.getReturnType();
	}

	public String getSign() {
		return this.convertCodeDataMethodSign();
	}

	public boolean isVoid() {
		return !this.hasReturnType();
	}

	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		this.addImportsTypeCodeBlock(importsType);
		this.addImportsTypeDataAnnotations(importsType);
		this.addImportsTypeDataParametrs(importsType);
		this.addImportsTypeDataReturnParameter(importsType);
		this.addImportsTypeDataExceptions(importsType);
		return importsType;
	}

	protected void addImportsTypeDataExceptions(List<String> importsType) {
		for(DataException dataException : this.getExceptions()){
			importsType.addAll(dataException.getImportsType());
		}
	}

	protected void addImportsTypeCodeBlock(List<String> importsType) {
		if(this.isImplemented()){
			importsType.addAll(this.getContent().getImportsType());
		}
	}
	
	protected void addImportsTypeDataAnnotations(List<String> importsType) {
		for(DataAnnotation dataAnnotation: this.getAnnotations()){
			importsType.addAll(dataAnnotation.getImportsType());
		}
	}
	
	protected void addImportsTypeDataParametrs(List<String> importsType) {
		for(DataParameter dataParameter: this.getParameters()){
			importsType.addAll(dataParameter.getImportsType());
		}
	}
	
	protected void addImportsTypeDataReturnParameter(List<String> importsType) {
		if(this.hasReturnType()){
			importsType.addAll(this.getReturnParameter().getImportsType());
		}
	}

	public List<DataException> getExceptions() {
		return this.exceptions;
	}

	/**
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(List<DataException> exceptions) {
		this.exceptions = exceptions;
	}

	public boolean hasName(String name) {
		return StringHelper.isNotEmpty(this.getName()) && this.getName().equalsIgnoreCase(name);
	}

	public boolean matchSign(String name, List<String> canonicalParameters) {
		return this.hasName(name) && this.hasParametersTypes(canonicalParameters);
	}
	
	public boolean hasParametersTypes(List<String> canonicalParameters) {
		boolean hasParametersTypes = canonicalParameters.size() == this.getParameters().size();
		if(hasParametersTypes){
			int i = 0;
			for(DataParameter dataParameter : this.getParameters()){
				if(!dataParameter.isCanonicalType(canonicalParameters.get(i))){
					return false;
				}
				i++;
			}
		}
		return hasParametersTypes;
	}

	public List<String> getParametersCanonicalTypes() {
		List<String> parametersCanonicalTypes = new ArrayList<String>();
		for(DataParameter dataParameter : this.getParameters()){
			parametersCanonicalTypes.add(dataParameter.getCanonicalType());
		}
		return parametersCanonicalTypes;
	}

	public boolean hasParameters(int totalParameters) {
		return this.getParameters().size() == totalParameters;
	}

	public void addAnnotation(DataAnnotation dataAnnotation){
		if(this.hasAnnotation(dataAnnotation)){
			throw new CodeGeneratorException("Data method name [" + this.getName() + "] has a data annotation with name [" + dataAnnotation.getName() + "].");
		}
		this.getAnnotations().add(dataAnnotation);
	}

	public boolean hasAnnotation(DataAnnotation dataAnnotation) {
		return this.getAnnotation(dataAnnotation.getName()) != null;
	}

	public DataAnnotation getAnnotation(String name){
		for (DataAnnotation da: this.getAnnotations()) {
			if (da.hasName(name)) {
				return da;
			}
		}
		return null;
	}

	public void addParameter(DataParameter dataParameter){
		if(this.hasParameter(dataParameter)){
			throw new CodeGeneratorException("Data method name [" + this.getName() + "] has a data parameter with name [" + dataParameter.getName() + "].");
		}
		this.getParameters().add(dataParameter);
	}

	
	public void addParameters(DataParameter ...dataParameters){
		if(dataParameters != null && dataParameters.length > 0){
			for(DataParameter dataParameter: dataParameters){
				this.addParameter(dataParameter);
			}
		}
	}

	public void addParameters(List<DataParameter> dataParameters){
		this.addParameters(dataParameters.toArray(new DataParameter[dataParameters.size()]));
	}

	public boolean hasParameter(DataParameter dataParameter) {
		return this.getParameter(dataParameter.getName()) != null;
	}

	public DataParameter getParameter(String name){
		for (DataParameter dp: this.getParameters()) {
			if (dp.hasName(name)) {
				return dp;
			}
		}
		return null;
	}

	public void addException(DataException dataException){
		if(this.hasException(dataException)){
			throw new CodeGeneratorException("Data method name [" + this.getName() + "] has a data exception with name [" + dataException.getCanonicalType() + "].");
		}
		this.getExceptions().add(dataException);
	}

	public boolean hasException(DataException dataException) {
		return this.getException(dataException.getCanonicalType()) != null;
	}

	public DataException getException(String canonicalName){
		for (DataException de: this.getExceptions()) {
			if (de.hasCanonicalType(canonicalName)) {
				return de;
			}
		}
		return null;
	}
}
