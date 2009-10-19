/**
 * 
 */
package com.angel.dao.generic.query.params;
/**
 * 
 * @author William
 *
 */
public enum ParamType {
	LIST_PARAMETER("LIST_PARAMETER"),
	OBJECT_PARAMETER("OBJECT_PARAMETER");
	
	private String name;
	
	private ParamType(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}