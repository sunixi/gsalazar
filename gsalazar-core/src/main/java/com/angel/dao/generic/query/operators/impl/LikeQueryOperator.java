package com.angel.dao.generic.query.operators.impl;

import com.angel.dao.generic.query.operators.QueryOperator;
import com.angel.dao.generic.query.params.QueryConditionParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class LikeQueryOperator implements QueryOperator{
	
	private LikeCondition likeCondition;

	public LikeQueryOperator(){
		this(LikeCondition.ANY_PLACE);
	}
	
	public LikeQueryOperator(LikeCondition likeCondition){
		super();
		this.setLikeCondition(likeCondition);
	}

	public String createOperatorQuery(QueryConditionParam queryConditionParam) {
		return queryConditionParam.getName() + " like '" + this.getLikeCondition().createLikeQuery(queryConditionParam.getValue()) + "' ";
	}
	
	/**
	 * @return the likeCondition
	 */
	public LikeCondition getLikeCondition() {
		return likeCondition;
	}

	/**
	 * @param likeCondition the likeCondition to set
	 */
	public void setLikeCondition(LikeCondition likeCondition) {
		this.likeCondition = likeCondition;
	}




	public enum LikeCondition {
		BEGINS("BEGIN"){
			@Override
			public String createLikeQuery(Object value) {
				return "%?";
			}
		},
		CONTAINS("CONTAINS"){
			@Override
			public String createLikeQuery(Object value) {
				return "%" + "?" + "%";
			}
		},
		ANY_PLACE("ANY PLACE") {
			@Override
			public String createLikeQuery(Object value) {
				return "%" + "?" + "%";
			}
		},
		ENDS("ENDS") {
			@Override
			public String createLikeQuery(Object value) {
				return "?" + "%";
			}
		};
		
		private String name;
		
		private LikeCondition(String name) {
			this.name = name;
		}

		public abstract String createLikeQuery(Object value);
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}
}
