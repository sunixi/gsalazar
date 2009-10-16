package com.angel.dao.generic.query.sources.impl;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.sources.QuerySourceData;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class QuerySourceDataImpl implements QuerySourceData{
    
	private String name;
	private String alias;
	
	public QuerySourceDataImpl(Class<?> sourceData){
		this(sourceData.getName());
	}
	
	public QuerySourceDataImpl(Class<?> sourceData, String alias){
		this(sourceData.getName(), alias);
	}
	
	public QuerySourceDataImpl(String name){
		this(name, StringHelper.EMPTY_STRING);
	}
	
	public QuerySourceDataImpl(String name, String alias){
		super();
		this.setName(name);
		this.setAlias(alias);
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

	public String getSourceData() {
		return this.getName() + " " + this.getAlias();
	}
}
