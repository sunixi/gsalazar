package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.common.helpers.CollectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.Query;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.clauses.WhereClause;
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
public class HQLWhereClause implements WhereClause{

	private static final Object[] EMPTY_PARAMS = new Object[0];
	
	private Condition condition;
	private boolean openGroup;

	private List<QueryConditionParam> conditions;

	public HQLWhereClause(){
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
	
	public boolean hasQueryConditionParams(){
		return this.getConditions().size() > 0;
	}
	
	public HQLWhereClause or(){
		this.condition = Condition.OR;
		return this;
	}
	
	public HQLWhereClause and(){
		this.condition = Condition.AND;
		return this;
	}
	
	public HQLWhereClause equals(String propertyName, Object value) {
		return this.addQueryWhereParam(propertyName + " = ?", value);
	}
	
	public HQLWhereClause equals(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam(alias + "." + propertyName + " = ?", value);
	}
	
	public HQLWhereClause addQueryWhereParam(String name, Object ...valuesParams){
		return this.addQueryWhereParam(name, ParamType.OBJECT_PARAMETER, valuesParams);
	}
	
	protected HQLWhereClause addQueryWhereParam(String name, ParamType paramType, Object ...valuesParams){
		List<Object> values = new ArrayList<Object>();
		if(valuesParams != null){
			for(Object param: valuesParams){
				values.add(param);
			}
		}
		return this.addQueryWhereParam(new HQLWhereParam(this.getCondition(), name, this.isOpenGroup(), !this.isOpenGroup(), values, paramType));
	}
	
	protected HQLWhereClause addQueryWhereParam(QueryConditionParam queryConditionParam){
		this.getConditions().add(queryConditionParam);
		if(this.getConditions().size() == 1){
			this.setCondition(Condition.AND);
		}
		return this;
	}
	
	public HQLWhereClause equals(String alias, String propertyName, String value) {
		return this.addQueryWhereParam(alias + "." + propertyName + " = ?", value);
	}
	public HQLWhereClause equals(String propertyName, String value) {
		return this.addQueryWhereParam(propertyName + " = ?", value);
	}
	
	public HQLWhereClause equalsAlias(String alias, String propertyName, String beanAlias) {
		return this.addQueryWhereParam(alias + "." + propertyName + " = " + beanAlias);
	}
	
	public HQLWhereClause equalsAlias(String propertyName, String beanAlias) {
		return this.addQueryWhereParam(propertyName + " = " + beanAlias);
	}

	public HQLWhereClause notEquals(String propertyName, Object value) {
		return this.addQueryWhereParam(propertyName + " <> ?", value);
	}
	
	public HQLWhereClause notEquals(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam(alias + "." + propertyName + " <> ?", value);
	}

	public <T extends Object> HQLWhereClause notIn(String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(propertyName + " not in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> HQLWhereClause notIn(String alias, String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(alias + "." + propertyName + " not in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> HQLWhereClause in(String propertyName, T[] values) {
		return this.addQueryWhereParam(propertyName + " in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> HQLWhereClause in(String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(propertyName + " in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	public <T extends Object> HQLWhereClause in(String alias, String propertyName, Collection<T> values) {
		return this.addQueryWhereParam(alias + "." + propertyName + " in ( ? )", ParamType.LIST_PARAMETER, values);
	}
	
	public HQLWhereClause isEmpty(String propertyName) {
		return this.addQueryWhereParam("is empty " + propertyName, new Object(){});
	}
	public HQLWhereClause isEmpty(String alias, String propertyName) {
		return this.addQueryWhereParam("is empty " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause isNoyEmpty(String propertyName) {
		return this.addQueryWhereParam("is not empty " + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause isNoyEmpty(String alias, String propertyName) {
		return this.addQueryWhereParam("is not empty " + alias + "." + propertyName, EMPTY_PARAMS);
	}

	public HQLWhereClause memberOf(String alias, String propertyName) {
		return this.addQueryWhereParam("member of " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause notMemberOf(String propertyName) {
		return this.addQueryWhereParam("not member of " + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause notMemberOf(String alias, String propertyName) {
		return this.addQueryWhereParam("not member of " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	
	public HQLWhereClause isNotNull(String propertyName) {
		return this.addQueryWhereParam("is not null " + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause isNotNull(String alias, String propertyName) {
		return this.addQueryWhereParam("is not null " + alias + "." + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause isNull(String propertyName) {
		return this.addQueryWhereParam("is null " + propertyName, EMPTY_PARAMS);
	}
	public HQLWhereClause isNull(String alias, String propertyName) {
		return this.addQueryWhereParam("is null " + alias + "." + propertyName, EMPTY_PARAMS);
	}

	public HQLWhereClause collectionSizeGT(String propertyName, long value) {
		return this.addQueryWhereParam("size(" + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionSizeGT(String alias, String propertyName, long value) {
		return this.addQueryWhereParam("size(" + alias + "." + propertyName + ") > ?", value);
	}

	public HQLWhereClause collectionSizeST(String propertyName, long value) {
		return this.addQueryWhereParam("size(" + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionSizeST(String alias, String propertyName, long value) {
		return this.addQueryWhereParam("size(" + alias + "." + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionSizeEQ(String propertyName, long value) {
		return this.addQueryWhereParam("size(" + propertyName + ") = ?", value);
	}
	public HQLWhereClause collectionSizeEQ(String alias, String propertyName, long value) {
		return this.addQueryWhereParam("size(" + alias + "." + propertyName + ") = ?", value);
	}

	public HQLWhereClause collectionMinElementGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + alias + "." + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMinElementST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + alias + "." + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionMinElementEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + alias + "." + propertyName + ") = ?", value);
	}
	public HQLWhereClause collectionMinElementGT(String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMinElementST(String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionMinElementEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("minelement(" + propertyName + ") = ?", value);
	}
	
	public HQLWhereClause collectionMaxElementGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + alias + "." + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMaxElementST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + alias + "." + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionMaxElementEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + alias + "." + propertyName + ") = ?", value);
	}
	
	public HQLWhereClause collectionMaxElementGT(String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMaxElementST(String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionMaxElementEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("maxelement(" + propertyName + ") = ?", value);
	}

	public HQLWhereClause collectionMinIndexGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + alias + "." + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMinIndexST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + alias + "." + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMinIndexEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + alias + "." + propertyName + ") = ?", value);
	}
	public HQLWhereClause collectionMinIndexGT(String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMinIndexST(String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMinIndexEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("minindex(" + propertyName + ") = ?", value);
	}
	
	public HQLWhereClause collectionMaxIndexGT(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + alias + "." + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMaxIndexST(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + alias + "." + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionMaxIndexEQ(String alias, String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + alias + "." + propertyName + ") = ?", value);
	}
	
	public HQLWhereClause collectionMaxIndexGT(String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + propertyName + ") > ?", value);
	}
	public HQLWhereClause collectionMaxIndexST(String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + propertyName + ") < ?", value);
	}
	public HQLWhereClause collectionMaxIndexEQ(String propertyName, Object value) {
		return this.addQueryWhereParam("maxindex(" + propertyName + ") = ?", value);
	}
	
	public HQLWhereClause between(String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(propertyName + " between ? and ?", minValue, maxValue);
	}
	public HQLWhereClause between(String alias, String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(alias + "." + propertyName + " between ? and ?", minValue, maxValue);
	}
	public HQLWhereClause notBetween(String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(propertyName + " not between ? and ?", minValue, maxValue);
	}
	public HQLWhereClause notBetween(String alias, String propertyName, Object minValue, Object maxValue) {
		return this.addQueryWhereParam(alias + "." + propertyName + " not between ? and ?", minValue, maxValue);
	}

	public HQLWhereClause like(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '%?%'", value);
	}
	public HQLWhereClause like(String propertyName , String value){
		return this.addQueryWhereParam(propertyName + " like '%?%'", value);
	}
	
	public HQLWhereClause likeLeft(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '%?'", value);
	}
	public HQLWhereClause likeLeft(String propertyName, String value){
		return this.addQueryWhereParam(propertyName + " like '%?'", value);
	}
	
	public HQLWhereClause likeRight(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '?%'", value);
	}
	public HQLWhereClause likeRight(String propertyName, String value){
		return this.addQueryWhereParam(propertyName + " like '?%'", value);
	}
	
	public HQLWhereClause likeFull(String propertyName, String value){
		return this.addQueryWhereParam(propertyName + " like '%'", value.replaceAll(" ", "%"));
	}
	public HQLWhereClause likeFull(String alias, String propertyName, String value){
		return this.addQueryWhereParam(alias + "." + propertyName + " like '%'", value.replaceAll(" ", "%"));
	}

	public <T extends Object> HQLWhereClause inElements(String alias, String propertyName, Collection<T> collection){
		/** select mother from Cat as mother, Cat as kit where kit in elements(foo.kittens).*/
		return this.addQueryWhereParam(alias + "." + propertyName + " in elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> HQLWhereClause inElements(String propertyName, Collection<T> collection){
		/** select mother from Cat as mother, Cat as kit where kit in elements(foo.kittens).*/
		return this.addQueryWhereParam(propertyName + " in elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> HQLWhereClause someElements(String alias, String propertyName, Collection<T> collection){
		/** select p from NameList list, Person p where p.name = some elements(list.names).*/
		return this.addQueryWhereParam(alias + "." + propertyName + " = some elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> HQLWhereClause someElements(String propertyName, Collection<T> collection){
		/** select p from NameList list, Person p where p.name = some elements(list.names).*/
		return this.addQueryWhereParam(propertyName + " = some elements(?)", ParamType.LIST_PARAMETER, collection);
	}
	public <T extends Object> HQLWhereClause existElements(String alias, String propertyName){
		/** from Cat cat where exists elements(cat.kittens).*/
		return this.addQueryWhereParam("exists elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> HQLWhereClause existElements(String propertyName){
		/** from Cat cat where exists elements(cat.kittens).*/
		return this.addQueryWhereParam("exists elements(" + propertyName + ")");
	}

	public <T extends Object> HQLWhereClause allElementsGT(String alias, String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? > all elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> HQLWhereClause allElementsGT(String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? > all elements(" + propertyName + ")");
	}
	
	public <T extends Object> HQLWhereClause allElementsST(String alias, String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? < all elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> HQLWhereClause allElementsST(String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? < all elements(" + propertyName + ")", value);
	}
	public <T extends Object> HQLWhereClause allElementsEQ(String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? = all elements(" + propertyName + ")");
	}
	public <T extends Object> HQLWhereClause allElementsEQ(String alias, String propertyName, long value){
		/** from Player p where 3 > all elements(p.scores). */
		return this.addQueryWhereParam(" ? = all elements(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> HQLWhereClause inIndices(String alias, String propertyName, String value){
		/** from Show show where 'fizard' in indices(show.acts). */
		return this.addQueryWhereParam(" ? in indices(" + alias + "." + propertyName + ")");
	}
	public <T extends Object> HQLWhereClause inIndices(String propertyName, String value){
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
	
	public HQLWhereClause someElementsValuesEQ(String alias, String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(alias + "." + propertyName + " = some elements( ? )",ParamType.LIST_PARAMETER,  values);
	}
	public HQLWhereClause someElementsValuesEQ(String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(propertyName + " = some elements( ? )", ParamType.LIST_PARAMETER, values);
	}

	public HQLWhereClause allElementsValuesEQ(String beanName, String alias, String propertyName) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(beanName + " = all elements(" + alias + "." + propertyName + ")");
	}
	public HQLWhereClause allElementsValuesEQ(String beanName, String propertyName) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(beanName + " = all elements(" + propertyName + ")");
	}
	
	public HQLWhereClause allElementsValuesEQ(String propertyName, Collection<?> values) {
		/** prod = all elements(cust.currentOrder.lineItems)*/
		return this.addQueryWhereParam(propertyName + " = all elements( ? )", ParamType.LIST_PARAMETER, values);
	}	
	public HQLWhereClause allElementsValuesEQ(String alias, String propertyName, Collection<?> values) {
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
	public boolean isOpenGroup() {
		return openGroup;
	}

	/**
	 * @param openGroup the openGroup to set
	 */
	protected void setOpenGroup(boolean openGroup) {
		this.openGroup = openGroup;
	}
	
	public HQLWhereClause openGroup(){
		this.setOpenGroup(true);
		return this;
	}
	
	public HQLWhereClause closeGroup(){
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

	public WhereClause addSubSelectEQ(String alias, String propertyName, QueryBuilder queryBuilder) {
		/** from Cat as fatcat where fatcat.weight = ( select avg(cat.weight) from DomesticCat cat). */
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		
		return this.addQueryWhereParam( alias + "." + propertyName + " = ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectEQ(String propertyName, QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( propertyName + " = ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectEQSome(String propertyName, QueryBuilder queryBuilder) {
		/** from DomesticCat as cat where cat.name = some (select name.nickName from Name as name) */
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( propertyName + " = some ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectEQSome(String alias, String propertyName, QueryBuilder queryBuilder) {
		/** from DomesticCat as cat where cat.name = some (select name.nickName from Name as name) */
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( alias + "." + propertyName + " = some ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectExists(QueryBuilder queryBuilder, String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		List<String> params = CollectionHelper.convertGenericTo(propertiesNames);
		String notExistsQuery = StringHelper.convertToPlainString(params.toArray(), ",");
		return this.addQueryWhereParam( notExistsQuery + " exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectExists(String alias, QueryBuilder queryBuilder, String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();

		if(propertiesNames != null && propertiesNames.length > 1){
			List<String> aliasProperties = new ArrayList<String>();
			for(String o: propertiesNames){
				aliasProperties.add(alias + "." + o);
			}
			String existsQuery = StringHelper.convertToPlainString(aliasProperties.toArray(), ", ");
			return this.addQueryWhereParam( existsQuery + " not exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);	
		} else {
			if(propertiesNames.length == 1){
				return this.addSubSelectNotIn(alias, propertiesNames[0], queryBuilder);
			} else {
				return this.addQueryWhereParam( alias + " not exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);				
			}
		}
	}

	public WhereClause addSubSelectGT(String alias, String propertyName, QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( alias + "." + propertyName + " > ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectGT(String propertyName, QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( propertyName + " = ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectIn(String alias, QueryBuilder queryBuilder, String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();

		if(propertiesNames != null && propertiesNames.length > 1){
			List<String> aliasProperties = new ArrayList<String>();
			for(String o: propertiesNames){
				aliasProperties.add(alias + "." + o);
			}
			String notInQuery = StringHelper.convertToPlainString(aliasProperties.toArray(), ", ");
			return this.addQueryWhereParam( notInQuery + " in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);	
		} else {
			if(propertiesNames.length == 1){
				return this.addSubSelectNotIn(alias, propertiesNames[0], queryBuilder);
			} else {
				return this.addQueryWhereParam( alias + " in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);				
			}
		}
	}

	public WhereClause addSubSelectIn(QueryBuilder queryBuilder,String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();

		if(propertiesNames != null && propertiesNames.length > 1){
			String inQuery = StringHelper.convertToPlainString(propertiesNames, ", ");
			return this.addQueryWhereParam( inQuery + " in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);	
		} else {
			if(propertiesNames.length == 1){
				return this.addSubSelectNotIn(propertiesNames[0], queryBuilder);
			} else {
				throw new RuntimeException("Properties names should be more than zero because it hasn't a alias.");
			}
		}
	}

	public WhereClause addSubSelectNotExists(QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( " not exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}
	
	public WhereClause addSubSelectNotExists(QueryBuilder queryBuilder, String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		List<String> params = CollectionHelper.convertGenericTo(propertiesNames);
		String notExistsQuery = StringHelper.convertToPlainString(params.toArray(), ",");
		return this.addQueryWhereParam( notExistsQuery + " not exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectNotExists(String alias, QueryBuilder queryBuilder, String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();

		if(propertiesNames != null && propertiesNames.length > 1){
			List<String> aliasProperties = new ArrayList<String>();
			for(String o: propertiesNames){
				aliasProperties.add(alias + "." + o);
			}
			String notExistsQuery = StringHelper.convertToPlainString(aliasProperties.toArray(), ", ");
			return this.addQueryWhereParam( notExistsQuery + " not exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);	
		} else {
			if(propertiesNames.length == 1){
				return this.addSubSelectNotIn(alias, propertiesNames[0], queryBuilder);
			} else {
				return this.addQueryWhereParam( alias + " not exists ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);				
			}
		}
	}

	public WhereClause addSubSelectNotIn(String alias, String propertyName, QueryBuilder queryBuilder) {
		/*
		 "from DomesticCat cat where cat.name not in ( select name.nickName from Name name )";
		 */
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( alias + "." + propertyName + " not in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}
	
	public WhereClause addSubSelectNotIn(String alias, QueryBuilder queryBuilder, String ...propertiesNames){
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();

		if(propertiesNames != null && propertiesNames.length > 1){
			List<String> aliasProperties = new ArrayList<String>();
			for(String o: propertiesNames){
				aliasProperties.add(alias + "." + o);
			}
			String notInQuery = StringHelper.convertToPlainString(aliasProperties.toArray(), ", ");
			return this.addQueryWhereParam( notInQuery + " not in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);	
		} else {
			if(propertiesNames.length == 1){
				return this.addSubSelectNotIn(alias, propertiesNames[0], queryBuilder);
			} else {
				return this.addQueryWhereParam( alias + " not in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);				
			}
		}
	}

	public WhereClause addSubSelectNotIn(QueryBuilder queryBuilder, String... propertiesNames) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		List<String> params = CollectionHelper.convertGenericTo(propertiesNames);
		String notInQuery = StringHelper.convertToPlainString(params.toArray(), ",");
		return this.addQueryWhereParam( notInQuery + " not in ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectST(String alias, String propertyName, QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( alias + "." + propertyName + " < ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause addSubSelectST(String propertyName, QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		Object[] subQueryParams = query.getParams();
		String subQuery = query.getQuery();
		return this.addQueryWhereParam( propertyName + " > ( " + subQuery + " )", ParamType.OBJECT_PARAMETER, subQueryParams);
	}

	public WhereClause grouped(WhereClause whereClause) {
		this.openGroup();
		for(QueryConditionParam qcp: whereClause.getConditions()){
			this.addQueryWhereParam(qcp);
		}
		this.closeGroup();
		return this;
	}

}
