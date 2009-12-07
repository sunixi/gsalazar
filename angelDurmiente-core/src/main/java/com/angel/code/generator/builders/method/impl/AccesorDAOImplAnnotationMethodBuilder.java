/**
 * 
 */
package com.angel.code.generator.builders.method.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ReturnableCodeLine;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AccesorDAOImplAnnotationMethodBuilder implements MethodBuilder {


	public <T> List<DataParameter> buildDataParameters(Class<T> domainClass, Field property) {
		List<DataParameter> parameters = new ArrayList<DataParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getType().getCanonicalName());
		parameters.add(javaParameter);
		return parameters;
	}

	public <T> void buildMethodContent(Class<T> domainClass, CodeBlock codeBlock, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		ExecutableReturnCodeLine executableReturnCodeLine = null;
		ReturnableCodeLine returnable = null;
		DataParameter returnDataParameter = this.buildReturnParameter(domainClass, property);
		if(accesor.unique()){
			if(accesor.optional()){
				executableReturnCodeLine = new ExecutableReturnCodeLine("super.findUniqueOrNull", returnDataParameter.getCanonicalType());
				returnable = new ReturnableCodeLine(returnDataParameter.getCanonicalType(), executableReturnCodeLine);
			} else {
				executableReturnCodeLine = new ExecutableReturnCodeLine("super.findUnique", returnDataParameter.getCanonicalType());
				returnable = new ReturnableCodeLine(returnDataParameter.getCanonicalType(), executableReturnCodeLine);
			}
		} else {
			executableReturnCodeLine = new ExecutableReturnCodeLine("super.findAll", returnDataParameter.getCanonicalType(), List.class.getCanonicalName());
			returnable = new ReturnableCodeLine(returnDataParameter.getCanonicalType(), List.class.getCanonicalName(), executableReturnCodeLine);
		}
		executableReturnCodeLine.addParameterName("\"" + property.getName() + "\"").addParameterName(property.getName());
		codeBlock.addCodeLine(returnable);
	}

	public <T> String buildMethodName(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		String methodName = "buscar";
		methodName += accesor.unique() ? "Unico" : "Todos";
		methodName += accesor.optional() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		return methodName;
	}

	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		Class<?> returnType = null;
		if(accesor.unique()){
			returnType = domainClass;
		} else {
			returnType = List.class;
		}
		return new JavaParameter(returnType.getCanonicalName());
	}

	public <T> Annotation getAnnotation(Class<T> domainClass, Field property) {
		return property.getAnnotation(Accesor.class);
	}
	
	public void buildDataMethod(CodesGenerator generator, DataType dataType, Class<?> domainClass, Object owner) {
		String methodName = this.buildMethodName(domainClass, (Field) owner);
		DataMethod dataMethod = dataType.createDataMethod(methodName);
		CodeBlock codeBlock = dataMethod.getContent();

		List<DataParameter> methodParameters = this.buildDataParameters(domainClass, (Field) owner);
		DataParameter returnParamter = this.buildReturnParameter(domainClass, (Field) owner);

		dataMethod.setParameters(methodParameters);
		dataMethod.setContent(codeBlock);
		dataMethod.setReturnType(returnParamter);		
	}
}
