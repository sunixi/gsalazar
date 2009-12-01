/**
 * 
 */
package com.angel.object.generator;


import org.junit.Test;

import ar.com.angelDurmiente.beans.BeanDemo;

import com.angel.object.generator.classesGeneratorFactory.ClassesGeneratorFactory;
import com.angel.object.generator.classesGeneratorFactory.impl.ClassesGeneratorFactoryImpl;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class ObjectGeneratorTestCase {


	@Test
	public void testGenerateObjectsValid(){
		/**
		 * "services": paquete donde se van a generar las clases. Si no se pone nada se pone services.
		 * 				Se concatena al del objeto Generator.
		 */
		/*
		ClassGenerator serviceClassGenerator = new ServiceClassGenerator("services");
		serviceClassGenerator.addTagAuthor("Guillermo Daniel Salazar");

		ClassGenerator serviceImplClassGenerator = new ServiceImplClassGenerator("services.impl", serviceClassGenerator);
		serviceImplClassGenerator.addTagAuthor("Guillermo Daniel Salazar");

		ClassGenerator daoClassGenerator = new DAOClassGenerator("daos");
		daoClassGenerator.addTagAuthor("Guillermo Daniel Salazar");

		ClassGenerator daoImplClassGenerator = new DAOImplClassGenerator("daos.impl", daoClassGenerator);
		daoImplClassGenerator.addTagAuthor("Guillermo Daniel Salazar");*/
		/*generator.addClassGenerator(serviceImplClassGenerator);
		generator.addClassGenerator(daoImplClassGenerator);*/

		
		/** Objeto generador principal de clases. */
		ClassesGeneratorFactory classesGeneratorFactory = new ClassesGeneratorFactoryImpl();
		ClassesGenerator generator = classesGeneratorFactory.createClassesGenerator("ar.com.angelDurmiente");//new ClassesGenerator("ar.com.angelDurmiente");
		generator.addAuthorTag("Guillermo Daniel Salazar");
		generator.addDomain(BeanDemo.class);
		generator.addRelativeImport("daos", "BeanDemoDAO");
		generator.addRelativeImport("services", "BeanDemoService");
		generator.addRelativeImport("rowProcessors", "BeanDemoAnnotationRowProcessorCommand");
		generator.generateClasses();		
	}
}
