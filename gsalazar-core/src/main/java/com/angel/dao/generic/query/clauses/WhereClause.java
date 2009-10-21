/**
 * 
 */
package com.angel.dao.generic.query.clauses;

import java.util.Collection;
import java.util.List;

import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.clauses.impl.HQLWhereClause;
import com.angel.dao.generic.query.params.QueryConditionParam;


/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface WhereClause extends QueryClause {

	public boolean hasQueryConditionParams();
	
	public WhereClause addSubSelectNotIn(String alias, String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectNotIn(String alias, QueryBuilder queryBuilder, String ...propertyName);
	
	public WhereClause addSubSelectNotIn(QueryBuilder queryBuilder, String ...propertiesNames);
	
	public WhereClause addSubSelectIn(String alias, QueryBuilder queryBuilder, String ...propertiesNames);
	
	public WhereClause addSubSelectIn(QueryBuilder queryBuilder, String ...propertiesNames);
	
	public WhereClause addSubSelectNotExists(QueryBuilder queryBuilder);

	public WhereClause addSubSelectNotExists(QueryBuilder queryBuilder, String ...propertiesNames);
	
	public WhereClause addSubSelectNotExists(String alias, QueryBuilder queryBuilder, String ...propertiesNames);

	public WhereClause addSubSelectExists(QueryBuilder queryBuilder, String ...propertiesNames);
	
	public WhereClause addSubSelectExists(String alias, QueryBuilder queryBuilder, String ...propertiesNames);

	public WhereClause addSubSelectEQSome(String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectEQSome(String alias, String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectEQ(String alias, String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectGT(String alias, String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectST(String alias, String propertyName, QueryBuilder queryBuilder);

	public WhereClause addSubSelectEQ(String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectGT(String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause addSubSelectST(String propertyName, QueryBuilder queryBuilder);
	
	public WhereClause or();
	
	public WhereClause and();
	
	public WhereClause grouped(WhereClause whereClause);

	public WhereClause equals(String propertyName, Object value);
	
	public WhereClause equals(String alias, String propertyName, Object value);
	
	public WhereClause equals(String alias, String propertyName, String value);
	public WhereClause equals(String propertyName, String value);
	
	public HQLWhereClause equalsAlias(String alias, String propertyName, String beanAlias);
	public HQLWhereClause equalsAlias(String propertyName, String beanAlias);

	public WhereClause notEquals(String propertyName, Object value);
	
	public WhereClause notEquals(String alias, String propertyName, Object value);

	public WhereClause addQueryWhereParam(String name, Object ...valuesParams);
	
	public <T extends Object> WhereClause notIn(String propertyName, Collection<T> values);
	public <T extends Object> WhereClause notIn(String alias, String propertyName, Collection<T> values);
	public <T extends Object> WhereClause in(String propertyName, T[] values);
	public <T extends Object> WhereClause in(String propertyName, Collection<T> values);
	public <T extends Object> WhereClause in(String alias, String propertyName, Collection<T> values);
	
	public WhereClause isEmpty(String propertyName);
	public WhereClause isEmpty(String alias, String propertyName);
	public WhereClause isNoyEmpty(String propertyName);
	public WhereClause isNoyEmpty(String alias, String propertyName);

	public WhereClause memberOf(String alias, String propertyName);
	public WhereClause notMemberOf(String propertyName);
	public WhereClause notMemberOf(String alias, String propertyName);
	
	public WhereClause isNotNull(String propertyName);
	public WhereClause isNotNull(String alias, String propertyName);
	public WhereClause isNull(String propertyName) ;
	public WhereClause isNull(String alias, String propertyName);

	public WhereClause collectionSizeGT(String propertyName, long value);
	public WhereClause collectionSizeGT(String alias, String propertyName, long value);

	public WhereClause collectionSizeST(String propertyName, long value);
	public WhereClause collectionSizeST(String alias, String propertyName, long value);
	public WhereClause collectionSizeEQ(String propertyName, long value) ;
	public WhereClause collectionSizeEQ(String alias, String propertyName, long value);

	public WhereClause collectionMinElementGT(String alias, String propertyName, Object value);
	public WhereClause collectionMinElementST(String alias, String propertyName, Object value);
	public WhereClause collectionMinElementEQ(String alias, String propertyName, Object value);
	public WhereClause collectionMinElementGT(String propertyName, Object value);
	public WhereClause collectionMinElementST(String propertyName, Object value);
	public WhereClause collectionMinElementEQ(String propertyName, Object value);
	
	public WhereClause collectionMaxElementGT(String alias, String propertyName, Object value);
	public WhereClause collectionMaxElementST(String alias, String propertyName, Object value);
	public WhereClause collectionMaxElementEQ(String alias, String propertyName, Object value);
	
	public WhereClause collectionMaxElementGT(String propertyName, Object value);
	public WhereClause collectionMaxElementST(String propertyName, Object value);
	public WhereClause collectionMaxElementEQ(String propertyName, Object value);

	public WhereClause collectionMinIndexGT(String alias, String propertyName, Object value);
	public WhereClause collectionMinIndexST(String alias, String propertyName, Object value);
	public WhereClause collectionMinIndexEQ(String alias, String propertyName, Object value);
	public WhereClause collectionMinIndexGT(String propertyName, Object value);
	public WhereClause collectionMinIndexST(String propertyName, Object value);
	public WhereClause collectionMinIndexEQ(String propertyName, Object value);
	
	public WhereClause collectionMaxIndexGT(String alias, String propertyName, Object value);
	public WhereClause collectionMaxIndexST(String alias, String propertyName, Object value);
	public WhereClause collectionMaxIndexEQ(String alias, String propertyName, Object value);
	
	public WhereClause collectionMaxIndexGT(String propertyName, Object value);
	public WhereClause collectionMaxIndexST(String propertyName, Object value);
	public WhereClause collectionMaxIndexEQ(String propertyName, Object value);
	
	public WhereClause between(String propertyName, Object minValue, Object maxValue);
	public WhereClause between(String alias, String propertyName, Object minValue, Object maxValue);
	public WhereClause notBetween(String propertyName, Object minValue, Object maxValue);
	public WhereClause notBetween(String alias, String propertyName, Object minValue, Object maxValue);

	public WhereClause like(String alias, String propertyName, String value);
	public WhereClause like(String propertyName , String value);
	
	public WhereClause likeLeft(String alias, String propertyName, String value);
	public WhereClause likeLeft(String propertyName, String value);
	
	public WhereClause likeRight(String alias, String propertyName, String value);
	public WhereClause likeRight(String propertyName, String value);
	public WhereClause likeFull(String propertyName, String value);
	public WhereClause likeFull(String alias, String propertyName, String value);
	public <T extends Object> WhereClause inElements(String alias, String propertyName, Collection<T> collection);
	public <T extends Object> WhereClause inElements(String propertyName, Collection<T> collection);
	public <T extends Object> WhereClause someElements(String alias, String propertyName, Collection<T> collection);
	public <T extends Object> WhereClause someElements(String propertyName, Collection<T> collection);
	public <T extends Object> WhereClause existElements(String alias, String propertyName);
	public <T extends Object> WhereClause existElements(String propertyName);
	public <T extends Object> WhereClause allElementsGT(String alias, String propertyName, long value);
	public <T extends Object> WhereClause allElementsGT(String propertyName, long value);
	public <T extends Object> WhereClause allElementsST(String alias, String propertyName, long value);
	public <T extends Object> WhereClause allElementsST(String propertyName, long value);
	public <T extends Object> WhereClause allElementsEQ(String propertyName, long value);
	public <T extends Object> WhereClause allElementsEQ(String alias, String propertyName, long value);
	public <T extends Object> WhereClause inIndices(String alias, String propertyName, String value);
	public <T extends Object> WhereClause inIndices(String propertyName, String value);
	public WhereClause someElementsValuesEQ(String alias, String propertyName, Collection<?> values);
	public WhereClause someElementsValuesEQ(String propertyName, Collection<?> values);
	public WhereClause allElementsValuesEQ(String beanName, String alias, String propertyName);
	public WhereClause allElementsValuesEQ(String beanName, String propertyName);
	public WhereClause allElementsValuesEQ(String propertyName, Collection<?> values);	
	public WhereClause allElementsValuesEQ(String alias, String propertyName, Collection<?> values);
	
	public List<QueryConditionParam> getConditions();

	public boolean isOpenGroup();
	public WhereClause openGroup();

	public WhereClause closeGroup();

	public List<Object> getParams();

}
