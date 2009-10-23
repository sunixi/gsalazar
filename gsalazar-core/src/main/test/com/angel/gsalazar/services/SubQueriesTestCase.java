/**
 * 
 */
package com.angel.gsalazar.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.QueryBuilderImpl;
import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.clauses.SelectClause;
import com.angel.dao.generic.query.clauses.WhereClause;
import com.angel.dao.generic.query.factory.impl.HQLClauseFactory;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class SubQueriesTestCase extends BaseQueriesTestCase{
	
	//from Cat as cat where not ( cat.name, cat.color ) in ( select cat.name, cat.color from DomesticCat cat )

	@Test
	public void testAddSubQueryGTInWhereClause(){
		String expectedQuery = "from Cat fatcat where fatcat.weight > ( select avg(cat.weight) from DomesticCat cat )";
		
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.add("Cat", "fatcat");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.addSubSelectGT("fatcat", "weight", this.newQueryBuilderForTestAddSubQueryGTInWhereClause());

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a new query like:
	 * <code>
	 * 	select avg(cat.weight) from DomesticCat cat
	 * </code>
	 * 
	 * @return
	 */
	protected QueryBuilder newQueryBuilderForTestAddSubQueryGTInWhereClause(){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());		
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause.add("DomesticCat", "cat");
		SelectClause selectClause = queryBuilder.getSelectClause();
		selectClause.addAvg("cat.weight");
		return queryBuilder;
	}

	@Test
	public void testAddSubQueryEQSomeInWhereClause(){
		String expectedQuery = "from DomesticCat cat where cat.name = some ( select name.nickName from Name name )";
		
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.add("DomesticCat", "cat");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.addSubSelectEQSome("cat", "name", this.newQueryBuilderForTestAddSubQueryEQSomeInWhereClause());

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a new query like:
	 * <code>
	 * 	select name.nickName from Name name
	 * </code>
	 * @return
	 */
	protected QueryBuilder newQueryBuilderForTestAddSubQueryEQSomeInWhereClause(){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());		
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause.add("Name", "name");
		SelectClause selectClause = queryBuilder.getSelectClause();
		selectClause.add("name.nickName");
		return queryBuilder;
	}

	@Test
	public void testAddSubQueryNotExistsInWhereClause(){
		String expectedQuery = "from Cat cat where not exists ( from Cat mate where mate.mate = cat )";
		
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.add("Cat", "cat");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.addSubSelectNotExists(this.newQueryBuilderForTestAddSubQueryNotExistsInWhereClause());

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like:
	 * <code>
	 * from Cat mate where mate.mate = cat
	 * </code>
	 * 
	 * @return
	 */
	protected QueryBuilder newQueryBuilderForTestAddSubQueryNotExistsInWhereClause(){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());		
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause.add("Cat", "mate");
		WhereClause whereClause = queryBuilder.getWhereClause();
		whereClause.equalsAlias("mate", "mate", "cat");
		return queryBuilder;
	}
	
	@Test
	public void testAddSubQueryNotInInWhereClause(){
		String expectedQuery = "from DomesticCat cat where cat.name not in ( select name.nickName from Name name )";
		
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.add("DomesticCat", "cat");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.addSubSelectNotIn("cat", "name", this.newQueryBuilderForTestAddSubQueryNotInInWhereClause());

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a new query like:
	 * <code>
	 * 	select name.nickName from Name name
	 * </code>
	 * @return
	 */
	protected QueryBuilder newQueryBuilderForTestAddSubQueryNotInInWhereClause(){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		SelectClause selectClause = queryBuilder.getSelectClause();
		selectClause.addAliased("name", "nickName");
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause.add("Name", "name");
		return queryBuilder;
	}
	
	@Test
	public void testAddSubQueryInSelectClause(){
		String expectedQuery = "select cat.id, ( select max(kit.weight) from cat.kitten kit) from Cat cat";
		SelectClause selectClause = this.getQueryBuilder().getSelectClause();
		selectClause
			.addAliased("cat", "id")
			.addSubQuery(this.newQueryBuilderForTestAddSubQueryInSelectClause());
		
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.add("Cat", "cat");

		String query = this.getQueryBuilder().buildQuery().getQuery();

		assertEquals("Query should be equals.", expectedQuery, query);
	}
	/**
	 * Create q query like.
	 * <code>
	 * select max(kit.weight) from cat.kitten kit
	 * </code>
	 * @return
	 */
	protected QueryBuilder newQueryBuilderForTestAddSubQueryInSelectClause(){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		SelectClause selectClause = queryBuilder.getSelectClause();
		selectClause.addMax("kit.weight");
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause.add("cat.kitten", "kit");
		return queryBuilder;
	}
}
