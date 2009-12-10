/**
 * 
 */
package com.angel.code.generator;


import static org.junit.Assert.*;

import org.junit.Test;

import ar.com.angelDurmiente.beans.BeanDemo;

import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableMultipleReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class CreateCodeBlockTest {


	@Test
	public void testCreateNullAssigmentWithVariableNameCodeLineValid(){	
		AssignableCodeLine assignableBeanDemoVariable = new AssignableCodeLine("beanDemo" , null, BeanDemo.class.getCanonicalName());
		assertEquals("Assignable line code must be equals.", "BeanDemo beanDemo = null", assignableBeanDemoVariable.convertCode());
	}

	@Test
	public void testCreateNullAssigmentCodeLineValid(){	
		AssignableCodeLine assignableBeanDemoVariable = new AssignableCodeLine(BeanDemo.class.getCanonicalName());
		assertEquals("Null assignable line code must be equals.", "BeanDemo beanDemo = null", assignableBeanDemoVariable.convertCode());
	}

	@Test
	public void testCreateAssigmentCodeLineWithExecutableValid(){
		ExecutableReturnCodeLine executableReturnCodeLine = new ExecutableReturnCodeLine("getService", BeanDemo.class.getCanonicalName());
		AssignableCodeLine assignableBeanDemoVariable = new AssignableCodeLine(BeanDemo.class.getCanonicalName(), executableReturnCodeLine);
		assertEquals("Null assignable line code must be equals.", "BeanDemo beanDemo = this.getService()", assignableBeanDemoVariable.convertCode());
	}

	@Test
	public void testCreateAssigmentCodeLineWithMultipleExecutablesValid(){
		ExecutableReturnCodeLine executableGetServiceReturnCodeLine = new ExecutableReturnCodeLine("getService", BeanDemo.class.getCanonicalName());
		ExecutableReturnCodeLine executableBuscarTodosReturnCodeLine = new ExecutableReturnCodeLine("buscarTodos", BeanDemo.class.getCanonicalName());
		ExecutableMultipleReturnCodeLine executableMultipleReturnCodeLine = new ExecutableMultipleReturnCodeLine(executableGetServiceReturnCodeLine);
		executableMultipleReturnCodeLine.addExecutableReturnCodeLine(executableBuscarTodosReturnCodeLine);

		AssignableCodeLine assignableBeanDemoVariable = new AssignableCodeLine(BeanDemo.class.getCanonicalName(), executableMultipleReturnCodeLine);
		assertEquals("Assignable line code must be equals.", "BeanDemo beanDemo = this.getService().buscarTodos()", assignableBeanDemoVariable.convertCode());
	}

	@Test
	public void testCreateAssigmentCodeLineWithMultipleExecutablesAndParametersValid(){
		ExecutableReturnCodeLine executableGetServiceReturnCodeLine = new ExecutableReturnCodeLine("getService", BeanDemo.class.getCanonicalName());
		executableGetServiceReturnCodeLine.addParameterName("apellido");
		ExecutableReturnCodeLine executableBuscarTodosReturnCodeLine = new ExecutableReturnCodeLine("buscarTodos", BeanDemo.class.getCanonicalName());
		executableBuscarTodosReturnCodeLine.addParameterNameString("nombre");
		executableBuscarTodosReturnCodeLine.addParameterName("nombre");
		ExecutableMultipleReturnCodeLine executableMultipleReturnCodeLine = new ExecutableMultipleReturnCodeLine(executableGetServiceReturnCodeLine);
		executableMultipleReturnCodeLine.addExecutableReturnCodeLine(executableBuscarTodosReturnCodeLine);

		AssignableCodeLine assignableBeanDemoVariable = new AssignableCodeLine(BeanDemo.class.getCanonicalName(), executableMultipleReturnCodeLine);
		assertEquals("Assignable line code must be equals.", "BeanDemo beanDemo = this.getService(apellido).buscarTodos(\"nombre\", nombre)", assignableBeanDemoVariable.convertCode());
	}

}
