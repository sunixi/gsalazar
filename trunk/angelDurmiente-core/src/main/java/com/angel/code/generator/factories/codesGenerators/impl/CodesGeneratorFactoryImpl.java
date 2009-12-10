/**
 * 
 */
package com.angel.code.generator.factories.codesGenerators.impl;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.impl.java.AnnotationDataGeneratorClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.AnnotationRowProcessorCommandClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ApplicationAnnotationGeneratorExecutableClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ApplicationBaseTestClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.DAOClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.DAOImplClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.FactoryClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ServiceClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ServiceImplClassGenerator;
import com.angel.code.generator.codeGenerator.impl.java.ServiceTestClassGenerator;
import com.angel.code.generator.codeGenerator.impl.xml.DAOsSpringXMLGenerator;
import com.angel.code.generator.codeGenerator.impl.xml.ServicesSpringXMLGenerator;
import com.angel.code.generator.codeGenerator.impl.xml.WebXMLGenerator;
import com.angel.code.generator.factories.codesGenerators.CodesGeneratorFactory;


/**
 * Default factory implementation to generate code. This factory add several codes generators by default.
 * The following codes generator are include in the codes generator instace created:
 * <br/><br/>
 * - {@link ServiceClassGenerator}: Generate services interfaces for domain objects in baseProjectPackage.services package.<br/>
 * - {@link ServiceImplClassGenerator}: Generate services implementations for domain objects in baseProjectPackage.services.impl package.<br/>
 * - {@link ServiceTestClassGenerator}: Generate services test for all methods in baseProjectPackage.services.impl package in the test resources.<br/>
 * - {@link DAOClassGenerator}: Generate daos interfaces for all domain objects in baseProjectPackage.daos package.<br/>
 * - {@link DAOImplClassGenerator}: Generate daos implementations for domain object in baseProjectPackage.daos.impl package.<br/>
 * - {@link AnnotationRowProcessorCommandClassGenerator}: Generate import row processors for all domain objects in baseProjectPackage.rowProcessors package.<br/>
 * - {@link AnnotationDataGeneratorClassGenerator}: Generate excel import data generators for all domain objects in baseProjectPackage.dataGenerators package.<br/>
 * - {@link FactoryClassGenerator}: Generate factories object for all domain objects (this objects are used to create object in test cases) in baseProjectPackage.factories package in test resources.<br/>
 * - {@link ApplicationAnnotationGeneratorExecutableClassGenerator}: Generate an application data generator to be execute with all data generator generated with {@link AnnotationDataGeneratorClassGenerator} in baseProjectPackage.executable package.<br/>
 * - {@link ServicesSpringXMLGenerator}: Generate a spring xml configration for all services generated with {@link ServiceClassGenerator} in the resources directory.<br/>
 * - {@link DAOsSpringXMLGenerator}: Generate a spring xml configuration for all daos generated with {@link DAOClassGenerator} in the resources directory.<br/>
 * - {@link WebXMLGenerator}: Generate a web.xml file with default base architecture configuration. 
 * 
 * @author Guillermo Salazar.
 * @since 29/Noviembre/2009.
 *
 */
public class CodesGeneratorFactoryImpl implements CodesGeneratorFactory {

	public CodesGenerator createClassesGenerator(String baseProjectPackage) {
		CodesGenerator classesGenerator = new CodesGenerator(baseProjectPackage);
		classesGenerator.addCodeGenerator(new AnnotationRowProcessorCommandClassGenerator("rowProcessors"));
		classesGenerator.addCodeGenerator(new AnnotationDataGeneratorClassGenerator("dataGenerators"));
		classesGenerator.addCodeGenerator(new ServiceClassGenerator("services"));
		classesGenerator.addCodeGenerator(new DAOClassGenerator("daos"));
		classesGenerator.addCodeGenerator(new ServiceImplClassGenerator("services.impl"));
		classesGenerator.addCodeGenerator(new DAOImplClassGenerator("daos.impl"));
		classesGenerator.addCodeGenerator(new ServiceTestClassGenerator("services"));
		classesGenerator.addCodeGenerator(new FactoryClassGenerator("factories"));
		classesGenerator.addCodeGenerator(new ApplicationAnnotationGeneratorExecutableClassGenerator("executables"));
		classesGenerator.addCodeGenerator(new ServicesSpringXMLGenerator());
		classesGenerator.addCodeGenerator(new DAOsSpringXMLGenerator());
		classesGenerator.addCodeGenerator(new WebXMLGenerator());
		classesGenerator.addCodeGenerator(new ApplicationBaseTestClassGenerator(""));
		return classesGenerator;
	}
	
}
