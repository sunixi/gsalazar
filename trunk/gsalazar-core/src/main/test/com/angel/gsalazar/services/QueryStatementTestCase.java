/**
 * 
 */
package com.angel.gsalazar.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ar.com.gsalazar.beans.Comentario;

import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.HQLQueryBuilder;
import com.angel.dao.generic.query.clauses.impl.FromClause;
import com.angel.dao.generic.query.clauses.impl.HavingClause;
import com.angel.dao.generic.query.clauses.impl.SelectClause;
import com.angel.dao.generic.query.clauses.impl.WhereClause;

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
		SelectClause selectClause = new SelectClause();
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
		SelectClause selectClause = new SelectClause();
		selectClause.add("cust").addCount("age").addCount("size", "s");
		
		System.out.println("Query: " + selectClause.createClause());
	}
	
	@Test
	public void testCreateFromClauseQuery(){
		FromClause fromClause = new FromClause();
		fromClause.add("Articulo", "arti").innerJoin(Comentario.class);
		System.out.println("Query: " + fromClause.createClause());
		assertEquals("Query for from clause should be equals.", "from Articulo arti inner join " + Comentario.class.getName(), fromClause.createClause());
	}
	
	@Test
	public void testCreateWhereQuery(){
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
		System.out.println("Query: " + whereClause.createClause());
	}
	
	@Test
	public void testCreateHavingQuery(){
		HavingClause havingClause = new HavingClause();
		havingClause
			.avgEQ("percent", 18)
			.and().countDistinctEQ("cust", "age", 47);
			;
		System.out.println("Query: " + havingClause.createClause());
	}
	
	@Test
	public void testQuery(){
		QueryBuilder queryBuilder = new HQLQueryBuilder();
		
		FromClause fromClause = (FromClause) queryBuilder.buildFromClause();
		fromClause
			.add(Comentario.class, "comentario")
			.add("Articulos", "articulos");
		WhereClause whereClause = (WhereClause) queryBuilder.buildWhereClause();
		whereClause
			.between("age", 15, 25)
			.notEquals("name", "Diego");
		
		System.out.println("Query: " + queryBuilder.buildQuery().getQuery());
	}
}
