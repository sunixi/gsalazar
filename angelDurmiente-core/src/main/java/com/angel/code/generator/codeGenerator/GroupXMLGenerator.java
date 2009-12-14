/**
 * 
 */
package com.angel.code.generator.codeGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.angel.code.generator.CodesGenerator;
import com.angel.common.helpers.FileHelper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public abstract class GroupXMLGenerator extends GroupCodeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(GroupXMLGenerator.class);
	protected static final String DEFAULT_BASE_SOURCES_DIRECTORY = "\\src\\main\\resources\\";
	protected static final String DEFAULT_XML_FILE_NAME = "applicationContext.xml";

	private XStream xStream;

	private Object rootXML;
	
	private String xmlFileName;
	
	private List<String> headerXMLLines;

	public GroupXMLGenerator(){
		super();
		super.setBaseSourcesDirectory(DEFAULT_BASE_SOURCES_DIRECTORY);
		this.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	public GroupXMLGenerator(String basePackage){
		super(basePackage);
		super.setBaseSourcesDirectory(DEFAULT_BASE_SOURCES_DIRECTORY);
		this.setXmlFileName(DEFAULT_XML_FILE_NAME);
	}

	public void initializeCodeGenerator(CodesGenerator generator, List<Class<?>> domainClasses){
		this.setXStream(new XStream(new DomDriver()));
		this.getXStream().autodetectAnnotations(true);
		this.initializeXMLConfiguration(generator, domainClasses);
	}
	
	protected void initializeXMLConfiguration(CodesGenerator generator, List<Class<?>> domainClasses){
		//Do nothing.
	}
	
	protected void finalizeCodeGenerator(CodesGenerator generator, Class<?> domainClass){
		//Do nothing.
	}
	
	@Override
	public void generateCode(CodesGenerator generator, List<Class<?>> domainClasses) {
		this.generateHeaderXMLLines(generator, domainClasses);
		super.generateCode(generator, domainClasses);
		this.createXMLFile(generator, domainClasses);
	}
	
	protected void generateHeaderXMLLines(CodesGenerator generator, List<Class<?>> domainClasses){
		this.setHeaderXMLLines(new ArrayList<String>());
		this.getHeaderXMLLines().add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		this.generateXMLHeader(this.getHeaderXMLLines());	
	}
	
	protected abstract void generateXMLHeader(List<String> headerXMLLines2);

	protected void createXMLFile(CodesGenerator generator, List<Class<?>> domainClasses){
		String xmlFileContent = this.getXStream().toXML(this.getRootXML());
		String directory = System.getProperty("user.dir") + super.getBaseSourcesDirectory();
		File directories = new File(directory);
		boolean directoriesCreated = directories.mkdirs();
		LOGGER.info("Directories [" + directory + "] was created: [" + directoriesCreated + "].");
		String fileName = this.getXmlFileName();
		File xmlClassFile = FileHelper.createFile(directory, fileName);
		try {
			Writer writer = new FileWriter(xmlClassFile);
			for(String headerLine: this.getHeaderXMLLines()){
				writer.write(headerLine);				
			}
			writer.write(xmlFileContent);
			writer.flush();
		} catch (IOException e) {}
	}

	/**
	 * @return the xStream
	 */
	protected XStream getXStream() {
		return xStream;
	}

	/**
	 * @param stream the xStream to set
	 */
	protected void setXStream(XStream stream) {
		xStream = stream;
	}

	public void addRootBean(Object rootXML) {
		this.setRootXML(rootXML);
	}

	/**
	 * @return the rootXML
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getRootXML() {
		return (T) rootXML;
	}

	/**
	 * @param rootXML the rootXML to set
	 */
	private <T> void setRootXML(T rootXML) {
		this.rootXML = rootXML;
	}

	/**
	 * @return the xmlFileName
	 */
	protected String getXmlFileName() {
		return xmlFileName;
	}

	/**
	 * @param xmlFileName the xmlFileName to set
	 */
	protected void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	/**
	 * @return the headerXMLLines
	 */
	private List<String> getHeaderXMLLines() {
		return headerXMLLines;
	}

	/**
	 * @param headerXMLLines the headerXMLLines to set
	 */
	private void setHeaderXMLLines(List<String> headerXMLLines) {
		this.headerXMLLines = headerXMLLines;
	}
}
