package com.angel.dao.generic.query.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.dao.generic.query.Query;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.clauses.impl.FromClause;
import com.angel.dao.generic.query.clauses.impl.GroupByClause;
import com.angel.dao.generic.query.clauses.impl.HavingClause;
import com.angel.dao.generic.query.clauses.impl.OrderByClause;
import com.angel.dao.generic.query.clauses.impl.SelectClause;
import com.angel.dao.generic.query.clauses.impl.WhereClause;
import com.angel.dao.generic.query.params.QueryConditionParam;




/**
 * TODO Write comments. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLQueryBuilder implements QueryBuilder{

	private SelectClause selectClause;

	private FromClause fromClause;
	
	private WhereClause whereClause;
	
	private GroupByClause groupByClause;
	
	private HavingClause havingClause;
	
	private OrderByClause orderByClause;
	
	public HQLQueryBuilder(SelectClause selectClause, FromClause fromClause, WhereClause whereClause, GroupByClause groupByClause, HavingClause havingClause, OrderByClause orderByClause){
		super();
		this.setSelectClause(selectClause);
		this.setFromClause(fromClause);
		this.setWhereClause(whereClause);
		this.setGroupByClause(groupByClause);
		this.setHavingClause(havingClause);
		this.setOrderByClause(orderByClause);
	}
	
	public HQLQueryBuilder(SelectClause selectClause, FromClause fromClause, WhereClause whereClause, GroupByClause groupByClause, HavingClause havingClause){
		super();
		this.setSelectClause(selectClause);
		this.setFromClause(fromClause);
		this.setWhereClause(whereClause);
		this.setGroupByClause(groupByClause);
		this.setHavingClause(havingClause);
	}
	
	public HQLQueryBuilder(SelectClause selectClause, FromClause fromClause, WhereClause whereClause, GroupByClause groupByClause){
		super();
		this.setSelectClause(selectClause);
		this.setFromClause(fromClause);
		this.setWhereClause(whereClause);
		this.setGroupByClause(groupByClause);
	}
	
	public HQLQueryBuilder(SelectClause selectClause, FromClause fromClause, WhereClause whereClause){
		super();
		this.setSelectClause(selectClause);
		this.setFromClause(fromClause);
		this.setWhereClause(whereClause);
	}
	
	public HQLQueryBuilder(SelectClause selectClause, FromClause fromClause){
		super();
		this.setSelectClause(selectClause);
		this.setFromClause(fromClause);
	}
	
	public HQLQueryBuilder(FromClause fromClause){
		super();
		this.setFromClause(fromClause);
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
		if(this.getFromClause() == null){
			throw new RuntimeException("It must have a from clause");
		}
		String selectQuery = this.getSelectClause() != null ? this.getSelectClause().createClause() :"";
		String fromQuery = this.getFromClause().createClause();
		String whereQuery = this.getWhereClause() != null ? this.getWhereClause().createClause(): "";
		String groupByQuery = this.getGroupByClause() != null ? this.getGroupByClause().createClause() :"";
		String havingQuery = this.getHavingClause() != null ? this.getHavingClause().createClause(): "";
		String orderByQuery = this.getOrderByClause() != null ? this.getOrderByClause().createClause(): "";
		
		String query = selectQuery + fromQuery + whereQuery + groupByQuery + havingQuery + orderByQuery; 
		List<Object> params = new ArrayList<Object>();
		params.addAll(this.getWhereClause() != null ? this.getWhereClause().getParams(): new ArrayList<QueryConditionParam>());
		params.addAll(this.getHavingClause() != null ? this.getHavingClause().getParams(): new ArrayList<QueryConditionParam>());
		return new Query(query, params);
	}
}
