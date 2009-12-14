/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.xml;

import java.util.List;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolDataSource;

import com.angel.architecture.spring.DDLGeneratingAnnotationSessionFactoryBean;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupXMLGenerator;
import com.angel.code.generator.data.impl.xml.spring.XMLBean;
import com.angel.code.generator.data.impl.xml.spring.XMLBeans;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyList;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyRef;
import com.angel.code.generator.data.impl.xml.spring.XMLPropertyValue;
import com.mysql.jdbc.Driver;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ModelSpringXMLGenerator extends GroupXMLGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(ModelSpringXMLGenerator.class);
	protected static final String DEFAULT_XML_FILE_NAME = "applicationContext-model.xml";
	
	public ModelSpringXMLGenerator(){
		super();
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	public ModelSpringXMLGenerator(String basePackage){
		super(basePackage);
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	@Override
	protected void generateCodeFor(CodesGenerator generator, List<Class<?>> domainClasses) {
		XMLBeans rootServiceXML = new XMLBeans();
		this.processDataSourceBean(generator, domainClasses, rootServiceXML);
		this.processSessionFactoryBean(generator, domainClasses, rootServiceXML);
		super.addRootBean(rootServiceXML);
	}

	/*
    <bean id="dataSource" class="org.logicalcobwebs.proxool.ProxoolDataSource">
        <property name="alias" value="ad"/>
        <property name="driver" value="com.mysql.jdbc.Driver"/>
        <property name="driverUrl" value="jdbc:mysql://localhost:3306/angelDurmiente"/>
        
        <!-- ODRA Server 
        <property name="delegateProperties" value="user=dba,password=trAyU2eg"/>
        <property name="user" value="dba"/>
        <property name="password" value="trAyU2eg"/>
        -->
        <!--Localhost -->
        <property name="delegateProperties" value="user=root,password=root"/>
        <property name="user" value="root"/>
        <property name="password" value="root"/>

        <!--<property name="houseKeepingSleepTime" value="15000"/>--> 
        <!-- Este parametro es el sql que usa para testear. Tener cuidado con lo que se setea -->
        <property name="houseKeepingTestSql" value="select 1 from dual" />
        <property name="maximumConnectionCount" value="5"/>
        <property name="minimumConnectionCount" value="5"/>
        <!-- Tiempo de coneccion ejecutando algo = 20 minutos -->
        <property name="maximumActiveTime" value="1200000"/>
        <!--<property name="maximumConnectionLifetime" value="7200000"/>--> 
        <property name="testBeforeUse" value="true"/>
        <property name="testAfterUse" value="false"/>
        <property name="verbose" value="false"/>
        <property name="prototypeCount" value="5"/>
        <!-- Este parametro muestra el query que ejecutó una conexion en el admin del proxool -->
        <property name="trace" value="true"/>
        <property name="statistics" value="10s,1m,15m,1d"/>
    </bean>
	 */
	protected void processDataSourceBean(CodesGenerator generator, List<Class<?>> domainClasses, XMLBeans rootModelXML){
		String applicationInitials = generator.getApplicationInitials();
		XMLBean dataSourceBean = new XMLBean("dataSource", ProxoolDataSource.class.getCanonicalName());
		dataSourceBean.addProperty(new XMLPropertyValue("alias", applicationInitials));
		dataSourceBean.addProperty(new XMLPropertyValue("driver", Driver.class.getCanonicalName()));
		dataSourceBean.addProperty(new XMLPropertyValue("driverUrl", "jdbc:mysql://localhost:3306/angelDurmiente"));
		dataSourceBean.addProperty(new XMLPropertyValue("delegateProperties", "user=root,password=root"));
		dataSourceBean.addProperty(new XMLPropertyValue("user", "root"));
		dataSourceBean.addProperty(new XMLPropertyValue("password", "root"));
		dataSourceBean.addProperty(new XMLPropertyValue("houseKeepingTestSql", "select 1 from dual"));
		dataSourceBean.addProperty(new XMLPropertyValue("maximumConnectionCount", "5"));
		dataSourceBean.addProperty(new XMLPropertyValue("minimumConnectionCount", "5"));
		dataSourceBean.addProperty(new XMLPropertyValue("maximumActiveTime", "1200000"));
		dataSourceBean.addProperty(new XMLPropertyValue("testBeforeUse", "true"));
		dataSourceBean.addProperty(new XMLPropertyValue("testAfterUse", "false"));
		dataSourceBean.addProperty(new XMLPropertyValue("verbose", "false"));
		dataSourceBean.addProperty(new XMLPropertyValue("prototypeCount", "5"));
		dataSourceBean.addProperty(new XMLPropertyValue("prototypeCount", "5"));
		dataSourceBean.addProperty(new XMLPropertyValue("trace", "true"));
		dataSourceBean.addProperty(new XMLPropertyValue("statistics", "10s,1m,15m,1d"));
		rootModelXML.addBean(dataSourceBean);
	}

	/*
    <bean id="sessionFactory" class="com.angel.architecture.spring.DDLGeneratingAnnotationSessionFactoryBean">
        <property name="entityInterceptor" ref="changeDetectionInterceptor"/>
        <property name="configLocation" value="classpath:hibernate.cfg.xml"/>
        <property name="dataSource" ref="dataSource"/>
        <property name="annotatedClasses">
            <list>
				<value>com.angel.architecture.persistence.beans.Role</value>
				<value>com.angel.architecture.persistence.beans.UserRoles</value>
				<value>ar.com.angelDurmiente.beans.Acorde</value>
				<value>ar.com.angelDurmiente.beans.Comentario</value>
			</list>
		</property>
        <property name="mappingResources">
            <list>
                <!-- mapeos de arquitectura -->
                <!--<value>com/angel/arquitectura/model/*.hbm.xml</value>-->
            </list>
        </property>
    </bean>
	 */
	protected void processSessionFactoryBean(CodesGenerator generator, List<Class<?>> domainClasses, XMLBeans rootModelXML){
		XMLBean dataSourceBean = new XMLBean("sessionFactory", DDLGeneratingAnnotationSessionFactoryBean.class.getCanonicalName());
		dataSourceBean.addProperty(new XMLPropertyRef("entityInterceptor", "changeDetectionInterceptor"));
		dataSourceBean.addProperty(new XMLPropertyValue("configLocation", "classpath:hibernate.cfg.xml"));
		dataSourceBean.addProperty(new XMLPropertyRef("dataSource", "dataSource"));
		XMLPropertyList annotatedClasses = new XMLPropertyList();
		annotatedClasses.setName("annotatedClasses");
		for(Class<?> domainClass: domainClasses){
			annotatedClasses.addValue(domainClass.getCanonicalName());
		}
		dataSourceBean.addProperty(annotatedClasses);
		
		XMLPropertyList mappingResources = new XMLPropertyList();
		mappingResources.setName("mappingResources");
		dataSourceBean.addProperty(mappingResources);
		rootModelXML.addBean(dataSourceBean);
	}
}
