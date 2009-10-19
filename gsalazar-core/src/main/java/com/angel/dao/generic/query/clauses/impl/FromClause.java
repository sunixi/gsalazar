package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.QueryClause;
import com.angel.dao.generic.query.params.QueryFromParam;
import com.angel.dao.generic.query.params.impl.HQLSelectFromParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class FromClause implements QueryClause{
	
	private List<QueryFromParam> fromParams;
	
	public FromClause(){
		super();
		this.setFromParams(new ArrayList<QueryFromParam>());
	}
	
	/**
	 * @return the fromParams
	 */
	public List<QueryFromParam> getFromParams() {
		return fromParams;
	}

	/**
	 * @param fromParams the fromParams to set
	 */
	public void setFromParams(List<QueryFromParam> fromParams) {
		this.fromParams = fromParams;
	}
	
	protected boolean hasQueryFromParams(){
		return this.getFromParams().size() > 0 ;
	}

	public String createClause() {
		String clause = "";
		if(this.hasQueryFromParams()){
			clause = " from ";
			int total = this.getFromParams().size();
			for(int i = 0; i < total; i++){
				QueryFromParam fromParam = this.getFromParams().get(i);
				if(i > 0){
					if(fromParam.isJoin()){
						clause += " " + fromParam.getFrom();
					} else{
						clause += ", " + fromParam.getFrom();						
					}
				} else {
					if(fromParam.isJoin()){
						throw new RuntimeException("Cannot add a join in first from parameter.");
					} else{
						clause += fromParam.getFrom();						
					}
				}
			}
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ").trim();
	}

	public <T extends Object> FromClause add(Class<T> objectBean, String alias) {
		return this.addQueryFromParam(objectBean.getName(), alias);
	}
	
	protected FromClause addQueryFromParam(String beanName, String alias){
		return this.addQueryFromParam(beanName, alias, false);
	}
	
	protected FromClause addQueryJoinFromParam(String beanName, String alias){
		return this.addQueryFromParam(beanName, alias, true);
	}
	
	protected FromClause addQueryFromParam(String beanName, String alias, boolean isJoin){
		HQLSelectFromParam fromParam = new HQLSelectFromParam();
		fromParam.setAlias(alias);
		fromParam.setName(beanName);
		fromParam.setJoin(isJoin);
		this.getFromParams().add(fromParam);
		return this;
	}

	public FromClause add(String beanName, String alias) {
		return this.addQueryFromParam(beanName, alias);
	}
	
	public <T extends Object> FromClause innerJoin(Class<T> objectBean){
		return this.innerJoin(objectBean.getName(), StringHelper.EMPTY_STRING);
	}
	public <T extends Object> FromClause innerJoin(Class<T> objectBean, String alias){
		return this.innerJoin(objectBean.getName(), alias);
	}
	public FromClause innerJoin(String beanName){
		return this.innerJoin(beanName, StringHelper.EMPTY_STRING);
	}
	public FromClause innerJoin(String beanName, String alias){
		return this.innerJoin(beanName, StringHelper.EMPTY_STRING, alias);
	}
	public FromClause innerJoin(String beanName, String propertyBeanName, String alias){
		String bean = beanName + (StringHelper.isEmpty(propertyBeanName) ? "": "." + propertyBeanName);
		return this.addQueryJoinFromParam("inner join " + bean + " ", alias);
	}
	
	public <T extends Object> FromClause leftJoin(Class<T> objectBean){
		return this.leftJoin(objectBean.getName(), StringHelper.EMPTY_STRING);
	}
	public <T extends Object> FromClause leftJoin(Class<T> objectBean, String alias){
		return this.leftJoin(objectBean.getName(), alias);
	}
	public <T extends Object> FromClause leftJoin(String objectBean){
		return this.leftJoin(objectBean, StringHelper.EMPTY_STRING);
	}
	public <T extends Object> FromClause leftJoin(String objectBean, String alias){
		return this.addQueryJoinFromParam("left join " + objectBean + " ", alias);
	}
	
	public <T extends Object> FromClause rightJoin(String objectBean){
		return this.rightJoin(objectBean, StringHelper.EMPTY_STRING);
	}
	public <T extends Object> FromClause rightJoin(String objectBean, String propertyBeanName, String alias){
		String bean = objectBean + (StringHelper.isEmpty(propertyBeanName) ? "": "." + propertyBeanName);
		return this.addQueryJoinFromParam("right join " + bean + " ", alias);
	}
	public <T extends Object> FromClause rightJoin(String objectBean, String alias){
		return this.addQueryJoinFromParam("right join " + objectBean + " ", alias);
	}
	public <T extends Object> FromClause rightJoin(Class<T> objectBean){
		return this.rightJoin(objectBean.getName(), StringHelper.EMPTY_STRING);
	}
	public <T extends Object> FromClause rightJoin(Class<T> objectBean, String alias){
		return this.rightJoin(objectBean.getName(), alias);
	}
	
}
