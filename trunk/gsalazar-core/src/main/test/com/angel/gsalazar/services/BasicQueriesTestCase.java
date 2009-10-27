/**
 * 
 */
package com.angel.gsalazar.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.clauses.OrderByClause;
import com.angel.dao.generic.query.clauses.SelectClause;
import com.angel.dao.generic.query.clauses.WhereClause;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class BasicQueriesTestCase extends BaseQueriesTestCase{

	/**
	 * Create a query like
	 * <code>
	 * 	from eg.Cat
	 * </code>
	 */
	@Test
	public void testFromWithPrefix(){
		String expectedQuery = "from eg.Cat";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.from("eg.Cat");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat
	 * </code>
	 */
	@Test
	public void testFromWithoutPrefix(){
		String expectedQuery = "from Cat";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.from("Cat");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat
	 * </code>
	 */
	@Test
	public void testFromWithAlias(){
		String expectedQuery = "from Cat cat";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.from("Cat", "cat");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	from Formula, Parameter
	 * </code>
	 */
	@Test
	public void testFromWith2Sources(){
		String expectedQuery = "from Formula, Parameter";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Formula")
			.from("Parameter");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	from Formula form, Parameter param
	 * </code>
	 */
	@Test
	public void testFromWith2SourcesWithAlias(){
		String expectedQuery = "from Formula form, Parameter param";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Formula","form")
			.from("Parameter", "param");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat left join cat.mate.kittens kittens
	 * </code>
	 */
	@Test
	public void testFromWithLeftJoinWithAlias(){
		String expectedQuery = "from Cat cat left join cat.mate.kittens kittens";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat","cat")
			.leftJoin("cat.mate.kittens", "kittens");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	from Formula form full join form.parameter param
	 * </code>
	 */
	@Test
	public void testFromWithFullJoinWithAlias(){
		String expectedQuery = "from Formula form full join form.parameter param";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Formula","form")
			.fullJoin("form.parameter", "param");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat join cat.mate mate left join cat.kittens kitten
	 * </code>
	 */
	@Test
	public void testFromWithJoinsAndAliases(){
		String expectedQuery = "from Cat cat inner join cat.mate mate left join cat.kittens kitten";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat","cat")
			.innerJoin("cat.mate", "mate")
			.leftJoin("cat.kittens", "kitten");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat where cat.mate.name like '%:param_0%'
	 * </code>
	 */
	@Test
	public void testFromWithLikeCondition(){
		String expectedQuery = "from Cat cat where cat.mate.name like '%:param_0%'";
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.from("Cat","cat");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.like("cat.mate.name", "s");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	select cat.mate from Cat cat
	 * </code>
	 */
	@Test
	public void testBasicSelect(){
		String expectedQuery = "select cat.mate from Cat cat";
		SelectClause selectClause = this.getQueryBuilder().getSelectClause();
		selectClause.addAliased("cat", "mate");
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat","cat");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	select mate from Cat cat inner join cat.mate mate
	 * </code>
	 */
	@Test
	public void testBasicSelectWithJoin(){
		String expectedQuery = "select mate from Cat cat inner join cat.mate mate";
		SelectClause selectClause = this.getQueryBuilder().getSelectClause();
		selectClause.add("mate");
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat","cat")
			.innerJoin("cat.mate", "mate");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	select cat.name from DomesticCat cat where cat.name like ':param_0%'
	 * </code>
	 */
	@Test
	public void testBasicSelectWithCondition(){
		String expectedQuery = "select cat.name from DomesticCat cat where cat.name like ':param_0%'";
		SelectClause selectClause = this.getQueryBuilder().getSelectClause();
		selectClause.addAliased("cat", "name");
		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("DomesticCat","cat");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.likeRight("cat", "name", "fri");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	/**
	 * Create a query like
	 * <code>
	 * 	from Document fetch all properties
	 * </code>
	 */
	@Test
	public void testBasicSelectFetchAllProperties(){
		String expectedQuery = "from Document fetch all properties";

		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.fromFetchProperties("Document");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Document fetch all properties order by name
	 * </code>
	 */
	@Test
	public void testBasicSelectFetchAllPropertiesOrderByName(){
		String expectedQuery = "from Document fetch all properties order by name desc";

		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.fromFetchProperties("Document");
		OrderByClause orderByClause = this.getQueryBuilder().getOrderByClause();
		orderByClause.desc("name");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat inner join fetch cat.mate
	 * </code>
	 */
	@Test
	public void testBasicInnerJoinWithFecth(){
		String expectedQuery = "from Cat cat inner join fetch cat.mate";

		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat", "cat")
			.innerJoinFetch("cat.mate");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat inner join fetch cat.mate left join fetch cat.kittens
	 * </code>
	 */
	@Test
	public void testBasic2JoinsWithFecth(){
		String expectedQuery = "from Cat cat inner join fetch cat.mate left join fetch cat.kittens";

		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat", "cat")
			.innerJoinFetch("cat.mate")
			.leftJoinFetch("cat.kittens");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
	
	/**
	 * Create a query like
	 * <code>
	 * 	from Document doc fetch all properties where doc.name like '%cats%'
	 * </code>
	 */
	@Test
	public void testBasicFecthAllPropertiesWithWhereConditions(){
		String expectedQuery = "from Document doc fetch all properties where doc.name like '%:param_0%'";

		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause.fromFetchProperties("Document", "doc");
		WhereClause whereClause = this.getQueryBuilder().getWhereClause();
		whereClause.like("doc", "name", "cats");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}

	
	/**
	 * Create a query like
	 * <code>
	 * 	from Cat cat inner join fetch cat.mate left join fetch cat.kittens child left join fetch child.kittens
	 * </code>
	 */
	@Test
	public void testBasicJoinsWithFecthWithWhereConditions(){
		String expectedQuery = "from Cat cat inner join fetch cat.mate left join fetch cat.kittens child left join fetch child.kittens";

		FromClause fromClause = this.getQueryBuilder().getFromClause();
		fromClause
			.from("Cat", "cat")
			.innerJoinFetch("cat.mate")
			.leftJoinFetch("cat.kittens", "child")
			.leftJoinFetch("child.kittens");

		String query = this.getQueryBuilder().buildQuery().getQuery();
		assertEquals("Query should be equals.", expectedQuery, query);
	}
}
