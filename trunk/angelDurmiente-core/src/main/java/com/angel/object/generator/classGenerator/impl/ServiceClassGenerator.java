/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Column;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.Generator;
import com.angel.object.generator.annotations.Accesor;
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
	protected void generateContentClass(Generator generator, Class<?> domainClass, JavaFile javaFile) {
		String contentMethod = "return (" + domainClass.getSimpleName() + "DAO" + ") super.getGenericDAO();";
		this.addJavaMethod(javaFile, domainClass, contentMethod);

		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			Annotation annotation = null;
			if(f.isAnnotationPresent(Accesor.class)){
				annotation = f.getAnnotation(Accesor.class);
			} else {
				if(f.isAnnotationPresent(Column.class)) {
					annotation = f.getAnnotation(Column.class);	
				}
			}
			MethodBuilder methodBuilder = super.getMethodBuilderFor(annotation);
			JavaMethod javaMethod = methodBuilder.buildJavaMethod(domainClass, f, annotation);
			javaFile.addJavaMethod(javaMethod);
		}
		/*
			public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario) {
				return this.getUsuarioDAO().buscarUnicoONuloPorNombreUsuario(nombreUsuario);
			}
		 */
	}

	
}
