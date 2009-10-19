package com.angel.dao.generic.query.params.impl;

import com.angel.dao.generic.query.params.QueryFromParam;
import com.angel.dao.generic.query.params.QuerySelectParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLSelectFromParam implements QuerySelectParam, QueryFromParam{
    
	public static final String DEFAULT_EQUALS_OPERATOR = " = ";
	
	private String name;
	private String alias = "";
	private boolean isJoin;

	public HQLSelectFromParam(){
		super();
		this.setJoin(false);
	}

	public HQLSelectFromParam(String name){
		this();
		this.setName(name);
	}
	
	public HQLSelectFromParam(String name, String alias){
		this(name);
		this.setAlias(alias);
	}

	public HQLSelectFromParam(String name, String alias, boolean isJoin){
		this(name, alias);
		this.setJoin(isJoin);
	}

	public String getSelection() {
		return this.getName() + " " + this.getAlias();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}


	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getFrom() {
		return this.getSelection();
	}

	/**
	 * @return the isJoin
	 */
	public boolean isJoin() {
		return isJoin;
	}

	/**
	 * @param isJoin the isJoin to set
	 */
	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

}
