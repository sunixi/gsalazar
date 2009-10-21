/**
 * 
 */
package com.angel.gsalazar.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ar.com.gsalazar.beans.Comentario;

import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.QueryBuilderImpl;
import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.clauses.WhereClause;
import com.angel.dao.generic.query.clauses.impl.HQLFromClause;
import com.angel.dao.generic.query.clauses.impl.HQLHavingClause;
import com.angel.dao.generic.query.clauses.impl.HQLSelectClause;
import com.angel.dao.generic.query.clauses.impl.HQLWhereClause;
import com.angel.dao.generic.query.factory.impl.HQLClauseFactory;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class QueryStatementTestCase {
	
	@Test
	public void testObjectFilter(){
		/*
		
		SelectClause selectClause = new SelectClause();
		selectClause.add("name").add("age").addMax("age");

		FromClause fromClause = new FromClause();
		fromClause.add(Articulo.class, "articulo").add("Articulo", "art");

		WhereClause whereClause = new WhereClause();
		whereClause.grouped(whereClause).equals("",null).equals("","")
			.distinct("",null).collectionSizeEQ("", 10L).collectionSizeST("", 10L)
			.collectionSizeGT("", 10L).notIn("comentarios", new ArrayList<Object>());
		
		GroupByClause groupByClause = new GroupByClause();
		groupByClause.add("surname").add("name");
		
		HavingClause havingClause = new HavingClause();
		havingClause.avgEQ("age", 25);

		OrderByClause orderByClause = new OrderByClause();
		orderByClause.asc("surname").desc("name").desc("count(*)");
		
		
		QueryFilter queryFilter = new ObjectFilter(fromClause);
		String query = queryFilter.applys();
		System.out.println("Query: " + query);
		*/
	}
	
	/**
	 *	select	cust
	 *	from	Product prod,
	 *			Store store
	 *	inner join store.customers cust
	 *	where	prod.name = 'widget'
	 *			and store.location.name in ( 'Melbourne', 'Sydney' )
	 *			and prod = all elements(cust.currentOrder.lineItems)
	 */
	@Test
	public void testCreateQuery(){
		HQLSelectClause selectClause = new HQLSelectClause();
		selectClause.add("cust");
		/*
		FromClause fromClause = new FromClause();
		fromClause	.add("Product", "prod")
					.add("Store", "store")
					.innerJoin("Customer", "cust");
		
		List<String> list = new ArrayList<String>();
		list.add("Melbourne");
		list.add("Sydney");
		WhereClause whereClause = new WhereClause();
		whereClause.equals("prod", "name", "widget");
		whereClause.in("store", "location.name", list);
		whereClause.allElementsValuesEQ("prod", "cust", "currentOrder.lineItems");
		*/
	}
	
	@Test
	public void testCreateSelectClauseQuery(){
		HQLSelectClause selectClause = new HQLSelectClause();
		selectClause.add("cust").addCount("age").addCount("size", "s");
		
		System.out.println("Query: " + selectClause.createClause());
	}
	
	@Test
	public void testCreateFromClauseQuery(){
		HQLFromClause fromClause = new HQLFromClause();
		fromClause.add("Articulo", "arti").innerJoin(Comentario.class);
		System.out.println("Query: " + fromClause.createClause());
		assertEquals("Query for from clause should be equals.", "from Articulo arti inner join " + Comentario.class.getName(), fromClause.createClause());
	}
	
	@Test
	public void testCreateWhereQuery(){
		HQLFromClause fromClause = new HQLFromClause();
		fromClause	.add("Product", "prod")
					.add("Store", "store")
					.innerJoin("Customer", "cust");
		
		List<String> list = new ArrayList<String>();
		list.add("Melbourne");
		list.add("Sydney");
		HQLWhereClause whereClause = new HQLWhereClause();
		whereClause.equals("prod", "name", "widget");
		whereClause.in("store", "location.name", list);
		whereClause.allElementsValuesEQ("prod", "cust", "currentOrder.lineItems");
		System.out.println("Query: " + whereClause.createClause());
	}
	
	@Test
	public void testCreateHavingQuery(){
		HQLHavingClause havingClause = new HQLHavingClause();
		havingClause
			.avgEQ("percent", 18)
			.and().countDistinctEQ("cust", "age", 47);
			;
		System.out.println("Query: " + havingClause.createClause());
	}
	
	@Test
	public void testQuery(){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause
			.add(Comentario.class, "comentario")
			.add("Articulos", "articulos");
		WhereClause whereClause = queryBuilder.getWhereClause();
		whereClause
			.between("age", 15, 25)
			.notEquals("name", "Diego");
		
		System.out.println("Query: " + queryBuilder.buildQuery().getQuery());
	}
}
