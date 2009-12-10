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
import com.angel.code.generator.builders.method.impl.AccesorDAOImplAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataConstructor;
import com.angel.code.generator.data.types.codeLine.HarcodedCodeLine;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;


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
	public DataType buildSubClassForClassGenerator(CodesGenerator generator, DataType subDataType){
		JavaClassDataType javaClassDataType = (JavaClassDataType) subDataType;
		javaClassDataType.setCanonicalName(GenericSpringHibernateDAO.class.getCanonicalName());
		javaClassDataType.setLeftGeneric(super.getDomainObjectCanonicalName());
		javaClassDataType.setRightGeneric(ObjectId.class.getCanonicalName());
		return subDataType;
	}

	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.buildJavaTypeConstructor(generator, domainClass);
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);

				methodBuilder.buildDataMethod(generator, this.getDataType(), domainClass, f);
			}
		}
	}
	
	protected void buildJavaTypeConstructor(CodesGenerator generator, Class<?> domainClass){
		DataConstructor dataConstructor = ((JavaClassDataType)super.getDataType()).createDataConstructor();
		CodeBlock codeBlock = dataConstructor.getContent();
		HarcodedCodeLine codeLine = new HarcodedCodeLine("super(" + domainClass.getSimpleName() + ".class, " + ObjectId.class.getSimpleName() + ".class)");
		codeLine.addGlobalImport(domainClass.getCanonicalName());
		codeLine.addGlobalImport(ObjectId.class.getCanonicalName());
		codeBlock.addCodeLine(codeLine);
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "SpringHibernateDAO";
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// TODO 
	}

	@Override
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}

	@Override
	protected void processJavaTypeInterfaces(CodesGenerator generator) {
		String canonicalInterfaceType = generator.getImportForClassName(super.getDomainObjectSimpleName() + "DAO");
		super.getDataType().createDataInterface(canonicalInterfaceType);
	}	
}
