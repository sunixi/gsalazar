/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.builders.method.impl.AccesorJavaAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaInterfaceDataType;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.dao.generic.interfaces.GenericDAO;


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
		this.addMethodBuilderStrategies(Accesor.class, new AccesorJavaAnnotationMethodBuilder());
	}

	@Override
	public DataType buildSubClassForClassGenerator(DataType subDataType){
		JavaInterfaceDataType javaInterfaceDataType = (JavaInterfaceDataType) subDataType;
		javaInterfaceDataType.setCanonicalName(GenericDAO.class.getCanonicalName());
		javaInterfaceDataType.setLeftGeneric(super.getDomainObjectCanonicalName());
		javaInterfaceDataType.setRightGeneric(ObjectId.class.getCanonicalName());
		return javaInterfaceDataType;
	}
	
	protected String buildClassName(Class<?> domainClass){
		return domainClass.getSimpleName() + "DAO";
	}


	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				methodBuilder.buildDataMethod(generator, this.getDataType(), domainClass, f);
			}
		}
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DataType buildDataType() {
		return new JavaInterfaceDataType();
	}
}
