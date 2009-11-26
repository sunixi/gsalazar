/**
 * 
 */
package com.angel.object.generator;


import org.junit.Test;

import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;

import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceClassGenerator;
import com.angel.object.generator.classNameStrategies.ClassNameStrategy;
import com.angel.object.generator.classNameStrategies.impl.ClassNameStrategyImpl;

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
		ClassGenerator serviceClassGenerator = new ServiceClassGenerator("services");
		serviceClassGenerator.excludeDomain("com.flex.Usuario");
		serviceClassGenerator.excludeDomain(Usuario.class);
		serviceClassGenerator.excludeDomain("Serice");
		ClassNameStrategy classNameStrategy = new ClassNameStrategyImpl();
		serviceClassGenerator.setClassNameStrategy(classNameStrategy);
		
		/** Objeto generador principal de clases. */
		//"ar.com.angelDurmiente": Paquete base del proyecto.
		Generator generator = new Generator("ar.com.angelDurmiente");
		/** serviceClassGenerator: generador de la clase que se desea a partir de la clase del dominio. */
		generator.addClassGenerator(serviceClassGenerator);
		generator.addDomain(Usuario.class);
		generator.addDomain(Texto.class);
		generator.generateClasses();
		
	}
}
