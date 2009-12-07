/**
 * 
 */
package com.angel.code.generator.builders.method.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import ar.com.angelDurmiente.beans.BeanDemo;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableMultipleReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AccesorServiceTestAnnotationMethodBuilder implements MethodBuilder {

	public <T> String buildContentMethodName(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		String methodName = "buscar";
		methodName += accesor.unique() ? "Unico" : "Todos";
		methodName += accesor.optional() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		return methodName;
	}
	
	public <T> String buildMethodName(Class<T> domainClass, Field property) {
		return "test" + this.buildContentMethodName(domainClass, property);
	}

	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property) {
		return null;
	}

	public <T> Annotation getAnnotation(Class<T> domainClass, Field property) {
		return property.getAnnotation(Accesor.class);
	}


	public void buildDataMethod(CodesGenerator generator, DataType dataType, Class<?> domainClass, Object owner) {
		Field property = (Field) owner;
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);

		String methodName = this.buildMethodName(domainClass, (Field) owner);
		DataMethod dataMethod = dataType.createDataMethod(methodName);		
		CodeBlock codeBlock = dataMethod.getContent();

		String domainObjectSimpleName = domainClass.getSimpleName();
		codeBlock.createCommentCodeLine("Must set a value to " + property.getName() + " property.");

		/** Create a line like: Type propertyName = null; */
		codeBlock.createAssignableCodeLine(property.getName(), null, property.getType().getCanonicalName());
		
		String simpleServiceName = domainObjectSimpleName + "Service";
		String canonicaLServiceName = generator.getImportForClassName(simpleServiceName);
		String getterDomainObjectServiceName = ReflectionHelper.getGetMethodName(simpleServiceName);

		/** Create a code line this.getDomainObjectService().buscar....(parametersNames)*/
		ExecutableReturnCodeLine executableGetServiceReturnCodeLine = new ExecutableReturnCodeLine(getterDomainObjectServiceName, canonicaLServiceName);
		
		String methodDAOContentName = this.buildContentMethodName(domainClass, property);
		ExecutableReturnCodeLine executableBuscarReturnCodeLine = new ExecutableReturnCodeLine(methodDAOContentName, dataType.getDomainObjectCanonicalName());
		executableBuscarReturnCodeLine.addParameterNameString(property.getName());
		executableBuscarReturnCodeLine.addParameterName(property.getName());
		if(!accesor.unique()){
			executableBuscarReturnCodeLine.setReturnCollectionCanonicalName(List.class.getCanonicalName());
		}

		ExecutableMultipleReturnCodeLine executableMultipleReturnCodeLine = new ExecutableMultipleReturnCodeLine(executableGetServiceReturnCodeLine);
		executableMultipleReturnCodeLine.addExecutableReturnCodeLine(executableBuscarReturnCodeLine);

		AssignableCodeLine assignableBeanDemoVariable = new AssignableCodeLine(BeanDemo.class.getCanonicalName(), executableMultipleReturnCodeLine);
		codeBlock.addCodeLine(assignableBeanDemoVariable);
		
		ExecutableCodeLine assertNotNulExecutableCodeLine = new ExecutableCodeLine("assertNotNull");
		assertNotNulExecutableCodeLine
			.addParameterNameString("Result collection mustn't be null.")
			.addParameterName("result");
		codeBlock.addCodeLine(assertNotNulExecutableCodeLine);
		
		ExecutableCodeLine assertTrueExecutableCodeLine = new ExecutableCodeLine("assertNotNull");
		assertTrueExecutableCodeLine
			.addParameterNameString("Result collection size must be more than one.")
			.addParameterName("result.size() > 0");
		codeBlock.addCodeLine(assertTrueExecutableCodeLine);
		
	}
}
