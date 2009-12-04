/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.angel.object.generator.CodesGenerator;
import com.angel.object.generator.classGenerator.GroupXMLGenerator;
import com.angel.object.generator.xml.types.XMLBean;
import com.angel.object.generator.xml.types.XMLBeans;
import com.angel.object.generator.xml.types.XMLPropertyBeanValue;
import com.angel.object.generator.xml.types.XMLPropertyRef;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ServicesSpringXMLGenerator extends GroupXMLGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(ServicesSpringXMLGenerator.class);
	protected static final String DEFAULT_XML_FILE_NAME = "applicationContext-services.xml";
	
	public ServicesSpringXMLGenerator(){
		super();
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	public ServicesSpringXMLGenerator(String basePackage){
		super(basePackage);
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	/*
	<bean id="usuarioService" parent="txProxyTemplate">
		<property name="target">
			<bean class="ar.com.angelDurmiente.services.impl.UsuarioServiceImpl">
				<property name="genericDAO" ref="usuarioDAO"/>
			</bean>
		</property>
	</bean>
	 */
	@Override
	protected void generateCodeFor(CodesGenerator generator, List<Class<?>> domainClasses) {
		XMLBeans rootServiceXML = new XMLBeans();
		for(Class<?> domainClass: domainClasses){
			String serviceImplCanonicalType = generator.getImportForClassName(
					domainClass.getSimpleName() + "ServiceImpl");
			String lowerServiceSimpleType = domainClass.getSimpleName().substring(0, 1).toLowerCase() + domainClass.getSimpleName().substring(1, domainClass.getSimpleName().length()); 
			String serviceSimpleType = lowerServiceSimpleType + "Service";
			String daoSimpleType = lowerServiceSimpleType + "DAO";
			
			XMLBean serviceBean = new XMLBean();
			serviceBean.setParent("txProxyTemplate");
			serviceBean.setId(serviceSimpleType);

			XMLBean targetPropertyBean = new XMLBean(null, serviceImplCanonicalType);
			targetPropertyBean.addProperty(new XMLPropertyRef("genericDAO", daoSimpleType));
			
			XMLPropertyBeanValue targetPropertyBeanValue = 
				new XMLPropertyBeanValue("sessionFactory", targetPropertyBean);

			serviceBean.addProperty(targetPropertyBeanValue);
			LOGGER.info("Adding xml service configuration for class [" + serviceImplCanonicalType + "].");
			rootServiceXML.addBean(serviceBean);
		}
		super.addRootBean(rootServiceXML);
	}
}
