/**
 * 
 */
package com.angel.dao.generic.query.condition;
/**
 * 
 * @author William
 *
 */
public enum Condition {
	OR(" OR "),
	AND(" AND ");
	
	private String name;
	
	private Condition(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}