package com.angel.dao.generic.query.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.CollectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.Query;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.clauses.GroupByClause;
import com.angel.dao.generic.query.clauses.HavingClause;
import com.angel.dao.generic.query.clauses.OrderByClause;
import com.angel.dao.generic.query.clauses.SelectClause;
import com.angel.dao.generic.query.clauses.WhereClause;
import com.angel.dao.generic.query.clauses.impl.HQLFromClause;
import com.angel.dao.generic.query.clauses.impl.HQLGroupByClause;
import com.angel.dao.generic.query.clauses.impl.HQLHavingClause;
import com.angel.dao.generic.query.clauses.impl.HQLOrderByClause;
import com.angel.dao.generic.query.clauses.impl.HQLSelectClause;
import com.angel.dao.generic.query.clauses.impl.HQLWhereClause;
import com.angel.dao.generic.query.factory.ClauseFactory;
import com.angel.dao.generic.query.params.QueryConditionParam;




/**
 * TODO Write comments. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class QueryBuilderImpl implements QueryBuilder{

	private SelectClause selectClause;

	private FromClause fromClause;
	
	private WhereClause whereClause;
	
	private GroupByClause groupByClause;
	
	private HavingClause havingClause;
	
	private OrderByClause orderByClause;
	
	private int maxResult;
	
	private int fetchSize;
	
	public QueryBuilderImpl(ClauseFactory clauseFactory){
		super();
		this.setSelectClause(clauseFactory.createSelectClause());
		this.setFromClause(clauseFactory.createFromClause());
		this.setWhereClause(clauseFactory.createWhereClause());
		this.setGroupByClause(clauseFactory.createGroupByClause());
		this.setHavingClause(clauseFactory.createHavingClause());
		this.setOrderByClause(clauseFactory.createOrderByClause());
	}

	/**
	 * @return the selectClause
	 */
	public SelectClause getSelectClause() {
		return selectClause;
	}

	/**
	 * @param selectClause the selectClause to set
	 */
	public void setSelectClause(SelectClause selectClause) {
		this.selectClause = selectClause;
	}

	/**
	 * @return the whereClause
	 */
	public WhereClause getWhereClause() {
		return whereClause;
	}

	/**
	 * @param whereClause the whereClause to set
	 */
	public void setWhereClause(WhereClause whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * @return the fromClause
	 */
	public FromClause getFromClause() {
		return fromClause;
	}

	/**
	 * @param fromClause the fromClause to set
	 */
	public void setFromClause(FromClause fromClause) {
		this.fromClause = fromClause;
	}

	/**
	 * @return the groupByClause
	 */
	public GroupByClause getGroupByClause() {
		return groupByClause;
	}

	/**
	 * @param groupByClause the groupByClause to set
	 */
	public void setGroupByClause(GroupByClause groupByClause) {
		this.groupByClause = groupByClause;
	}

	/**
	 * @return the havingClause
	 */
	public HavingClause getHavingClause() {
		return havingClause;
	}

	/**
	 * @param havingClause the havingClause to set
	 */
	public void setHavingClause(HavingClause havingClause) {
		this.havingClause = havingClause;
	}

	/**
	 * @return the orderByClause
	 */
	public OrderByClause getOrderByClause() {
		return orderByClause;
	}

	/**
	 * @param orderByClause the orderByClause to set
	 */
	public void setOrderByClause(OrderByClause orderByClause) {
		this.orderByClause = orderByClause;
	}

	public Query buildQuery() {
		String selectQuery = this.getSelectClause().createClause();
		String fromQuery = this.getFromClause().createClause();
		String whereQuery = this.getWhereClause().createClause();
		String groupByQuery = this.getGroupByClause().createClause();
		String havingQuery = this.getHavingClause().createClause();
		String orderByQuery = this.getOrderByClause().createClause();
		
		String query = selectQuery + fromQuery + whereQuery + groupByQuery + havingQuery + orderByQuery; 
		List<Object> params = new ArrayList<Object>();
		params.addAll(CollectionHelper.convertGenericTo(this.getSelectClause().getSubQueriesParams()));
		List<QueryConditionParam> conditions = new ArrayList<QueryConditionParam>();
		if(this.getWhereClause() != null){
			for(Object o: this.getWhereClause().getParams()){
				params.add(o);
			}
			conditions.addAll(this.getWhereClause().getConditions());
		}
		query = StringHelper.replaceAllRecursively(query, "  ", " ");
		Query q = new Query(query.trim(), params, conditions);
		if(this.getMaxResult() > 0){
			q.setMaxResult(this.getMaxResult());
		}
		if(this.getFetchSize() > 0){
			q.setFetchSize(this.getFetchSize());
		}
		return q;
	}

	public FromClause buildFromClause() {
		this.setFromClause(new HQLFromClause());
		return this.getFromClause();
	}

	public GroupByClause buildGroupByClause() {
		this.setGroupByClause(new HQLGroupByClause());
		return this.getGroupByClause();
	}

	public HavingClause buildHavingClause() {
		this.setHavingClause(new HQLHavingClause());
		return this.getHavingClause();
	}

	public OrderByClause buildOrderByClause() {
		this.setOrderByClause(new HQLOrderByClause());
		return this.getOrderByClause();
	}

	public SelectClause buildSelectClause() {
		this.setSelectClause(new HQLSelectClause());
		return this.getSelectClause();
	}

	public WhereClause buildWhereClause() {
		this.setWhereClause(new HQLWhereClause());
		return this.getWhereClause();
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
}
