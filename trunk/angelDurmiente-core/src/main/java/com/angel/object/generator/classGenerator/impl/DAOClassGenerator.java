/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.persistence.Column;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.dao.generic.interfaces.GenericDAO;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.java.types.JavaInterface;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorAnnotationMethodBuilder;
import com.angel.object.generator.methodBuilder.impl.ColumnAnnotationMethodBuilder;


/**
 * @author	Guillermo Daniel Salazar.
 * @since	28/Noviembre/2009.
 *
 */
public class DAOClassGenerator extends ClassGenerator {

	/**
	 * 
	 */
	public DAOClassGenerator(String basePackage) {
		super(basePackage);
		this.addMethodBuilderStrategies(Accesor.class, new AccesorAnnotationMethodBuilder());
		this.addMethodBuilderStrategies(Column.class, new ColumnAnnotationMethodBuilder());
	}
	
	public DAOClassGenerator(String basePackage, ClassGenerator interfaceClassGenerator) {
		this(basePackage);
		this.setInterfaceClassGenerator(interfaceClassGenerator);
	}

	protected void buildInterfacesClasses(){
		//Do Nothing.
	}
	
	@Override
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		subjavaType.setTypeName(GenericDAO.class.getCanonicalName());
		subjavaType.setLeftGeneric(super.getDomainObjectCanonicalName());
		subjavaType.setRightGeneric(ObjectId.class.getCanonicalName());
		return subjavaType;
	}
	
	protected String buildClassName(Class<?> domainClass){
		return domainClass.getSimpleName() + "DAO";
	}


	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass) {
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				String methodName = methodBuilder.buildMethodName(domainClass, f);
				List<JavaParameter> javaParameters = methodBuilder.buildJavaParameters(domainClass, f);
				JavaParameter returnParameter = methodBuilder.buildReturnParameter(domainClass, f);
				super.getJavaType().addTypeMethodPublicNotImplemented(methodName, javaParameters, returnParameter);
			}
		}
	}


	@Override
	protected void updateCurrentJavaType(ClassesGenerator generator, Class<?> domainClass) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected JavaType buildJavaType() {
		return new JavaInterface();
	}
}