/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.xml;

import java.util.List;

import org.apache.log4j.Logger;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupXMLGenerator;
import com.angel.code.generator.data.impl.xml.web.XMLContextParameter;
import com.angel.code.generator.data.impl.xml.web.XMLListener;
import com.angel.code.generator.data.impl.xml.web.XMLSertvletInitParameter;
import com.angel.code.generator.data.impl.xml.web.XMLServletMapping;
import com.angel.code.generator.data.impl.xml.web.XMLWebapp;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class WebXMLGenerator extends GroupXMLGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(WebXMLGenerator.class);
	protected static final String DEFAULT_BASE_SOURCES_DIRECTORY = "\\src\\main\\webapp\\";
	protected static final String DEFAULT_XML_FILE_NAME = "web.xml";
	
	public WebXMLGenerator(){
		super();
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
		super.setBaseSourcesDirectory(DEFAULT_BASE_SOURCES_DIRECTORY);
	}

	public WebXMLGenerator(String basePackage){
		super(basePackage);
		super.setXmlFileName(DEFAULT_XML_FILE_NAME);
		super.setBaseSourcesDirectory(DEFAULT_BASE_SOURCES_DIRECTORY);
	}

	@Override
	protected void generateCodeFor(CodesGenerator generator, List<Class<?>> domainClasses) {
		LOGGER.info("Generating code for web XML.");
		XMLWebapp xmlWebapp = new XMLWebapp();
		xmlWebapp.addWelcomeFile("main.html");
		LOGGER.info("[" + this.getXmlFileName() + "] Setting display name [" + generator.getProjectName() + "].");
		xmlWebapp.setDisplayName(generator.getProjectName());
		LOGGER.info("[" + this.getXmlFileName() + "] Processing context parameters.");
		this.processContextParameters(generator, xmlWebapp);
		LOGGER.info("[" + this.getXmlFileName() + "] Processing listeners.");
		this.processListeners(xmlWebapp);
		LOGGER.info("[" + this.getXmlFileName() + "] Processing filters.");
		this.processFilters(xmlWebapp);
		LOGGER.info("[" + this.getXmlFileName() + "] Processing servlets.");
		this.processServlets(xmlWebapp);
		LOGGER.info("[" + this.getXmlFileName() + "] Adding to root xml bean.");
		super.addRootBean(xmlWebapp);
	}

	protected void processServlets(XMLWebapp xmlWebapp) {

		xmlWebapp.addServlet(
				"MessageBrokerServlet",
				"flex.messaging.MessageBrokerServlet",
				"/messagebroker/*",
				"1",
				new XMLSertvletInitParameter("services.configuration.file","/WEB-INF/flex/services-config.xml"),
				new XMLSertvletInitParameter("useContextClassLoader","true"),
				new XMLSertvletInitParameter("flex.write.path","/WEB-INF/flex")
		);
		xmlWebapp.addServlet(
				"downloadExportFileServlet",
				"com.angel.webapp.servlets.DownloadExportFileServlet",
				"/downloadExportFile/*"
		);
		xmlWebapp.addServlet(
				"downloadEmailAttachmentServlet",
				"com.angel.webapp.servlets.DownloadEmailAttachmentServlet",
				"/downloadEmailAttachment/*"
		);
		xmlWebapp.addServlet(
				"uploadImportFileServlet",
				"com.angel.webapp.servlets.UploadImportFileServlet",
				"/uploadImportFile/*"
		);
		xmlWebapp.addServlet(
				"uploadEmailAttachmentServlet",
				"com.angel.webapp.servlets.UploadEmailAttachmentServlet",
				"/uploadEmailAttachment/*"
		);
		xmlWebapp.addServlet(
				"initializerServlet",
				"com.angel.webapp.servlets.InitializerServlet",
				"/initializer/*",
				new XMLSertvletInitParameter("ioConfigFile","io-config.xml")
		);
		xmlWebapp.addServlet(
				"FlexForbiddenServlet",
				"flex.bootstrap.BootstrapServlet",
				"*.mxml",
				new XMLSertvletInitParameter("servlet.class","flex.webtier.server.j2ee.ForbiddenServlet")
		);
		xmlWebapp.addServlet(
				"FlexInternalServlet",
				"flex.bootstrap.BootstrapServlet",
				"/flex-internal/*",
				"10"
		);
		xmlWebapp.addServletMapping(new XMLServletMapping("FlexForbiddenServlet", "*.as"));
		xmlWebapp.addServletMapping(new XMLServletMapping("FlexForbiddenServlet", "*.swc"));
	}

	protected void processFilters(XMLWebapp xmlWebapp) {
		xmlWebapp.addFilter("lazyLoadingFilter", "org.springframework.orm.hibernate3.support.OpenSessionInViewFilter", "/*");
	}

	protected void processListeners(XMLWebapp xmlWebapp) {
		xmlWebapp.addListener(new XMLListener("flex.messaging.HttpFlexSession"));
		xmlWebapp.addListener(new XMLListener("org.springframework.web.context.ContextLoaderListener"));
		xmlWebapp.addListener(new XMLListener("org.springframework.web.context.request.RequestContextListener"));
	}

	protected void processContextParameters(CodesGenerator generator, XMLWebapp xmlWebapp) {
		String applicationInitials = generator.getApplicationInitials();
		LOGGER.info("[" + super.getXmlFileName() + "] Getting application initials [" + applicationInitials + "].");
		xmlWebapp.addContextParam(
				new XMLContextParameter(
						"flex.class.path",
						"/WEB-INF/flex/hotfixes,/WEB-INF/flex/jars"
						)
				);
		xmlWebapp.addContextParam(
				new XMLContextParameter(
						"contextConfigLocation",
						"classpath:applicationContext-model.xml,\n" + 
			            "classpath:applicationContext-dao.xml,\n" +
			            "classpath:applicationContext-flex.xml,\n" +
			            "classpath:applicationContext-services.xml,\n" +
			            "classpath:applicationContext-" + applicationInitials + "flex.xml,\n" +
			            "classpath:applicationContext-" + applicationInitials + "daos.xml,\n" +
			            "classpath:applicationContext-" + applicationInitials + "services.xml,\n" +
			            "classpath:applicationContext-" + applicationInitials + "model.xml\n"
						)
				);
	}

	@Override
	protected void generateXMLHeader(List<String> headerXMLLines) {
		//Do nothing.	
	}
}
