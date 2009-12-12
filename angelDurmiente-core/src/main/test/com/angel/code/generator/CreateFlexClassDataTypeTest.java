/**
 * 
 */
package com.angel.code.generator;


import java.util.List;

import org.junit.Test;

import ar.com.angelDurmiente.beans.BeanDemo;

import com.angel.code.generator.data.impl.as3.FlexClassDataType;
import com.angel.code.generator.data.impl.as3.FlexDataMethod;
import com.angel.code.generator.data.impl.as3.annotations.FlexAnnotation;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class CreateFlexClassDataTypeTest {


	@Test
	public void testCreateNullAssigmentWithVariableNameCodeLineValid(){	
		FlexClassDataType flexClassDataType = new FlexClassDataType();
		flexClassDataType.setCanonicalName("Hola");
		flexClassDataType.setDomainObject(BeanDemo.class);
		flexClassDataType.createDataAnnotation("Bindable");
		flexClassDataType.createDataConstructor();
		
		FlexAnnotation remoteClassAnnotation = flexClassDataType.createDataAnnotation("RemoteClass");
		remoteClassAnnotation.createAnnotationPropertyString("alias", BeanDemo.class.getCanonicalName());
		
		
		flexClassDataType.createDataProperty("nombre", String.class.getCanonicalName());
		flexClassDataType.createDataProperty("usuarios", List.class.getCanonicalName());

		flexClassDataType.createDataMethodAccesorsFor("nombre");
		
		FlexDataMethod flexDataMethod = flexClassDataType.createDataMethod("buscarUsuarios");
		//flexDataMethod.createException(CodeGeneratorException.class.getCanonicalName());
		FlexAnnotation flexAnnotation = flexDataMethod.createAnnotation("Event");
		flexAnnotation.createAnnotationPropertyString("name", "usuarios");
		String convertedCode = flexClassDataType.convertCode();
		System.out.println(convertedCode);
	}


}
