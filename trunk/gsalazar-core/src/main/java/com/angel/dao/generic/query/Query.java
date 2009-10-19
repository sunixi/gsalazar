/**
 * 
 */
package com.angel.dao.generic.query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author William
 *
 */
public class Query {

	private String query;
	private List<Object> params;
	
	public Query(String query, List<Object> params){
		super();
		this.query = query;
		this.params = params;
	}
	
	public Query(String query){
		super();
		this.query = query;
		this.params = new ArrayList<Object>();
	}
	
	public boolean hasParams(){
		return this.getParams().size() > 0;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return the params
	 */
	public List<Object> getParams() {
		return params;
	}
}
