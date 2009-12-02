/**
 * 
 */
package com.angel.object.generator;


import org.junit.Test;

import ar.com.angelDurmiente.beans.BeanDemo;

import com.angel.object.generator.classesGeneratorFactory.CodesGeneratorFactory;
import com.angel.object.generator.classesGeneratorFactory.impl.CodesGeneratorFactoryImpl;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class ObjectGeneratorTestCase {


	@Test
	public void testCodesGeneratorValid(){	
		/** Objeto generador principal de clases. */
		CodesGeneratorFactory classesGeneratorFactory = new CodesGeneratorFactoryImpl();
		CodesGenerator generator = classesGeneratorFactory.createClassesGenerator("ar.com.angelDurmiente");
		//generator.setBeanPackageName("beans");
		generator.addAuthorTag("Guillermo Daniel Salazar");
		generator.addDomain(BeanDemo.class);
		generator.generateCode();		
	}
}
