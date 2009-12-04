/**
 * 
 */
package com.angel.code.generator.data.impl.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("bean")
public class XMLBean {

	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String parent;
	@XStreamAsAttribute
	private String singleton;
	@XStreamAsAttribute
	@XStreamAlias("class")
	private String klass;

	@XStreamImplicit(itemFieldName = "property")
	private List<XMLPropertyValue> properties;

	/**
	 * 
	 */
	public XMLBean() {
		super();
		this.setProperties(new ArrayList<XMLPropertyValue>());
	}

	public XMLBean(String id, String klass) {
		this();
		this.setId(id);
		this.setKlass(klass);
	}
	
	public XMLBean(String id, String klass, String singleton) {
		this(id, klass);
		this.setSingleton(singleton);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the properties
	 */
	public List<XMLPropertyValue> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<XMLPropertyValue> properties) {
		this.properties = properties;
	}

	/**
	 * @return the klass
	 */
	public String getKlass() {
		return klass;
	}

	/**
	 * @param klass the klass to set
	 */
	public void setKlass(String klass) {
		this.klass = klass;
	}

	/**
	 * @return the singleton
	 */
	public String getSingleton() {
		return singleton;
	}

	/**
	 * @param singleton the singleton to set
	 */
	public void setSingleton(String singleton) {
		this.singleton = singleton;
	}

	public void addProperty(XMLPropertyValue property) {
		this.getProperties().add(property);
	}
}
