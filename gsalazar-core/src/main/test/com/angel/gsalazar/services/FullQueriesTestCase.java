/**
 * 
 */
package com.angel.gsalazar.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.clauses.SelectClause;
import com.angel.dao.generic.query.clauses.WhereClause;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class FullQueriesTestCase extends BasicQueriesTestCase{
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat inner join cat.mate mate left outer join cat.kittens kitten
	 * </code>
	 */
	@Test
	public void testFromWithInnetAndLeftJoin(){
		String expectedQuery = "from Cat cat inner join cat.mate mate left join cat.kittens kitten";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat","cat")
			.innerJoin("cat.mate", "mate")
			.leftJoin("cat.kittens", "kitten");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		System.out.println("Query 1: [" + query + "].");
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	select	cust
	 * 	from	Product prod,
	 * 			Store store
	 * 				inner join store.customers cust
	 * 	where	prod.name = 'widget'
	 * 			and store.location.name in ( 'Melbourne', 'Sydney' )
	 * 			and prod = all elements(cust.currentOrder.lineItems)
	 * </code>
	 * :param_0 : 'widget'
	 * :param_1 : 'Melbourne', 'Sydney'
	 */
	@Test
	public void testCreateQueryWithInnerJoinAndWhereConditions(){
		String expectedQuery = "select cust from Product prod, Store store inner join " +
				"store.customers cust where prod.name = :param_0 " +
				"AND store.location.name in ( :param_1 ) " +
				"AND prod = all elements( cust.currentOrder.lineItems )";
		
		SelectClause selectClause = this.getQueryBuilder().getSelectClause();
		selectClause.add("cust");
		
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Product","prod")
			.from("Store", "store")
			.innerJoin("store.customers", "cust");

		List<String> cities = new java.util.ArrayList<String>();
		cities.add("melbourne");
		cities.add("Sydney");

		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause
			.equals("prod.name", "widget")
			.in("store.location.name", cities)
			.allElementsValuesEQ("prod", "cust.currentOrder.lineItems");
		
		String query = this.getQueryBuilder().buildQuery().getQuery();
		System.out.println("Query 2: [" + query + "].");
		assertEquals("Query should be equals.", expectedQuery, query);
	}
}
