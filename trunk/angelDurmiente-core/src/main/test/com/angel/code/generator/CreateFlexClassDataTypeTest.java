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
import com.angel.code.generator.data.impl.as3.properties.FlexProperty;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class CreateFlexClassDataTypeTest {


	@Test
	public void testCreateNullAssigmentWithVariableNameCodeLineValid(){	
		FlexClassDataType flexClassDataType = new FlexClassDataType();
		flexClassDataType.setCanonicalName("HOLA");
		flexClassDataType.setDomainObject(BeanDemo.class);
		flexClassDataType.createDataAnnotation("Bindable");
		FlexAnnotation remoteClassAnnotation = flexClassDataType.createDataAnnotation("RemoteClass");
		remoteClassAnnotation.createAnnotationPropertyString("alias", BeanDemo.class.getCanonicalName());
		
		
		FlexProperty nombreProperty = flexClassDataType.createDataProperty("nombre");
		nombreProperty.setCanonicalType(String.class.getCanonicalName());
		FlexProperty flexProperty = flexClassDataType.createDataProperty("usuarios");
		flexProperty.setCanonicalType(List.class.getCanonicalName());

		FlexDataMethod nombreGetterDataMethod = flexClassDataType.createGetterDataMethod("nombre");
		
		FlexDataMethod flexDataMethod = flexClassDataType.createDataMethod("buscarUsuarios");
		FlexAnnotation flexAnnotation = flexDataMethod.createAnnotation("Event");
		flexAnnotation.createAnnotationPropertyString("name", "usuarios");
		String convertedCode = flexClassDataType.convertCode();
		System.out.println(convertedCode);
	}


}
