/**
 * 
 */
package com.angel.object.generator.classesGeneratorFactory.impl;

import com.angel.object.generator.CodesGenerator;
import com.angel.object.generator.classGenerator.impl.AnnotationDataGeneratorClassGenerator;
import com.angel.object.generator.classGenerator.impl.AnnotationRowProcessorCommandClassGenerator;
import com.angel.object.generator.classGenerator.impl.DAOClassGenerator;
import com.angel.object.generator.classGenerator.impl.DAOImplClassGenerator;
import com.angel.object.generator.classGenerator.impl.FactoryClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceImplClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceTestClassGenerator;
import com.angel.object.generator.classesGeneratorFactory.CodesGeneratorFactory;


/**
 * @author Guillermo Salazar.
 * @since 29/Noviembre/2009.
 *
 */
public class CodesGeneratorFactoryImpl implements CodesGeneratorFactory {

	public CodesGenerator createClassesGenerator(String baseProjectPackage) {
		CodesGenerator classesGenerator = new CodesGenerator(baseProjectPackage);
		classesGenerator.addCodeGenerator(new ServiceImplClassGenerator("services.impl", new ServiceClassGenerator("services")));
		classesGenerator.addCodeGenerator(new DAOImplClassGenerator("daos.impl", new DAOClassGenerator("daos")));
		classesGenerator.addCodeGenerator(new ServiceTestClassGenerator("services"));
		classesGenerator.addCodeGenerator(new AnnotationRowProcessorCommandClassGenerator("rowProcessors"));
		classesGenerator.addCodeGenerator(new AnnotationDataGeneratorClassGenerator("dataGenerators"));
		classesGenerator.addCodeGenerator(new FactoryClassGenerator("factories"));
		return classesGenerator;
	}
	
}
