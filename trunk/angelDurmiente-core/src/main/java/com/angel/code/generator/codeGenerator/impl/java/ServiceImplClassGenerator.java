/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.angel.architecture.services.impl.GenericServiceImpl;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.builders.method.impl.AccesorServiceImplAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.codeLine.HarcodedCodeLine;
import com.angel.common.helpers.ReflectionHelper;


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
	public DataType buildSubClassForClassGenerator(DataType subDataType){
		subDataType.setCanonicalName(GenericServiceImpl.class.getCanonicalName());
		return subDataType;
	}
	
	@Override
	protected void processJavaTypeInterfaces(CodesGenerator generator){
		String canonicalInterfaceType = generator.getImportForClassName(super.getDomainObjectSimpleName() + "Service");
		super.getDataType().createDataInterface(canonicalInterfaceType);
	}
	
	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.buildGenericDAOGetter(generator, domainClass);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				methodBuilder.buildDataMethod(generator, this.getDataType(), domainClass, f);
			}
		}
	}

	protected void buildGenericDAOGetter(CodesGenerator generator, Class<?> domainClass) {
		String domainClassDAO = domainClass.getSimpleName() + "DAO";
		String importClassName = generator.getImportForClassName(domainClassDAO);

		String methodName = ReflectionHelper.getGetMethodName(domainClassDAO);
		DataMethod typeMethod = super.getDataType().createDataMethod(methodName);
		typeMethod.setMethodName(methodName);
		typeMethod.createReturnParameter(importClassName);
		typeMethod.setVisibility(Visibility.PROTECTED);

		CodeBlock codeBlock = typeMethod.createCodeBlock();
		HarcodedCodeLine harcodedCodeLine = new HarcodedCodeLine();
		harcodedCodeLine.setCode("return (" + domainClassDAO + ") super.getGenericDAO()");
		codeBlock.addCodeLine(harcodedCodeLine);
		super.getDataType().addGlobalImport(importClassName);
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
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}	
}
