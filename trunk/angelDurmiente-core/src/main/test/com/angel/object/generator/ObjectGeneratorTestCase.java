/**
 * 
 */
package com.angel.object.generator;


import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.junit.Test;

import ar.com.angelDurmiente.beans.BeanDemo;
import ar.com.angelDurmiente.beans.BeanDemo2;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.data.impl.xml.spring.XMLBean;
import com.angel.code.generator.data.impl.xml.spring.XMLBeans;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyRef;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyValue;
import com.angel.code.generator.data.impl.xml.web.XMLContextParameter;
import com.angel.code.generator.data.impl.xml.web.XMLListener;
import com.angel.code.generator.data.impl.xml.web.XMLWebapp;
import com.angel.code.generator.factories.codesGenerators.CodesGeneratorFactory;
import com.angel.code.generator.factories.codesGenerators.impl.CodesGeneratorFactoryImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
		generator.addDomain(BeanDemo2.class);
		generator.generateCode();		
	}
	
	public void taaaestGenerateDigesterXML(){	
        try {
            Digester digester = new Digester();
            digester.setValidating( false );

            digester.addObjectCreate( "beans", XMLBeans.class );
            digester.addObjectCreate( "beans/bean", XMLBean.class );
            digester.addBeanPropertySetter("bean/bean/property", "property" );

            digester.addBeanPropertySetter( "response/matches", "matches" );

            Reader reader = new StringReader(
                    "<?xml version='1.0' encoding='UTF-8'?>" + 
                    "<response>" + 
                        "<request><name>books</name><value>xml</value></request>" +  
                        "<matches>20</matches>" + 
                    "</response>");
            digester.parse( reader );
    //        System.out.println( response.toString() );

         } catch( Exception exc ) {
            exc.printStackTrace();
         }

				
	}
	
	/*
	<bean id="cancionAcordeDAO" class="ar.com.angelDurmiente.daos.impl.CancionAcordeSpringHibernateDAO" singleton="true">
        <property name="sessionFactory"     ref="sessionFactory"/>
		<property name="persistentClass"    value="ar.com.angelDurmiente.beans.CancionAcorde"/>
        <property name="codeClass"          value="com.angel.architecture.persistence.ids.impl.ObjectIdImpl"/>
    </bean>
	 */
	@Test
	public void testGenerateXStreamXML(){	
		XMLBean beanBeanDemoDAO = new XMLBean("cancionAcordeDAO", "ar.com.angelDurmiente.daos.impl.CancionAcordeSpringHibernateDAO", "true");
		beanBeanDemoDAO.addProperty(new XMLPropertyRef("sessionFactory", "sessionFactory"));
		beanBeanDemoDAO.addProperty(new XMLPropertyValue("persistentClass", "ar.com.angelDurmiente.beans.CancionAcorde"));
		beanBeanDemoDAO.addProperty(new XMLPropertyValue("codeClass", "com.angel.architecture.persistence.ids.impl.ObjectIdImpl"));
		
		XMLBean beanBeanDemo2DAO = new XMLBean("cancionAcordeDAO", "ar.com.angelDurmiente.daos.impl.CancionAcordeSpringHibernateDAO", "true");
		beanBeanDemo2DAO.addProperty(new XMLPropertyRef("sessionFactory", "sessionFactory"));
		beanBeanDemo2DAO.addProperty(new XMLPropertyValue("persistentClass", "ar.com.angelDurmiente.beans.CancionAcorde"));
		beanBeanDemo2DAO.addProperty(new XMLPropertyValue("codeClass", "com.angel.architecture.persistence.ids.impl.ObjectIdImpl"));
		
		XMLBeans beans = new XMLBeans();
		beans.addBean(beanBeanDemoDAO);
		beans.addBean(beanBeanDemo2DAO);

		XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
		xstream.autodetectAnnotations(true);
/*
		xstream.processAnnotations(XMLBeans.class);
		xstream.processAnnotations(XMLBean.class);
		xstream.processAnnotations(XMLPropertyValue.class);
		xstream.processAnnotations(XMLPropertyRef.class);
*/
		/*
		xstream.alias("beans", XMLBeans.class);
		xstream.alias("bean", XMLBean.class);		
		xstream.alias("property", XMLPropertyValue.class);
		xstream.alias("property", XMLPropertyRef.class);
		xstream.addImplicitCollection(XMLBean.class, "properties");
		xstream.addImplicitCollection(XMLBeans.class, "beans");
		xstream.aliasField("class", XMLBean.class, "klass");
		xstream.useAttributeFor(XMLBean.class, "id");
		xstream.useAttributeFor(XMLBean.class, "klass");
		xstream.useAttributeFor(XMLBean.class, "singleton");
		xstream.useAttributeFor(XMLPropertyValue.class, "name");
		xstream.useAttributeFor(XMLPropertyValue.class, "value");
		xstream.useAttributeFor(XMLPropertyRef.class, "ref");
		 */

		String xml = xstream.toXML(beans);
		System.out.println(xml);
	}

	@Test
	public void testGenerateWebappXML(){	
		XMLWebapp xmlWebapp = new XMLWebapp();
		xmlWebapp.setDisplayName("Elangeldurmiente. Sitio solo para personas romanticas.");
		xmlWebapp.addContextParam(new XMLContextParameter("flex.class.path", "/WEB-INF/flex/hotfixes,/WEB-INF/flex/jars"));
		xmlWebapp.addListener(new XMLListener("flex.messaging.HttpFlexSession"));
		xmlWebapp.addListener(new XMLListener("org.springframework.web.context.ContextLoaderListener"));
		xmlWebapp.addListener(new XMLListener("org.springframework.web.context.request.RequestContextListener"));
		xmlWebapp.addFilter("lazyLoadingFilter", "org.springframework.orm.hibernate3.support.OpenSessionInViewFilter", "/*");
		xmlWebapp.addServlet("MessageBrokerServlet", "flex.messaging.MessageBrokerServlet", "/messagebroker/*");

		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		String xml = xstream.toXML(xmlWebapp);
		System.out.println(xml);
	}
}
