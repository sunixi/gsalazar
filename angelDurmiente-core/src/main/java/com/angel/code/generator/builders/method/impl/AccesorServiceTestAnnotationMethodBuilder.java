/**
 * 
 */
package com.angel.code.generator.builders.method.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaCodeLine;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.codeLine.CommentCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
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
		String methodName = this.buildMethodName(domainClass, (Field) owner);
		DataMethod dataMethod = dataType.createDataMethod(methodName);		
		CodeBlock codeBlock = dataMethod.getContent();

		String domainObjectSimpleName = domainClass.getSimpleName();
		CommentCodeLine commentCodeLine = codeBlock.createCommentCodeLine();
		commentCodeLine.setTODOComment("Must set a value to " + property.getName() + " property.");

		codeBlock.createAssignableCodeLine(property.getName(), null, property.getType().getCanonicalName());

		String getServiceName = "get" + domainObjectSimpleName + "Service";
		ExecutableCodeLine executableCodeLine = new ExecutableReturnCodeLine(getServiceName, );
		
		
		List<String> parameters = new ArrayList<String>();
		parameters.add(property.getName());
		methodName +=  this.buildContentMethodName(domainClass, property);
		JavaCodeLine calledMethodLine = javaBlockCode.getLineCodeCalledMethod(methodName, parameters);

		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		if(!accesor.unique()){
			javaBlockCode
				.addLineCodeCollectionVariableAssigment(
						(Class<? extends Collection<?>>) List.class, domainClass.getSimpleName(), "result", calledMethodLine);

		} else {
			javaBlockCode.addLineCodeAssigmentTypedVariable(property.getType().getCanonicalName(),
					property.getName(), calledMethodLine);
		}
		
		List<String> notNullAssertParameters = new ArrayList<String>();
		notNullAssertParameters.add("\"Result collection mustn't be null.\"");
		notNullAssertParameters.add("result");
		javaBlockCode.addLineCodeCallMethodWithParameters("assertNotNull", notNullAssertParameters);
		
		List<String> assertParameters = new ArrayList<String>();
		assertParameters.add("\"Result collection size must be more than one.\"");
		assertParameters.add("result.size() > 0");
		javaBlockCode.addLineCodeCallMethodWithParameters("assertTrue", assertParameters);
		
		
		
		
		
		
		
		
		this.buildMethodContent(domainClass, (Field) owner);
		List<DataParameter> methodParameters = this.buildDataParameters(domainClass, (Field) owner);
		DataParameter returnParamter = this.buildReturnParameter(domainClass, (Field) owner);

		dataMethod.setParameters(methodParameters);
		dataMethod.setContent(codeBlock);
		dataMethod.setReturnType(returnParamter);		
	}
}
