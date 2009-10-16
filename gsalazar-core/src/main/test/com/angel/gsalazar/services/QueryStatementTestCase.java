/**
 * 
 */
package com.angel.gsalazar.services;

import org.junit.Test;

import ar.com.gsalazar.beans.Articulo;

import com.angel.dao.generic.query.QueryStatement;
import com.angel.dao.generic.query.QueryStatementImpl;
import com.angel.dao.generic.query.operators.impl.LikeQueryOperator;
import com.angel.dao.generic.query.operators.impl.SizeCollectionQueryOperator;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class QueryStatementTestCase {
	
	@Test
	public void testCreateQueryStatement(){
		QueryStatement queryStatement = new QueryStatementImpl();
		queryStatement
			.addSelectionParam("user")
			.addSelectionParam("personname","name")
			.addSourceData(Articulo.class)
			.addSourceData(Articulo.class, "art")
			.addConditionParam("apellido", "salazar")
			.addConditionParam("apellido", "sa l azar", new LikeQueryOperator())
			.addConditionParam("comentarios", "10", new SizeCollectionQueryOperator());
		String query = queryStatement.buildQuery();
		System.out.println("Query: " + query);
	}
	
}
