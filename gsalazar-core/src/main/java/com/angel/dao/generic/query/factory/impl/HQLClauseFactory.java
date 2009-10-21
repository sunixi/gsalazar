package com.angel.dao.generic.query.factory.impl;

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



/**
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLClauseFactory implements ClauseFactory{

	public FromClause createFromClause() {
		return new HQLFromClause();
	}

	public GroupByClause createGroupByClause() {
		return new HQLGroupByClause();
	}

	public HavingClause createHavingClause() {
		return new HQLHavingClause();
	}

	public OrderByClause createOrderByClause() {
		return new HQLOrderByClause();
	}

	public SelectClause createSelectClause() {
		return new HQLSelectClause();
	}

	public WhereClause createWhereClause() {
		return new HQLWhereClause();
	}
		
}
