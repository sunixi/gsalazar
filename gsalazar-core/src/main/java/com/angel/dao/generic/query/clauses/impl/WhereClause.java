package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.QueryClause;
import com.angel.dao.generic.query.condition.Condition;
import com.angel.dao.generic.query.params.ParamType;
import com.angel.dao.generic.query.params.QueryConditionParam;
import com.angel.dao.generic.query.params.impl.HQLWhereParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class WhereClause implements QueryClause{

	private static final Object[] EMPTY_PARAMS = new Object[0];
	
	private Condition condition;
	private boolean openGroup;

	private List<QueryConditionParam> conditions;

	public WhereClause(){
		super();
		this.setOpenGroup(false);
		this.setConditions(new ArrayList<QueryConditionParam>());
	}
	
	public String createClause() {
		String clause = "";
		if(this.hasQueryConditionParams()){
			clause = " where ";
			for(QueryConditionParam qcp: this.getConditions()){
				clause += qcp.queryCondition();
			}
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ");
	}
	
	protected boolean hasQueryConditionParams(){
		return this.getConditions().size() > 0;
	}
	
	public WhereClause or(){
		this.condition = Condition.OR;
		return this;
	}
	
	public WhereClause and(){
		this.condition = Condition.AND;
		return this;
	}
	
	public WhereClause grouped(WhereClause whereClause) {
		this.openGroup();
		for(QueryConditionParam qcp: whereClause.getConditions()){
			this.addQueryWhereParam(qcp);
		}
		this.closeGroup();
		return this;
	}

	public WhereClause equals(String propertyName, Object value) {
		return this.addQueryWhereParam(propertyName + " = ?", value);
	}
	
	public WhereClause equals(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam(alias + "." + propertyName + " = ?", value);
	}
	
	protected WhereClause addQueryWhereParam(String name, Object ...valuesParams){
		return this.addQueryWhereParam(name, ParamType.OBJECT_PARAMETER, valuesParams);
	}
	
	protected WhereClause addQueryWhereParam(String name, ParamType paramType, Object ...valuesParams){
		List<Object> values = new ArrayList<Object>();
		if(valuesParams != null){
			for(Object param: valuesParams){
				values.add(param);
			}
		}
		return this.addQueryWhereParam(new HQLWhereParam(this.getCondition(), name, this.isOpenGroup(), !this.isOpenGroup(), values, paramType));
	}
	
	protected WhereClause addQueryWhereParam(QueryConditionParam queryConditionParam){
		this.getConditions().add(queryConditionParam);
		if(this.getConditions().size() == 1){
			this.setCondition(Condition.AND);
		}
		return this;
	}
	
	public WhereClause equals(String alias, String propertyName, String value) {
		return this.addQueryWhereParam(alias + "." + propertyName + " = ?", value);
	}
	public WhereClause equals(String propertyName, String value) {
		return this.addQueryWhereParam(propertyName + " = ?", value);
	}

	public WhereClause notEquals(String propertyName, Object value) {
		return this.addQueryWhereParam(propertyName + " <> ?", value);
	}
	
	public WhereClause notEquals(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam(alias + "." + propertyName + " <> ?", value);
	}

	public <T extends Object> WhereClause notIn(String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(propertyName + " not in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> WhereClause notIn(String alias, String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(alias + "." + propertyName + " not in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> WhereClause in(String propertyName, T[] values) {
		return this.addQueryWhereParam(propertyName + " in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> WhereClause in(String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(propertyName + " in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> WhereClause in(String alias, String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(alias + "." + propertyName + " in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	
	public WhereClause isEmpty(String propertyName) {
		return this.addQueryWhereParam("is empty " + propertyName, new Object(){});
	}
	public WhereClause isEmpty(String alias, String propertyName) {
		return this.addQueryWhereParam("is empty " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	public WhereClause isNoyEmpty(String propertyName) {
		return this.addQueryWhereParam("is not empty " + propertyName, EMPTY_PARAMS);
	}
	public WhereClause isNoyEmpty(String alias, String propertyName) {
		return this.addQueryWhereParam("is not empty " + alias + "." + propertyName, EMPTY_PARAMS);
	}

	public WhereClause memberOf(String alias, String propertyName) {
		return this.addQueryWhereParam("member of " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	public WhereClause notMemberOf(String propertyName) {
		return this.addQueryWhereParam("not member of " + propertyName, EMPTY_PARAMS);
	}
	public WhereClause notMemberOf(String alias, String propertyName) {
		return this.addQueryWhereParam("not member of " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	
	public WhereClause isNotNull(String propertyName) {
		return this.addQueryWhereParam("is not null " + propertyName, EMPTY_PARAMS);
	}
	public WhereClause isNotNull(String alias, String propertyName) {
		return this.addQueryWhereParam("is not null " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	public WhereClause isNull(String propertyName) {
		return this.addQueryWhereParam("is null " + propertyName, EMPTY_PARAMS);
	}
	public WhereClause isNull(String alias, String propertyName) {
		return this.addQueryWhereParam("is null " + alias + "." + propertyName, EMPTY_PARAMS);
	}

	public WhereClause collectionSizeGT(String propertyName, long value) {
		return this.addQueryWhereParam("size(" + propertyName + ") > ?", value);
	}
	public WhereClause collectionSizeGT(String alias, String propertyName, long value) {
		return this.addQueryWhereParam("size(" + alias + "." + propertyName + ") > ?", value);
	}

	public WhereClause collectionSizeST(String propertyName, long value) {
		return this.addQueryWhereParam("size(" + propertyName + ") < ?", value);
	}
	public WhereClause collectionSizeST(String alias, String propertyName, long value) {
		return this.addQueryWhereParam("size(" + alias + "." + propertyName + ") < ?", value);
	}
	public WhereClause collectionSizeEQ(String propertyName, long value) {
		return this.addQueryWhereParam("size(" + propertyName + ") = ?", value);
	}
	public WhereClause collectionSizeEQ(String alias, String propertyName, long value) {
		return this.addQueryWhereParam("size(" + alias + "." + propertyName + ") = ?", value);
	}

	public WhereClause collectionMinElementGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + alias + "." + propertyName + ") > ?", value);
	}
	public WhereClause collectionMinElementST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + alias + "." + propertyName + ") < ?", value);
	}
	public WhereClause collectionMinElementEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + alias + "." + propertyName + ") = ?", value);
	}
	public WhereClause collectionMinElementGT(String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + propertyName + ") > ?", value);
	}
	public WhereClause collectionMinElementST(String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + propertyName + ") < ?", value);
	}
	public WhereClause collectionMinElementEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + propertyName + ") = ?", value);
	}
	
	public WhereClause collectionMaxElementGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + alias + "." + propertyName + ") > ?", value);
	}
	public WhereClause collectionMaxElementST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + alias + "." + propertyName + ") < ?", value);
	}
	public WhereClause collectionMaxElementEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + alias + "." + propertyName + ") = ?", value);
	}
	
	public WhereClause collectionMaxElementGT(String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + propertyName + ") > ?", value);
	}
	public WhereClause collectionMaxElementST(String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + propertyName + ") < ?", value);
	}
	public WhereClause collectionMaxElementEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + propertyName + ") = ?", value);
	}

	public WhereClause collectionMinIndexGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + alias + "." + propertyName + ") > ?", value);
	}
	public WhereClause collectionMinIndexST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + alias + "." + propertyName + ") > ?", value);
	}
	public WhereClause collectionMinIndexEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + alias + "." + propertyName + ") = ?", value);
	}
	public WhereClause collectionMinIndexGT(String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + propertyName + ") > ?", value);
	}
	public WhereClause collectionMinIndexST(String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + propertyName + ") > ?", value);
	}
	public WhereClause collectionMinIndexEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + propertyName + ") = ?", value);
	}
	
	public WhereClause collectionMaxIndexGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + alias + "." + propertyName + ") > ?", value);
	}
	public WhereClause collectionMaxIndexST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + alias + "." + propertyName + ") < ?", value);
	}
	public WhereClause collectionMaxIndexEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + alias + "." + propertyName + ") = ?", value);
	}
	
	public WhereClause collectionMaxIndexGT(String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + propertyName + ") > ?", value);
	}
	public WhereClause collectionMaxIndexST(String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + propertyName + ") < ?", value);
	}
	public WhereClause collectionMaxIndexEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + propertyName + ") = ?", value);
	}
	
	public WhereClause between(String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(propertyName + " between ? and ?", minValue, maxValue);
	}
	public WhereClause between(String alias, String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(alias + "." + propertyName + " between ? and ?", minValue, maxValue);
	}
	public WhereClause notBetween(String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(propertyName + " not between ? and ?", minValue, maxValue);
	}
	public WhereClause notBetween(String alias, String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(alias + "." + propertyName + " not between ? and ?", minValue, maxValue);
	}

	public WhereClause like(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '%?%'", value);
	}
	public WhereClause like(String propertyName , String value){
		return this.addQueryWhereParam(propertyName + " like '%?%'", value);
	}
	
	public WhereClause likeLeft(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '%?'", value);
	}
	public WhereClause likeLeft(String propertyName, String value){
		return this.addQueryWhereParam(propertyName + " like '%?'", value);
	}
	
	public WhereClause likeRight(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '?%'", value);
	}
	public WhereClause likeRight(String propertyName, String value){
		return this.addQueryWhereParam(propertyName + " like '?%'", value);
	}
	
	public WhereClause likeFull(String propertyName, String value){
		return this.addQueryWhereParam(propertyName + " like '%'", value.replaceAll(" ", "%"));
	}
	public WhereClause likeFull(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '%'", value.replaceAll(" ", "%"));
	}

	public <T extends Object> WhereClause inElements(String alias, String propertyName, Collection<T> collection){
		/** select mother from Cat as mother, Cat as kit where kit in elements(foo.kittens).*/
		return this.addQueryWhereParam(alias + "." + propertyName + " in elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> WhereClause inElements(String propertyName, Collection<T> collection){
		/** select mother from Cat as mother, Cat as kit where kit in elements(foo.kittens).*/
		return this.addQueryWhereParam(propertyName + " in elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> WhereClause someElements(String alias, String propertyName, Collection<T> collection){
		/** select p from NameList list, Person p where p.name = some elements(list.names).*/
		return this.addQueryWhereParam(alias + "." + propertyName + " = some elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> WhereClause someElements(String propertyName, Collection<T> collection){
		/** select p from NameList list, Person p where p.name = some elements(list.names).*/
		return this.addQueryWhereParam(propertyName + " = some elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> WhereClause existElements(String alias, String propertyName){
		/** from Cat cat where exists elements(cat.kittens).*/
		return this.addQueryWhereParam("exists elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> WhereClause existElements(String propertyName){
		/** from Cat cat where exists elements(cat.kittens).*/
		return this.addQueryWhereParam("exists elements(" + propertyName + ")");
	}

	public <T extends Object> WhereClause allElementsGT(String alias, String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? > all elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> WhereClause allElementsGT(String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? > all elements(" + propertyName + ")");
	}
	
	public <T extends Object> WhereClause allElementsST(String alias, String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? < all elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> WhereClause allElementsST(String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? < all elements(" + propertyName + ")", value);
	}
	public <T extends Object> WhereClause allElementsEQ(String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? = all elements(" + propertyName + ")");
	}
	public <T extends Object> WhereClause allElementsEQ(String alias, String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? = all elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> WhereClause inIndices(String alias, String propertyName, String value){
		/** from Show show where 'fizard' in indices(show.acts). */
		return this.addQueryWhereParam(" ? in indices(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> WhereClause inIndices(String propertyName, String value){
		return this.inIndices(StringHelper.EMPTY_STRING, propertyName, value);
	}

	/**
	 * @return the condition
	 */
	protected Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	protected void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public WhereClause someElementsValuesEQ(String alias, String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(alias + "." + propertyName + " = some elements( ? )",ParamType.LIST_PARAMETER,  values);
	}
	public WhereClause someElementsValuesEQ(String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(propertyName + " = some elements( ? )", ParamType.LIST_PARAMETER, values);
	}

	public WhereClause allElementsValuesEQ(String beanName, String alias, String propertyName) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(beanName + " = all elements(" + alias + "." + propertyName + ")");
	}
	public WhereClause allElementsValuesEQ(String beanName, String propertyName) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(beanName + " = all elements(" + propertyName + ")");
	}
	
	public WhereClause allElementsValuesEQ(String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(propertyName + " = all elements( ? )", ParamType.LIST_PARAMETER, values);
	}	
	public WhereClause allElementsValuesEQ(String alias, String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam( alias + "." + propertyName + " = all elements( ? )", ParamType.LIST_PARAMETER, values);
	}	

	/**
	 * @return the conditions
	 */
	public List<QueryConditionParam> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	protected void setConditions(List<QueryConditionParam> conditions) {
		this.conditions = conditions;
	}

	/**
	 * @return the openGroup
	 */
	protected boolean isOpenGroup() {
		return openGroup;
	}

	/**
	 * @param openGroup the openGroup to set
	 */
	protected void setOpenGroup(boolean openGroup) {
		this.openGroup = openGroup;
	}
	
	public WhereClause openGroup(){
		this.setOpenGroup(true);
		return this;
	}
	
	public WhereClause closeGroup(){
		this.setOpenGroup(false);
		return this;
	}

	/**
	 * @return the params
	 */
	public List<Object> getParams() {
		List<Object> params = new ArrayList<Object>();
		for(QueryConditionParam qcp: this.getConditions()){
			params.addAll(qcp.getValues());
		}
		return params;
	}
}
