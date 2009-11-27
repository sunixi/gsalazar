/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.aspectj.asm.IProgramElement.Modifiers;

import com.angel.architecture.services.impl.GenericServiceImpl;
import com.angel.architecture.services.interfaces.GenericService;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaFile;
import com.angel.object.generator.java.JavaMethod;
import com.angel.object.generator.methodBuilder.MethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ServiceClassGenerator extends ClassGenerator {

	public ServiceClassGenerator(String basePackage) {
		super(basePackage);
	}

	public ServiceClassGenerator() {
		super();
		
	}

	@Override
	public Class<?> getSubClassForClassGenerator(){
		return GenericServiceImpl.class;
	}
	
	protected void getInterfacesClasses(List<Class<?>> interfaces){
		interfaces.add(GenericService.class);
	}
	
	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass, JavaFile javaFile) {
		this.buildGenericDAOGetter(domainClass, javaFile);

		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				JavaMethod javaMethod = methodBuilder.buildJavaMethod(domainClass, f);
				javaFile.addJavaMethod(javaMethod);
				String methodContent = "return this." + 
					ReflectionHelper.getGetMethodName(domainClass.getSimpleName() + "DAO") + "()." +
					javaMethod.getMethodName() + "(" +
					javaMethod.getPlainJavaParametersNames() + ");";
				javaMethod.setContentMethod(methodContent);
				super.buildJavaMethodContent(javaMethod);
			}
		}
		
		/*
			public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario);

			public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario) {
				return this.getUsuarioDAO().buscarUnicoONuloPorNombreUsuario(nombreUsuario);
			}
		 */
	}

	protected void buildGenericDAOGetter(Class<?> domainClass, JavaFile javaFile) {
		String contentMethod = "return (" + domainClass.getSimpleName() + "DAO" + ") super.getGenericDAO();";
		String domainClassDAO = domainClass.getSimpleName() + "DAO";
		String methodName = ReflectionHelper.getGetMethodName(domainClassDAO);
		javaFile.addJavaMethod(methodName, "", ReflectionHelper.getClassFrom(domainClassDAO), contentMethod);
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "ServiceImpl";
	}

	
}
