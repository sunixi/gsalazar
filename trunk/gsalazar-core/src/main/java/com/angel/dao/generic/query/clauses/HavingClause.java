/**
 * 
 */
package com.angel.dao.generic.query.clauses;

import java.util.Collection;
import java.util.List;

import com.angel.dao.generic.query.clauses.impl.HQLHavingClause;
import com.angel.dao.generic.query.params.QueryConditionParam;


/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface HavingClause extends QueryClause {

	public HavingClause and();
	
	public HavingClause or();

	public boolean hasQueryConditionParams();

	public HavingClause closeGroup();
	
	public HavingClause openGroup();
	
	public HavingClause grouped(HQLHavingClause havingClause);
	
	public HavingClause minGT(String alias, String property, long value);
	
	public HavingClause minGT(String property, long value);
	public HavingClause minST(String property, long value);
	public HavingClause minST(String alias, String property, long value);
	public HavingClause minEQ(String property, long value);
	public HavingClause minEQ(String alias, String property, long value);

	public HavingClause maxGT(String property, long value);
	public HavingClause maxGT(String alias, String property, long value);
	public HavingClause maxST(String property, long value);
	public HavingClause maxST(String alias, String property, long value);
	public HavingClause maxEQ(String property, long value);
	public HavingClause maxEQ(String alias, String property, long value);
	
	public HavingClause countGT(String property, long value);
	public HavingClause countGT(String alias, String property, long value);
	public HavingClause countST(String property, long value);
	public HavingClause countST(String alias, String property, long value);

	public HavingClause countEQ(String property, long value);
	public HavingClause countEQ(String alias, String property, long value);
	public HavingClause avgGT(String property, long value);
	public HavingClause avgGT(String alias, String property, long value);
	public HavingClause avgST(String property, long value);
	public HavingClause avgST(String alias, String property, long value);
	public HavingClause avgEQ(String property, long value);
	public HavingClause avgEQ(String alias, String property, long value);
	public HavingClause sumGT(String property, long value);
	public HavingClause sumGT(String alias, String property, long value);
	public HavingClause sumST(String property, long value);
	public HavingClause sumST(String alias, String property, long value);
	public HavingClause sumEQ(String property, long value);
	public HavingClause sumEQ(String alias, String property, long value);
	public HavingClause countDistinctGT(String property, long value);
	public HavingClause countDistinctGT(String alias, String property, long value);
	public HavingClause countDistinctST(String property, long value);
	public HavingClause countDistinctST(String alias, String property, long value);
	public HavingClause countDistinctEQ(String property, long value);
	public HavingClause countDistinctEQ(String alias, String property, long value);
	public Collection<? extends Object> getParams();
	public List<QueryConditionParam> getConditions();
	
	public boolean isOpenGroup();

}
