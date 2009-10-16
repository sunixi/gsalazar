package com.angel.dao.generic.query.params.impl;

import com.angel.dao.generic.query.QueryStatement;
import com.angel.dao.generic.query.operators.QueryOperator;
import com.angel.dao.generic.query.operators.impl.EqualsQueryOperator;
import com.angel.dao.generic.query.params.QueryConditionParam;
import com.angel.dao.generic.query.params.QuerySelectionParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class QueryPropertyParam implements QueryConditionParam, QuerySelectionParam{
    
	public static final String DEFAULT_EQUALS_OPERATOR = " = ";
	
	private String name;
	private String alias = "";
	private Object value;
	private String condition;
	private QueryOperator operator;
	private boolean openTag = false;
	private boolean closeTag = false;

	public QueryPropertyParam(String name, String value, String condition, QueryOperator operator, boolean openTag, boolean closeTag) {
        this(name, value);
        this.setCondition(condition);
        this.setOpenTag(openTag);
        this.setOperator(operator);
        this.setCloseTag(closeTag);
    }
	
	public QueryPropertyParam(String name, String value, String condition, boolean openTag, boolean closeTag) {
        this(name, value);
        this.setCondition(condition);
        this.setOpenTag(openTag);
        this.setOperator(new EqualsQueryOperator());
        this.setCloseTag(closeTag);
    }

	public QueryPropertyParam(String name, String value, String condition, QueryOperator operator) {
        this(name, value, condition, operator, false, false);
    }
	
	public QueryPropertyParam(String name, String value, String condition) {
        this(name, value);
        this.setCondition(condition);
    }
	
	public QueryPropertyParam(String name, String value) {
        this();
        this.setName(name);
        this.setValue(value);
    }

    public QueryPropertyParam() {
        super();
        this.setOperator(new EqualsQueryOperator());
        this.setCondition(QueryStatement.AND_CONDITION);
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String buildCondition(String separator){
		String queryParam = "";
		queryParam += this.getCondition();
		if(this.isOpenTag()){
			queryParam += " ( ";
		}
		queryParam +=  this.getName() + separator + this.getValue();
		if(this.isCloseTag()){
			queryParam += " ) ";
		}
		return queryParam;
	}
	
	public String buildFirstCondition(String separator){
		String queryParam = "";
		if(this.isOpenTag()){
			queryParam += " ( ";
		}
		queryParam +=  this.getName() + separator + this.getValue();
		if(this.isCloseTag()){
			queryParam += " ) ";
		}
		return queryParam;
	}


	public boolean isName(String name) {
		return this.getName().equalsIgnoreCase(name);
	}
	
	public boolean isValue(Object value) {
		return this.getValue().equals(value);
	}

	public boolean isCondition(String condition) {
		return this.getCondition().equalsIgnoreCase(condition);
	}

	public boolean isParameterState(String name, String value, String condition) {
		return this.isName(name) && this.isValue(value) && this.isCondition(condition);
	}

	/**
	 * @return the operator
	 */
	public QueryOperator getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(QueryOperator operator) {
		this.operator = operator;
	}

	/**
	 * @return the openTag
	 */
	public boolean isOpenTag() {
		return openTag;
	}

	/**
	 * @param openTag the openTag to set
	 */
	public void setOpenTag(boolean openTag) {
		this.openTag = openTag;
	}

	/**
	 * @return the closeTag
	 */
	public boolean isCloseTag() {
		return closeTag;
	}

	/**
	 * @param closeTag the closeTag to set
	 */
	public void setCloseTag(boolean closeTag) {
		this.closeTag = closeTag;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSelection() {
		return this.getName() + " " + this.getAlias();
	}

	public String getQueryCondition() {
		String queryCondition = this.isOpenTag() ? " ( ": " ";
		queryCondition += 	this.getOperator().createOperatorQuery(this);
		queryCondition += this.isCloseTag() ? " ) ": " ";
		return queryCondition;
	}
}
