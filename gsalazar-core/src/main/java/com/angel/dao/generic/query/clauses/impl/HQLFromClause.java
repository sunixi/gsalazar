package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.params.QueryFromParam;
import com.angel.dao.generic.query.params.impl.HQLSelectFromParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLFromClause implements FromClause{
	
	private List<QueryFromParam> fromParams;
	
	public HQLFromClause(){
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
	
	public boolean hasQueryFromParams(){
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
		return StringHelper.replaceAllRecursively(clause, "  ", " ");
	}

	public <T extends Object> HQLFromClause add(Class<T> objectBean, String alias) {
		return this.addQueryFromParam(objectBean.getName(), alias);
	}
	
	public HQLFromClause addQueryFromParam(String beanName, String alias){
		return this.addQueryFromParam(beanName, alias, false);
	}
	
	public HQLFromClause addQueryJoinFromParam(String beanName, String alias){
		return this.addQueryFromParam(beanName, alias, true);
	}
	
	protected HQLFromClause addQueryFromParam(String beanName, String alias, boolean isJoin){
		HQLSelectFromParam fromParam = new HQLSelectFromParam();
		fromParam.setAlias(alias);
		fromParam.setName(beanName);
		fromParam.setJoin(isJoin);
		this.getFromParams().add(fromParam);
		return this;
	}

	public HQLFromClause add(String beanName, String alias) {
		return this.addQueryFromParam(beanName, alias);
	}
	
	public <T extends Object> HQLFromClause innerJoin(Class<T> objectBean){
		return this.innerJoin(objectBean.getName(), StringHelper.EMPTY_STRING);
	}
	public <T extends Object> HQLFromClause innerJoin(Class<T> objectBean, String alias){
		return this.innerJoin(objectBean.getName(), alias);
	}
	public HQLFromClause innerJoin(String beanName){
		return this.innerJoin(beanName, StringHelper.EMPTY_STRING);
	}
	public HQLFromClause innerJoin(String beanName, String alias){
		return this.innerJoin(beanName, StringHelper.EMPTY_STRING, alias);
	}
	public HQLFromClause innerJoin(String beanName, String propertyBeanName, String alias){
		String bean = beanName + (StringHelper.isEmpty(propertyBeanName) ? "": "." + propertyBeanName);
		return this.addQueryJoinFromParam("inner join " + bean + " ", alias);
	}
	
	public <T extends Object> HQLFromClause leftJoin(Class<T> objectBean){
		return this.leftJoin(objectBean.getName(), StringHelper.EMPTY_STRING);
	}
	public <T extends Object> HQLFromClause leftJoin(Class<T> objectBean, String alias){
		return this.leftJoin(objectBean.getName(), alias);
	}
	public <T extends Object> HQLFromClause leftJoin(String objectBean){
		return this.leftJoin(objectBean, StringHelper.EMPTY_STRING);
	}
	public <T extends Object> HQLFromClause leftJoin(String objectBean, String alias){
		return this.addQueryJoinFromParam("left join " + objectBean + " ", alias);
	}
	
	public <T extends Object> HQLFromClause rightJoin(String objectBean){
		return this.rightJoin(objectBean, StringHelper.EMPTY_STRING);
	}
	public <T extends Object> HQLFromClause rightJoin(String objectBean, String propertyBeanName, String alias){
		String bean = objectBean + (StringHelper.isEmpty(propertyBeanName) ? "": "." + propertyBeanName);
		return this.addQueryJoinFromParam("right join " + bean + " ", alias);
	}
	public <T extends Object> HQLFromClause rightJoin(String objectBean, String alias){
		return this.addQueryJoinFromParam("right join " + objectBean + " ", alias);
	}
	public <T extends Object> HQLFromClause rightJoin(Class<T> objectBean){
		return this.rightJoin(objectBean.getName(), StringHelper.EMPTY_STRING);
	}
	public <T extends Object> HQLFromClause rightJoin(Class<T> objectBean, String alias){
		return this.rightJoin(objectBean.getName(), alias);
	}
	
}
