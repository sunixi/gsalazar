/**
 * 
 */
package com.angel.object.generator.classesGeneratorFactory.impl;

import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.classGenerator.impl.AnnotationRowProcessorCommandClassGenerator;
import com.angel.object.generator.classGenerator.impl.DAOClassGenerator;
import com.angel.object.generator.classGenerator.impl.DAOImplClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceImplClassGenerator;
import com.angel.object.generator.classGenerator.impl.ServiceTestClassGenerator;
import com.angel.object.generator.classesGeneratorFactory.ClassesGeneratorFactory;


/**
 * @author Guillermo Salazar.
 * @since 29/Noviembre/2009.
 *
 */
public class ClassesGeneratorFactoryImpl implements ClassesGeneratorFactory {

	public ClassesGenerator createClassesGenerator(String baseProjectPackage) {
		ClassesGenerator classesGenerator = new ClassesGenerator(baseProjectPackage);
		classesGenerator.addClassGenerator(new ServiceImplClassGenerator("services.impl", new ServiceClassGenerator("services")));
		classesGenerator.addClassGenerator(new DAOImplClassGenerator("daos.impl", new DAOClassGenerator("daos")));
		classesGenerator.addClassGenerator(new ServiceTestClassGenerator("services"));
		classesGenerator.addClassGenerator(new AnnotationRowProcessorCommandClassGenerator("rowProcessors"));
		return classesGenerator;
	}
	
}
