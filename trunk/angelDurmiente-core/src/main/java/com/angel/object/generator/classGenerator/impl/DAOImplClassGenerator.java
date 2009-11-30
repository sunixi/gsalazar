/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaConstructor;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.java.types.JavaClass;
import com.angel.object.generator.java.types.JavaInterface;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorDAOImplAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class DAOImplClassGenerator extends ClassGenerator {

	public DAOImplClassGenerator(String basePackage) {
		super(basePackage);
		this.addMethodBuilderStrategies(Accesor.class, new AccesorDAOImplAnnotationMethodBuilder());
	}
	
	public DAOImplClassGenerator(String basePackage, ClassGenerator interfaceClassGenerator) {
		this(basePackage);
		this.setInterfaceClassGenerator(interfaceClassGenerator);
	}

	@Override
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		subjavaType.setTypeName(GenericSpringHibernateDAO.class.getCanonicalName());
		subjavaType.setLeftGeneric(super.getDomainObjectCanonicalName());
		subjavaType.setRightGeneric(ObjectId.class.getCanonicalName());
		return subjavaType;
	}

	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass) {
		this.buildJavaTypeConstructor(generator, domainClass);
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);

				String methodName = methodBuilder.buildMethodName(domainClass, f);
				List<JavaParameter> javaParameters = methodBuilder.buildJavaParameters(domainClass, f);
				JavaParameter returnParameter = methodBuilder.buildReturnParameter(domainClass, f);
				JavaBlockCode blockCode = methodBuilder.buildMethodContent(domainClass, f);

				JavaBlockCode blockCodeCreated = super.getJavaType().addTypeMethodPublicImplemented(methodName, javaParameters, returnParameter);
				blockCodeCreated.replaceBlockCode(blockCode);
			}
		}
	}
	
	protected void buildJavaTypeConstructor(ClassesGenerator generator, Class<?> domainClass){
		JavaConstructor javaConstructor = super.createJavaConstructor();
		String content = "super(" + domainClass.getSimpleName() + ".class, " + ObjectId.class.getSimpleName() + ".class);";
		javaConstructor.setContent(content);
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "SpringHibernateDAO";
	}

	@Override
	protected void updateCurrentJavaType(ClassesGenerator generator, Class<?> domainClass) {
		// TODO 
	}

	@Override
	protected JavaType buildJavaType() {
		return new JavaClass();
	}

	@Override
	protected void buildInterfacesClasses() {
		JavaInterface javaInterface = super.createJavaInterface();
		javaInterface.setTypeName(super.getDomainObjectSimpleName() + "DAO");
		
	}	
}
