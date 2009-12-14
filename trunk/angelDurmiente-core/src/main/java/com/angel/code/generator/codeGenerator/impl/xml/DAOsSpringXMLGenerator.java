/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.xml;

import java.util.List;

import org.apache.log4j.Logger;

import com.angel.architecture.persistence.ids.impl.ObjectIdImpl;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupXMLGenerator;
import com.angel.code.generator.data.impl.xml.spring.XMLBean;
import com.angel.code.generator.data.impl.xml.spring.XMLBeans;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyRef;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyValue;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class DAOsSpringXMLGenerator extends GroupXMLGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(DAOsSpringXMLGenerator.class);
	protected static final String DEFAULT_XML_FILE_NAME = "applicationContext-daos.xml";
	
	public DAOsSpringXMLGenerator(){
		super();
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	public DAOsSpringXMLGenerator(String basePackage){
		super(basePackage);
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	@Override
	protected void generateCodeFor(CodesGenerator generator, List<Class<?>> domainClasses) {
		XMLBeans rootServiceXML = new XMLBeans();
		for(Class<?> domainClass: domainClasses){
			String serviceImplCanonicalType = generator.getImportForClassName(
					domainClass.getSimpleName() + "SpringHibernateDAO");
			String lowerServiceSimpleType = domainClass.getSimpleName().substring(0, 1).toLowerCase() + domainClass.getSimpleName().substring(1, domainClass.getSimpleName().length()); 
			String serviceSimpleType = lowerServiceSimpleType + "DAO";
			XMLBean serviceBean = new XMLBean(serviceSimpleType, serviceImplCanonicalType, "true");
			serviceBean.addProperty(new XMLPropertyRef("sessionFactory", "sessionFactory"));
			serviceBean.addProperty(new XMLPropertyValue("persistentClass", domainClass.getCanonicalName()));
			serviceBean.addProperty(new XMLPropertyValue("codeClass", ObjectIdImpl.class.getCanonicalName()));
			LOGGER.info("Adding xml service configuration for class [" + serviceImplCanonicalType + "].");
			rootServiceXML.addBean(serviceBean);
		}
		super.addRootBean(rootServiceXML);
	}

	@Override
	protected void generateXMLHeader(List<String> headerXMLLines) {
		headerXMLLines.add("<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\n\t\t" +
			    "http://www.springframework.org/dtd/spring-beans.dtd\">\n");
	}
}
