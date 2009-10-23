/**
 * 
 */
package com.angel.dao.generic.query;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.params.QueryConditionParam;

/**
 * @author William
 *
 */
public class Query {

	public static final String PREFIX_PARAM_NAME = ":param_";
	private String query;
	private Object[] params;
	private List<QueryConditionParam> conditions;
	private int maxResult = 0;
	private int fetchSize = 0;
	
	public Query(String query, List<Object> params, List<QueryConditionParam> conditions, int maxResult, int fetchSize){
		this(query, params, conditions);
		this.maxResult = maxResult;
		this.fetchSize = fetchSize;
	}
	
	public Query(String query, List<Object> params, int maxResult, int fetchSize){
		this(query);
		this.maxResult = maxResult;
		this.fetchSize = fetchSize;
	}
	
	public Query(String query, List<Object> params, List<QueryConditionParam> conditions){
		this(query);
		this.params = params.toArray();
		this.conditions = conditions;
	}
	
	public Query(String query){
		super();
		this.query = query;
		this.params = new ArrayList<Object>().toArray();
		this.initializeQueryParams();
	}
	
	protected void initializeQueryParams(){
		int i = 0;
		while(StringHelper.containsCharacter(this.getQuery(), '?')){
			query = StringHelper.replaceFirst(this.getQuery(), "?", PREFIX_PARAM_NAME + i);
			i++;
		}
	}
	
	
	public boolean hasParams(){
		return this.getParams().length > 0;
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
	public Object[] getParams() {
		return params;
	}

	/**
	 * @return the maxResult
	 */
	public int getMaxResult() {
		return maxResult;
	}

	/**
	 * @param maxResult the maxResult to set
	 */
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	/**
	 * @return the fetchSize
	 */
	public int getFetchSize() {
		return fetchSize;
	}

	/**
	 * @param fetchSize the fetchSize to set
	 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * @return the conditions
	 */
	public List<QueryConditionParam> getConditions() {
		return conditions;
	}
}

