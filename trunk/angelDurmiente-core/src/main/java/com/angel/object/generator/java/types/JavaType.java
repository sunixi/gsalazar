/**
 * 
 */
package com.angel.object.generator.java.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;
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

	public JavaType(){
		super();
		this.setMethods(new ArrayList<TypeMethod>());
		this.setInterfaces(new ArrayList<JavaInterface>());
		this.setComment(new JavaTypeComment());
		this.setGlobalImports(new ArrayList<String>());
		this.setProperties(new ArrayList<JavaProperty>());
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
	public void addTypeMethod(
			String methodName, 
			List<JavaParameter> javaParameters,
			JavaParameter returnParameter,
			String contentMethod,
			boolean isImplemented,
			Visibility visibility
		){
		TypeMethod typeMethod = new TypeMethod(methodName);
		typeMethod.setContentMethod(contentMethod);
		typeMethod.setParameters(javaParameters);
		typeMethod.setReturnType(returnParameter);
		typeMethod.setVisibility(visibility);
		typeMethod.setImplemented(isImplemented);
		typeMethod.setOwnerType(this);
		this.getMethods().add(typeMethod);
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
		for(TypeMethod tm: this.getMethods()){
			for(String tmi: tm.getImports()){
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
			methodsSigns.add(tm.getMethod());
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

	public void addTypeMethodPublic(String methodName, 
			List<JavaParameter> javaParameters,
			JavaParameter returnParameter,
			String contentMethod,
			boolean isImplemented){
		this.addTypeMethod(methodName, javaParameters, returnParameter, contentMethod, isImplemented, Visibility.PUBLIC);
	}
	
	public void addTypeMethodProtected(String methodName, 
			List<JavaParameter> javaParameters,
			JavaParameter returnParameter,
			String contentMethod,
			boolean isImplemented){
		this.addTypeMethod(methodName, javaParameters, returnParameter, contentMethod, isImplemented, Visibility.PROTECTED);
	}
	
	public void addTypeMethodProtectedImplemented(String methodName, 
			List<JavaParameter> javaParameters,
			JavaParameter returnParameter,
			String contentMethod){
		this.addTypeMethod(methodName, javaParameters, returnParameter, contentMethod, true, Visibility.PROTECTED);
	}
	
	public void addTypeMethodProtectedVoidImplemented(String methodName, List<JavaParameter> javaParameters, String contentMethod){
		this.addTypeMethod(methodName, javaParameters, null, contentMethod, true, Visibility.PROTECTED);
	}
	
	public void addTypeMethodProtectedImplementedWithoutParameters(String methodName, JavaParameter returnParameter, String contentMethod){
		this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnParameter, contentMethod, true, Visibility.PROTECTED);
	}
	
	public void addTypeMethodPublicVoid(String methodName, 
			List<JavaParameter> javaParameters,
			String contentMethod,
			boolean isImplemented){
		this.addTypeMethod(methodName, javaParameters, null, contentMethod, isImplemented, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicWithoutParameters(String methodName, 
			JavaParameter returnType,
			String contentMethod,
			boolean isImplemented){
		this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnType, contentMethod, isImplemented, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicWithoutParametersImplemented(String methodName, 
			JavaParameter returnType,
			String contentMethod){
		this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnType, contentMethod, true, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicVoidWithoutParametersImplemented(String methodName, String contentMethod){
		this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), null, contentMethod, true, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicWithoutParametersNotImplemented(String methodName, JavaParameter returnType){
		this.addTypeMethod(methodName, new ArrayList<JavaParameter>(), returnType, StringHelper.EMPTY_STRING, false, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicImplemented(String methodName, List<JavaParameter> javaParameters, JavaParameter returnType, String contentMethod){
		this.addTypeMethod(methodName, javaParameters, returnType, contentMethod, true, Visibility.PUBLIC);
	}
	
	public void addTypeMethodPublicNotImplemented(String methodName, List<JavaParameter> javaParameters, JavaParameter returnType){
		this.addTypeMethod(methodName, javaParameters, returnType, StringHelper.EMPTY_STRING, false, Visibility.PUBLIC);
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
		String getterContent = "return this." + javaProperty.getParameterName() + ";";
		JavaParameter returnJavaParameter = new JavaParameter(javaProperty.getParameterName(), javaProperty.getParameterName());
		this.addTypeMethodPublicWithoutParametersImplemented(getMethodName, returnJavaParameter, getterContent);
	}
	
	public void createSetterFor(JavaProperty javaProperty){
		String setMethodName = ReflectionHelper.getSetMethodName(javaProperty.getParameterName());
		String setterContent = "this." + javaProperty.getParameterName() + " = " + javaProperty.getParameterName() + ";";		
		JavaParameter javaParameter = new JavaParameter(javaProperty.getParameterName(), javaProperty.getParameterName());
		List<JavaParameter> javaParameters = new ArrayList<JavaParameter>();
		javaParameters.add(javaParameter);
		this.addTypeMethodPublicVoid(setMethodName, javaParameters, setterContent, true);
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
}
