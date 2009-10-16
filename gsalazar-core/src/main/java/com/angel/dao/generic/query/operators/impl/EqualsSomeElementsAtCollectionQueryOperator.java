package com.angel.dao.generic.query.operators.impl;

import com.angel.dao.generic.query.operators.QueryOperator;
import com.angel.dao.generic.query.params.QueryConditionParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class EqualsSomeElementsAtCollectionQueryOperator implements QueryOperator{
	
	private SizeCondition sizeCondition;
	
	public EqualsSomeElementsAtCollectionQueryOperator(){
		this(SizeCondition.EQ);
	}
	
	public EqualsSomeElementsAtCollectionQueryOperator(SizeCondition sizeCondition){
		super();
		this.setSizeCondition(sizeCondition);
	}

	public String createOperatorQuery(QueryConditionParam queryConditionParam) {
		
//		" '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
		return this.getSizeCondition().createSizeQuery(queryConditionParam);

	}
	
	/**
	 * @return the sizeCondition
	 */
	public SizeCondition getSizeCondition() {
		return sizeCondition;
	}

	/**
	 * @param sizeCondition the sizeCondition to set
	 */
	public void setSizeCondition(SizeCondition sizeCondition) {
		this.sizeCondition = sizeCondition;
	}



	public enum SizeCondition {
		EQ("EQUALS"){
			public String createSizeQuery(QueryConditionParam queryConditionParam) {
				return " size(" + queryConditionParam.getName() + ") = " + queryConditionParam.getValue() + " ";
			}
		},
		GT("GREATER THAN"){
			@Override
			public String createSizeQuery(QueryConditionParam queryConditionParam) {
				return " size(" + queryConditionParam.getName() + ") > " + queryConditionParam.getValue() + " ";
			}
		},
		ST("SLOWER THAN") {
			@Override
			public String createSizeQuery(QueryConditionParam queryConditionParam) {
				return " size(" + queryConditionParam.getName() + ") < " + queryConditionParam.getValue() + " ";
			}
		},
		GET("GREATER OR EQUALS THAN") {
			@Override
			public String createSizeQuery(QueryConditionParam queryConditionParam) {
				return " size(" + queryConditionParam.getName() + ") >= " + queryConditionParam.getValue() + " ";
			}
		},
		SET("SLOWER OR EQUALS THAN") {
			@Override
			public String createSizeQuery(QueryConditionParam queryConditionParam) {
				return " size(" + queryConditionParam.getName() + ") <= " + queryConditionParam.getValue() + " ";
			}
		};
		
		private String name;
		
		private SizeCondition(String name) {
			this.name = name;
		}

		public abstract String createSizeQuery(QueryConditionParam queryConditionParam);
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}
}
