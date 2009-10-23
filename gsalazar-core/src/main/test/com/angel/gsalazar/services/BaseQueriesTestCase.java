/**
 * 
 */
package com.angel.gsalazar.services;

import org.junit.After;
import org.junit.Before;

import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.QueryBuilderImpl;
import com.angel.dao.generic.query.factory.impl.HQLClauseFactory;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class BaseQueriesTestCase {
	
	private QueryBuilder queryBuilder;
	
	@Before
	public void beforeEachTest(){
		this.queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
	}

	@After
	public void afterEachTest(){
		this.queryBuilder = null;
	}

	/**
	 * @return the queryBuilder
	 */
	protected QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	/**
	 * @param queryBuilder the queryBuilder to set
	 */
	protected void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
}
