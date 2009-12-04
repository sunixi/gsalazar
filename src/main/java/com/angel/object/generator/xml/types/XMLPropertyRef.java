/**
 * 
 */
package com.angel.object.generator.xml.types;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("property")
public class XMLPropertyRef extends XMLPropertyValue{
	@XStreamAsAttribute
	private String ref;
		
	/**
	 * 
	 */
	public XMLPropertyRef() {
		super();
	}

	public XMLPropertyRef(String name) {
		super(name);
	}
	
	public XMLPropertyRef(String name, String ref) {
		this(name);
		this.setRef(ref);
	}

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}	
}
