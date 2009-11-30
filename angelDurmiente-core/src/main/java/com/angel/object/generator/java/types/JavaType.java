/**
 * 
 */
package com.angel.object.generator.java.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.java.JavaAnnotation;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaConstructor;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.java.JavaProperty;
import com.angel.object.generator.java.JavaTypeComment;
import com.angel.object.generator.java.TypeMethod;
import com.angel.object.generator.java.enums.Visibility;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class JavaType implements CodeConvertible {

	private static final String IMPORT_PREFIX = "import ";
	private static final String JAVA_END_OF_LINE = ";";

	/**
	 * Identify full package and type name. It includes full package names
	 * separated with ".".
	 */
	private String typeName;

	private String rightGeneric;
	
	private JavaType subJavaType;

	private String leftGeneric;
	
	private List<TypeMethod> methods;
	
	private List<JavaInterface> interfaces;
	
	private JavaTypeComment comment;
	
	private Class<?> domainObject;
	
	private List<String> globalImports;
	
	private List<JavaProperty> properties;
	
	private List<JavaAnnotation> annotations;

	public JavaType(){
		super();
		this.setMethods(new ArrayList<TypeMethod>());
		this.setInterfaces(new ArrayList<JavaInterface>());
		this.setComment(new JavaTypeComment());
		this.setGlobalImports(new ArrayList<String>());
		this.setProperties(new ArrayList<JavaProperty>());
		this.setAnnotations(new ArrayList<JavaAnnotation>());
	}
	
	public JavaType(String typeName){
		this();
		this.setTypeName(typeName);
	}
	
	public abstract JavaConstructor createJavaConstructor();

	/**
	 * Define if it is an interfaces or a class implementation.
	 * 
	 * @return true if it is a concrete class implementation. Otherwise it returns false.
	 */
	public abstract boolean isAnImplementationType();
	
	/**
	 * Get java type identifier name. It returns class, interfaces, annotations.
	 *  
	 * @return java type identifier name.
	 */
	public abstract String getJavaTypeIdentifierName();
	
	/**
	 * Generate java type content.
	 *  
	 * @return content for this java type.
	 */
	protected abstract String convertContentJavaType();
	
	/**
	 * Add imports for this java type.
	 * 
	 * @param imports to add imports.
	 */
	public abstract void addTypesImports(List<String> imports);
	
	/**
	 * 
	 * @param methodName
	 * @param javaParameters
	 * @param returnParameter
	 * @param contentMethod
	 * @param visibility
	 */
	public JavaBlockCode addTypeMethod(
			String methodName, 
			List<JavaParameter> javaParameters,
			JavaParameter returnParameter,
			boolean isImplemented,
			Visibility visibility
		){
		TypeMethod typeMethod = new TypeMethod(methodName);
		typeMethod.setParameters(javaParameters);
		typeMethod.setReturnType(returnParameter);
		typeMethod.setVisibility(visibility);
		typeMethod.setImplemented(isImplemented);
		typeMethod.setOwnerType(this);
		this.getMethods().add(typeMethod);
		return typeMethod.getContent();
	}

	/**
	 * @return the subJavaType
	 */
	public JavaType getSubJavaType() {
		return subJavaType;
	}

	/**
	 * @param subJavaType the subJavaType to set
	 */
	public void setSubJavaType(JavaType subJavaType) {
		this.subJavaType = subJavaType;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the rightGeneric
	 */
	public String getRightGeneric() {
		return rightGeneric;
	}

	/**
	 * @param rightGeneric the rightGeneric to set
	 */
	public void setRightGeneric(String rightGeneric) {
		this.rightGeneric = rightGeneric;
	}

	/**
	 * @return the leftGeneric
	 */
	public String getLeftGeneric() {
		return leftGeneric;
	}

	/**
	 * @param leftGeneric the leftGeneric to set
	 */
	public void setLeftGeneric(String leftGeneric) {
		this.leftGeneric = leftGeneric;
	}

	/**
	 * @return the methods
	 */
	public List<TypeMethod> getMethods() {
		return methods;
	}

	/**
	 * @param methods the methods to set
	 */
	public void setMethods(List<TypeMethod> methods) {
		this.methods = methods;
	}

	/**
	 * Get simple type name, without packages names.
	 * 
	 * @return simple type name.
	 */
	public String getSimpleName() {
		return PackageHelper.getClassSimpleName(this.getTypeName());
	}
	
	public String getLeftGenericSimpleName(){
		return PackageHelper.getClassSimpleName(this.getLeftGeneric());
	}
	
	public String getRightGenericSimpleName(){
		return PackageHelper.getClassSimpleName(this.getRightGeneric());
	}
	
	/**
	 * Get all java types imports for this java type.
	 * 
	 * @return all java imports for this types.
	 */
	public List<String> getTypesImports(){
		List<String> typesImports = new ArrayList<String>();
		this.addImport(typesImports, this.getDomainObject().getCanonicalName());
		if(this.hasLeftGeneric()){
			this.addImport(typesImports, this.getLeftGeneric());
		}
		if(this.hasRigthGeneric()){
			this.addImport(typesImports, this.getRightGeneric());
		}
		if(this.hasSubJavaType()){
			this.addImport(typesImports, this.getSubJavaType().getTypeName());
			if(this.getSubJavaType().hasLeftGeneric()){
				this.addImport(typesImports, this.getSubJavaType().getLeftGeneric());
			}
			if(this.getSubJavaType().hasRigthGeneric()){
				this.addImport(typesImports, this.getSubJavaType().getRightGeneric());
			}
		}
		for(Importable tm: this.getMethods()){
			for(String tmi: tm.getImportsType()){
				this.addImport(typesImports, tmi);
			}
		}
		for(Importable i: this.getProperties()){
			for(String it: i.getImportsType()){
				this.addImport(typesImports, it);
			}
		}
		for(String gi: this.getGlobalImports()){
			this.addImport(typesImports, gi);
		}
		this.addTypesImports(typesImports);
		return typesImports;
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
	
	protected String getTypesImportPlain(){
		return StringHelper.convertToPlainString(this.getTypesImports().toArray(), "\n");
	}
	
	public String getBasePackage(){
		int lastIndex = this.getTypeName().lastIndexOf(".");
		String basePackage = "";
		if(lastIndex > 0){
			basePackage = this.getTypeName().substring(0, lastIndex);
		}
		return basePackage;
	}
	
	public String convert(){
		String javaTypeConverted = "package " + this.getBasePackage() + ";\n\n";
		javaTypeConverted += this.getTypesImportPlain() + "\n\n\n"; 
		javaTypeConverted += this.getComment().convert() + "\n";
		javaTypeConverted += this.convertAnnotations() + "\n";
		javaTypeConverted += "public " + this.getJavaTypeIdentifierName() + " " + this.getJavaTypeSign();
		javaTypeConverted += this.hasSubJavaType() ? " extends " + this.getSubJavaTypeSign() : "";
		javaTypeConverted += this.hasInterfaces() ? " implements " + this.getInterfacesTypeSign() : "";
		javaTypeConverted += " {\n\n";
		javaTypeConverted += this.convertJavaProperties() + "\n\n";
		javaTypeConverted += this.convertContentJavaType();
		javaTypeConverted += this.hasMethods() ? this.getMethodsSign() : "\n";
		javaTypeConverted += "}\n";
		return javaTypeConverted;
	}
	
	protected String convertAnnotations() {
		String codeConverter = "";
		for(CodeConvertible ja: this.getAnnotations()){
			codeConverter += ja.convert();
		}
		return codeConverter;
	}

	protected String convertJavaProperties(){
		String codeConverted = "";
		for(CodeConvertible cc: this.getProperties()){
			codeConverted += cc.convert();
		}
		return codeConverted;
	}
	
	public boolean hasProperties(){
		return this.getProperties().size() > 0;
	}
	
	public boolean hasMethods(){
		return this.getMethods().size() > 0;
	}
	
	public boolean hasInterfaces() {
		return this.getInterfaces().size() > 0;
	}
	
	protected String getMethodsSign(){
		List<String> methodsSigns = new ArrayList<String>();
		for(TypeMethod tm: this.getMethods()){
			methodsSigns.add(tm.convert());
		}
		return StringHelper.convertToPlainString(methodsSigns.toArray(), "\n");
	}
	
	protected String getInterfacesTypeSign(){
		List<String> interfacesSigns = new ArrayList<String>();
		for(JavaInterface ji: this.getInterfaces()){
			interfacesSigns.add(ji.getJavaTypeSign());
		}
		return StringHelper.convertToPlainString(interfacesSigns.toArray(), ",");
	}
	
	protected String getSubJavaTypeSign(){
		return this.getSubJavaType().getJavaTypeSign();
	}

	/**
	 * Get java type sign with its name, and its generic.
	 * 
	 * @return java type sign.
	 */
	public String getJavaTypeSign(){
		String leftClassSign = this.hasLeftGeneric() ? "<" + this.getLeftGenericSimpleName() : "";
		String rigthClassSign = this.hasRigthGeneric() ? this.getRightGenericSimpleName(): "";
		String subClassSign = leftClassSign + (this.hasRigthGeneric() ? "," + rigthClassSign + ">": (this.hasLeftGeneric() ? ">" : ""));
		return this.getSimpleName() + subClassSign;
	}
	
	public boolean hasGenerics() {
		return StringHelper.isNotEmpty(this.getLeftGeneric());
	}
	
	public boolean hasLeftGeneric() {
		return StringHelper.isNotEmpty(this.getLeftGeneric());
	}
	
	public boolean hasRigthGeneric() {
		return StringHelper.isNotEmpty(this.getRightGeneric());
	}

	public boolean hasSubJavaType(){
		return this.getSubJavaType() != null;
	}

	/**
	 * @return the interfaces
	 */
	public List<JavaInterface> getInterfaces() {
		return interfaces;
	}

	/**
	 * @param interfaces the interfaces to set
	 */
	public void setInterfaces(List<JavaInterface> interfaces) {
		this.interfaces = interfaces;
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
	
	public void setComment(String comment){
		this.getComment().setComment(comment);
	}
	
	public void addTagComment(String tag, String comment){
		this.getComment().addTagComment(tag, comment);
	}
	
	public void addAuthorTagComment(String comment){
		this.getComment().addTagComment("author", comment);
	}

	public String getName() {
		return this.getSimpleName() + ".java";
	}

	public JavaBlockCode addTypeMethodPublic(String methodName, 
			List<JavaParameter> javaParameters,
			JavaParameter returnParameter,
			boolean isImplemented){
		JavaBlockCode javaBlockCode = this.addTypeMethod(methodName, javaParameters, returnParameter, isImplemented, Visibility.PUBLIC);
		if(isImplemented){
			return javaBlockCode;
		}
		return null;
	}
	
	public JavaBlockCode addTypeMethodProtected(String methodName, List<JavaParameter> javaParameters, 
			JavaParameter returnParameter, boolean isImplemented){
		JavaBlockCode javaBlockCode = this.addTypeMethod(methodName, javaParameters, returnParameter, isImplemented, Visibility.PROTECTED);
		if(isImplemented){
			return javaBlockCode;
		}
		return null;
	}
	
	public JavaBlockCode addTypeMethodProtectedImplemented(String methodName, 
			List<JavaParameter> javaParameters, JavaParameter returnParameter){
		return this.addTypeMethod(methodName, javaParameters, returnParameter, true, Visibility.PROTECTED);
	}
	
	public JavaBlockCode addTypeMethodProtectedVoidImplemented(String methodName, List<JavaParameter> javaParameters){
		return this.addTypeMethod(methodName, javaParameters, null, true, Visibility.PROTECTED);
	}
	
	public JavaBlockCode addTypeMethodProtectedImplementedWithoutParameters(String methodName, JavaParameter returnParameter){
		return this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnParameter,true, Visibility.PROTECTED);
	}
	
	public JavaBlockCode addTypeMethodPublicVoid(String methodName, 
			List<JavaParameter> javaParameters, boolean isImplemented){
		JavaBlockCode javaBlockCode = this.addTypeMethod(methodName, javaParameters, null, isImplemented, Visibility.PUBLIC);
		if(isImplemented){
			return javaBlockCode;
		}
		return null;
	}
	
	public JavaBlockCode addTypeMethodPublicWithoutParameters(String methodName, JavaParameter returnType, boolean isImplemented){
		JavaBlockCode javaBlockCode = this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnType, isImplemented, Visibility.PUBLIC);
		if(isImplemented){
			return javaBlockCode;
		}
		return null;
	}
	
	public JavaBlockCode addTypeMethodPublicWithoutParametersImplemented(String methodName, JavaParameter returnType){
		return this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnType, true, Visibility.PUBLIC);
	}
	
	public JavaBlockCode addTypeMethodPublicVoidWithoutParametersImplemented(String methodName){
		return this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), null, true, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicWithoutParametersNotImplemented(String methodName, JavaParameter returnType){
		this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnType, false, Visibility.PUBLIC);
	}
	
	public JavaBlockCode addTypeMethodPublicImplemented(String methodName, List<JavaParameter> javaParameters, JavaParameter returnType){
		return this.addTypeMethod(methodName, javaParameters, returnType, true, Visibility.PUBLIC);
	}
	
	public void  addTypeMethodPublicNotImplemented(String methodName, List<JavaParameter> javaParameters, JavaParameter returnType){
		this.addTypeMethod(methodName, javaParameters, returnType, false, Visibility.PUBLIC);
	}
	
	public JavaParameter buildJavaParameter(String parameterName, String parameterType){
		return new JavaParameter(parameterName, parameterType);
	}
	
	public JavaParameter buildReturnJavaParameter(String parameterType){
		return new JavaParameter(StringHelper.EMPTY_STRING, parameterType);
	}

	/**
	 * @return the domainObject
	 */
	public Class<?> getDomainObject() {
		return domainObject;
	}

	/**
	 * @param domainObject the domainObject to set
	 */
	public void setDomainObject(Class<?> domainObject) {
		this.domainObject = domainObject;
	}	
	
	public String getDomainObjectSimpleName(){
		return this.getDomainObject().getSimpleName();
	}

	public String getDomainObjectCanonicalName() {
		return this.getDomainObject().getCanonicalName();
	}

	public void addInterface(String interfaceType){
		this.getInterfaces().add(new JavaInterface(interfaceType));
	}
	
	public void addInterfaces(List<String> interfacesTypes){
		for(String i: interfacesTypes){
			this.addInterface(i);
		}
	}
	
	public void addInterface(JavaInterface javaInterface){
		this.getInterfaces().add(javaInterface);
	}
	
	public void addJavaInterfaces(List<JavaInterface> javaInterfaces){
		for(JavaInterface ji: javaInterfaces){
			this.addInterface(ji);
		}
	}
	
	public void addJavaProperty(JavaProperty javaProperty){
		this.getProperties().add(javaProperty);
	}
	
	/**
	 * Create a java property with its getter and setter method.
	 * 
	 * @param propertyName to be created.
	 * @param propertyType to be created.
	 * @return a java property with its getter and setter method.
	 */
	public JavaProperty createJavaProperty(String propertyName, String propertyType){
		JavaProperty javaProperty = this.createJavaProperty();
		javaProperty.setParameterName(propertyName);
		javaProperty.setParameterType(propertyType);
		this.createGetterFor(javaProperty);
		this.createSetterFor(javaProperty);
		return javaProperty;
	}
	
	public void createGetterFor(JavaProperty javaProperty){
		String getMethodName = ReflectionHelper.getGetMethodName(javaProperty.getParameterName());
		JavaParameter returnJavaParameter = new JavaParameter(javaProperty.getParameterName(), javaProperty.getParameterType());
		JavaBlockCode javaBlockCode = this.addTypeMethodPublicWithoutParametersImplemented(getMethodName, returnJavaParameter);
		javaBlockCode.addLineCodeReturnThisVariable(javaProperty.getParameterName());
	}
	
	public void createSetterFor(JavaProperty javaProperty){
		String setMethodName = ReflectionHelper.getSetMethodName(javaProperty.getParameterName());
		JavaParameter javaParameter = new JavaParameter(javaProperty.getParameterName(), javaProperty.getParameterType());
		List<JavaParameter> javaParameters = new ArrayList<JavaParameter>();
		javaParameters.add(javaParameter);
		JavaBlockCode javaBlockCode = this.addTypeMethodPublicVoid(setMethodName, javaParameters, true);
		javaBlockCode.addLineCodeThisAssigment(javaProperty.getParameterName());
	}
	
	public JavaProperty createJavaProperty(){
		JavaProperty javaProperty = new JavaProperty();
		this.addJavaProperty(javaProperty);
		return javaProperty;
	}

	public JavaInterface createJavaInterface() {
		JavaInterface javaInterface = new JavaInterface();
		this.addInterface(javaInterface);
		return javaInterface;
	}

	public void addImport(String classNameImport) {
		this.getGlobalImports().add(classNameImport);		
	}

	/**
	 * @return the globalImports
	 */
	protected List<String> getGlobalImports() {
		return globalImports;
	}

	/**
	 * @param globalImports the globalImports to set
	 */
	protected void setGlobalImports(List<String> globalImports) {
		this.globalImports = globalImports;
	}

	/**
	 * @return the properties
	 */
	public List<JavaProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<JavaProperty> properties) {
		this.properties = properties;
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
	
	public void addAnnotation(String canonicalName){
		this.getAnnotations().add(new JavaAnnotation(canonicalName));
	}
	
	public void addAnnotation(String canonicalName, List<JavaProperty> properties){
		this.getAnnotations().add(new JavaAnnotation(canonicalName, properties));
	}
}
