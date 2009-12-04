/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.angel.architecture.services.impl.GenericServiceImpl;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.impl.java.ClassDataType;
import com.angel.code.generator.data.impl.java.InterfaceDataType;
import com.angel.code.generator.data.impl.java.JavaType;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.TypeMethod;
import com.angel.object.generator.java.properties.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorServiceImplAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ServiceImplClassGenerator extends ClassGenerator {

	public ServiceImplClassGenerator(String basePackage) {
		super(basePackage);
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorServiceImplAnnotationMethodBuilder());
	}
	
	public ServiceImplClassGenerator(String basePackage, ClassGenerator interfaceClassGenerator) {
		this(basePackage);
		this.setInterfaceClassGenerator(interfaceClassGenerator);
	}

	@Override
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		subjavaType.setTypeName(GenericServiceImpl.class.getCanonicalName());
		return subjavaType;
	}
	
	@Override
	protected void processJavaTypeInterfaces(CodesGenerator generator){
		InterfaceDataType serviceInterface = super.createJavaInterface();
		String canonicalInterfaceType = generator.getImportForClassName(super.getDomainObjectSimpleName() + "Service");
		serviceInterface.setTypeName(canonicalInterfaceType);
	}
	
	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.buildGenericDAOGetter(generator, domainClass);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);

				String methodName = methodBuilder.buildMethodName(domainClass, f);
				List<JavaParameter> javaParameters = methodBuilder.buildJavaParameters(domainClass, f);
				JavaParameter returnParameter = methodBuilder.buildReturnParameter(domainClass, f);
				JavaBlockCode javaBlockCode = methodBuilder.buildMethodContent(domainClass, f);

				TypeMethod typeMethod = super.getJavaType().addTypeMethodPublicImplemented(methodName, javaParameters, returnParameter);
				typeMethod.getContent().replaceBlockCode(javaBlockCode);
			}
		}
	}

	protected void buildGenericDAOGetter(CodesGenerator generator, Class<?> domainClass) {
		String domainClassDAO = domainClass.getSimpleName() + "DAO";

		String methodName = ReflectionHelper.getGetMethodName(domainClassDAO);
		JavaParameter returnParameter = super.getJavaType().buildReturnJavaParameter(domainClassDAO);
		returnParameter.notImportType();
		TypeMethod typeMethod = super.getJavaType()
				.addTypeMethodProtectedImplementedWithoutParameters(methodName, returnParameter);
		JavaBlockCode javaBlockCode = typeMethod.getContent();
		javaBlockCode.addLineCodeReturnVariable("(" + domainClassDAO + ") super.getGenericDAO()");
		String importClassName = generator.getImportForClassName(domainClassDAO);
		super.getJavaType().addImport(importClassName);
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "ServiceImpl";
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// TODO 
	}

	@Override
	protected JavaType buildJavaType() {
		return new ClassDataType();
	}	
}
