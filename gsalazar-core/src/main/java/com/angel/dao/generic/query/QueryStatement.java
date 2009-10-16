package com.angel.dao.generic.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.operators.QueryOperator;
import com.angel.dao.generic.query.params.QueryConditionParam;
import com.angel.dao.generic.query.params.QuerySelectionParam;
import com.angel.dao.generic.query.params.QuerySortedParam;
import com.angel.dao.generic.query.params.impl.QueryPropertyParam;
import com.angel.dao.generic.query.sources.QuerySourceData;
import com.angel.dao.generic.query.sources.impl.QuerySourceDataImpl;

/**
 * TODO Write comments.
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public abstract class QueryStatement {

	//private static final Logger LOGGER = Logger.getLogger(QueryStatement.class);
	
	public static final Boolean NON_OPEN_TAG_PARAM =  false;
	public static final Boolean OPEN_TAG_PARAM =  true;
	
	public static final String OR_CONDITION = " and ";
	public static final String AND_CONDITION = " or ";
	
	public static final Boolean NON_CLOSE_TAG_PARAM =  false;
	public static final Boolean CLOSE_TAG_PARAM =  true;
	
	/** Used to add an order param in descendent order type. */
	public static final String DESC_ORDER_TYPE =  " desc ";
	
	/** Used to add an order param in ascendent order type. */
	public static final String ASC_ORDER_TYPE =  " asc ";

	/** Query parameters to build selection properties.*/
	private List<QuerySelectionParam> selectionParams = new ArrayList<QuerySelectionParam>();
	
	/** Sources data to build query.*/
	private List<QuerySourceData> sourcesData;

	/** Query parameters to build query filter. */
	private List<QueryConditionParam> conditionParams;

	/** Order parameters to set a specific order to query filter. */
	private List<QuerySortedParam> sortedParams;

	private long openTags = 0;
	
	private long closeTags = 0;
	
	private static Map<String, String> ORDER_TYPES = new HashMap<String, String>();

	static {
		ORDER_TYPES.put(DESC_ORDER_TYPE, " desc ");
		ORDER_TYPES.put(ASC_ORDER_TYPE, " asc ");
	}
	
	public QueryStatement(){
		super();
		this.setSelectionParams(new ArrayList<QuerySelectionParam>());
		this.setSourcesData(new ArrayList<QuerySourceData>());
		this.setConditionParams(new ArrayList<QueryConditionParam>());
		this.setSortedParams(new ArrayList<QuerySortedParam>());
	}
	
	/*
	public void addAllParametersBewteenTags(Map<String, String> parameters, String condition){
		int current = 1;
		int total = parameters.keySet().size();
		for(String value: parameters.keySet()){
			if(current == 1 && current != total){
				this.addOpenTagParam(parameters.get(value), value, condition);
			} else {
				if(current == total && current != 1){
					this.addCloseTagParam(parameters.get(value), value, condition);
				} else {
					this.addParam(parameters.get(value), value, condition);        			
				}
			}
			current++;
		}
	}*/
	
	public QueryStatement addSourceData(QuerySourceData sourceData){
		this.getSourcesData().add(sourceData);
		return this;
	}
	public QueryStatement addSourceData(String name, String alias){
		return this.addSourceData(new QuerySourceDataImpl(name, alias));
	}
	public QueryStatement addSourceData(String name){
		return this.addSourceData(new QuerySourceDataImpl(name));
	}
	public QueryStatement addSourceData(Class<?> sourceData){
		return this.addSourceData(new QuerySourceDataImpl(sourceData));
	}
	public QueryStatement addSourceData(Class<?> sourceData, String alias){
		return this.addSourceData(new QuerySourceDataImpl(sourceData,alias));
	}

	public QueryStatement addSelectionParam(QuerySelectionParam selectionParam){
		this.getSelectionParams().add(selectionParam);
		return this;
	}
	public QueryStatement addSelectionParam(String name){
		QueryPropertyParam selectionParam = new QueryPropertyParam();
		selectionParam.setName(name);
		return this.addSelectionParam(selectionParam);
	}
	public QueryStatement addSelectionParam(String name, String alias){
		QueryPropertyParam selectionParam = new QueryPropertyParam();
		selectionParam.setName(name);
		selectionParam.setAlias(alias);
		return this.addSelectionParam(selectionParam);
	}
	
	public QueryStatement addConditionParam(QueryConditionParam queryConditionParam){
		this.getConditionParams().add(queryConditionParam);
		return this;
	}
	public QueryStatement addConditionParam(String name, Object value, String condition, QueryOperator operator, boolean openTag, boolean closeTag){
		QueryPropertyParam queryConditionParam = new QueryPropertyParam();
		queryConditionParam.setName(name);
		queryConditionParam.setValue(value);
		queryConditionParam.setCondition(condition);
		if(operator != null){
			queryConditionParam.setOperator(operator);
		}
		queryConditionParam.setOpenTag(openTag);
		queryConditionParam.setCloseTag(closeTag);
		return this.addConditionParam(queryConditionParam);
	}
	public QueryStatement addConditionParam(String name, Object value, String condition, QueryOperator operator, boolean openTag){
		return this.addConditionParam(name, value, condition, operator, openTag, false);
	}
	public QueryStatement addConditionParam(String name, Object value, String condition, QueryOperator operator){
		return this.addConditionParam(name, value, condition, operator, false, false);
	}
	public QueryStatement addConditionParam(String name, Object value, String condition){
		return this.addConditionParam(name, value, condition, null, false, false);
	}
	public QueryStatement addConditionParam(String name, Object value, QueryOperator operator){
		return this.addConditionParam(name, value, AND_CONDITION, operator, false, false);
	}
	public QueryStatement addConditionParam(String name, Object value){
		return this.addConditionParam(name, value, AND_CONDITION, null, false, false);
	}
	
	
	/**getName();

	public String getValue();

	public String getCondition();

	public String getOperator();

	public boolean isOpenTag();

	public boolean isCloseTag();*/

	public void clearParameters(){
		this.clearSelectionParams();
		this.clearConditionParams();
		this.clearSourcesData();
		this.clearSortedParams();
	}
	
	public void clearConditionParams(){
		this.getConditionParams().clear();
	}
	
	public void clearSelectionParams(){
		this.getSelectionParams().clear();
	}
	
	public void clearSourcesData(){
		this.getSourcesData().clear();
	}
	
	public void clearSortedParams(){
		this.getSortedParams().clear();
	}

	/**
	 * @param sourcesData the sourcesData to set
	 */
	protected void setSourcesData(List<QuerySourceData> sourcesData) {
		this.sourcesData = sourcesData;
	}

	/**
	 * @param conditionParams the conditionParams to set
	 */
	protected void setConditionParams(List<QueryConditionParam> conditionParams) {
		this.conditionParams = conditionParams;
	}

	/**
	 * @param sortedParams the sortedParams to set
	 */
	protected void setSortedParams(List<QuerySortedParam> sortedParams) {
		this.sortedParams = sortedParams;
	}

	/**
	 * @return the selectionParams
	 */
	public List<QuerySelectionParam> getSelectionParams() {
		return selectionParams;
	}

	/**
	 * @param selectionParams the selectionParams to set
	 */
	protected void setSelectionParams(List<QuerySelectionParam> selectionParams) {
		this.selectionParams = selectionParams;
	}

	/**
	 * @return the sourcesData
	 */
	public List<QuerySourceData> getSourcesData() {
		return sourcesData;
	}

	/**
	 * @return the conditionParams
	 */
	public List<QueryConditionParam> getConditionParams() {
		return conditionParams;
	}

	/**
	 * @return the sortedParams
	 */
	public List<QuerySortedParam> getSortedParams() {
		return sortedParams;
	}

	/**
	 * @return the openTags
	 */
	protected long getOpenTags() {
		return openTags;
	}

	/**
	 * @param openTags the openTags to set
	 */
	protected void setOpenTags(long openTags) {
		this.openTags = openTags;
	}

	/**
	 * @return the closeTags
	 */
	protected long getCloseTags() {
		return closeTags;
	}

	/**
	 * @param closeTags the closeTags to set
	 */
	protected void setCloseTags(long closeTags) {
		this.closeTags = closeTags;
	}

	public String buildQuery() {
		String selectionQuery = this.buildSelectionQuery();
		String sourceDataQuery = this.buildSourcesDataQuery();
		String conditionQuery = this.buildConditionQuery();
		String sortedQuery = this.buildSortedQuery();
		String query =	selectionQuery + StringHelper.EMPTY_STRING +
						sourceDataQuery + StringHelper.EMPTY_STRING +
						conditionQuery + StringHelper.EMPTY_STRING +
						sortedQuery + StringHelper.EMPTY_STRING;
		return query;
	}
	
	protected String buildSelectionQuery(){
		String query = "select * ";
		if(this.hasSelectionParams()){
			query = "select \n";
			int current = 1;
			int total = this.getSelectionParams().size();
			for(QuerySelectionParam qsp: this.getSelectionParams()){
				if(current == 1 && current != total){
					query += qsp.getSelection() + ", ";
					query += "\n";
				} else {
					if(current == total && current != 1){
						query += qsp.getSelection();
					} else {       			
						query += qsp.getSelection() + ", ";
						query += "\n";
					}
				}
				current++;
			}
		}
		return query;
	}
	
	protected String buildSourcesDataQuery(){
		String query = "";
		if(this.hasSourceData()){
			query = " \n from \n";
			int current = 1;
			int total = this.getSourcesData().size();
			for(QuerySourceData qsd: this.getSourcesData()){
				if(current == 1 && current != total){
					query += qsd.getSourceData() + ", ";
				} else {
					if(current == total && current != 1){
						query += qsd.getSourceData();
					} else {       			
						query += qsd.getSourceData() + ", ";
					}
				}
				query += "\n";
				current++;
			}
		}
		return query;
	}
	
	protected String buildConditionQuery(){
		String query = "";
		if(this.hasConditionParams()){
			query = " where ";
			int current = 1;
			int total = this.getConditionParams().size();
			for(QueryConditionParam qcp: this.getConditionParams()){
				if(current == 1 && current != total){
					query += qcp.getQueryCondition();
				} else {
					if(current == total && current != 1){
						query += qcp.getCondition() + " " + qcp.getQueryCondition();
					} else {
						if(current == total && total > 1){
							query += qcp.getCondition() + " " + qcp.getQueryCondition();
						} else {
							if(total == 1){
								query += " " + qcp.getQueryCondition();
							} else {
								query += qcp.getCondition() + " " + qcp.getQueryCondition();
							}
						}
					}
				}
				current++;
			}
		}
		return query;
	}
	
	protected String buildSortedQuery(){
		String query = "";
		if(this.hasSortedParams()){
			query = " order by ";
			int current = 1;
			int total = this.getSortedParams().size();
			for(QuerySortedParam qsp: this.getSortedParams()){
				if(current == 1 && current != total){
					query += qsp.getSortedParam();
				} else {
					if(current == total && current != 1){
						query += qsp.getSortedParam();
					} else {       			
						query += qsp.getSortedParam() + ", ";
					}
				}
				current++;
			}
		}
		return query;
	}
	
	public boolean hasSelectionParams(){
		return this.getSelectionParams().size() > 0;
	}
	
	public boolean hasConditionParams(){
		return this.getConditionParams().size() > 0;
	}
	
	public boolean hasSourceData(){
		return this.getSourcesData().size() > 0;
	}
	
	public boolean hasSortedParams(){
		return this.getSortedParams().size() > 0;
	}
}
