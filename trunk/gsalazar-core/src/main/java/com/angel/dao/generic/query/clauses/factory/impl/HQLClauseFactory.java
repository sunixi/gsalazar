package com.angel.dao.generic.query.clauses.factory.impl;

import com.angel.dao.generic.query.clauses.QueryClause;
import com.angel.dao.generic.query.clauses.factory.ClauseFactory;
import com.angel.dao.generic.query.clauses.impl.FromClause;
import com.angel.dao.generic.query.clauses.impl.GroupByClause;
import com.angel.dao.generic.query.clauses.impl.HavingClause;
import com.angel.dao.generic.query.clauses.impl.OrderByClause;
import com.angel.dao.generic.query.clauses.impl.SelectClause;
import com.angel.dao.generic.query.clauses.impl.WhereClause;



/**
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLClauseFactory implements ClauseFactory{

	public QueryClause createFromClause() {
		return new FromClause();
	}

	public QueryClause createGroupByClause() {
		return new GroupByClause();
	}

	public QueryClause createHavingClause() {
		return new HavingClause();
	}

	public QueryClause createOrderByClause() {
		return new OrderByClause();
	}

	public QueryClause createSelectClause() {
		return new SelectClause();
	}

	public QueryClause createWhereClause() {
		return new WhereClause();
	}
		
}
