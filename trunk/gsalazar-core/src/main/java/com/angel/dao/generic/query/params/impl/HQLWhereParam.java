package com.angel.dao.generic.query.params.impl;

import java.util.List;

import com.angel.dao.generic.query.condition.Condition;
import com.angel.dao.generic.query.params.QueryConditionParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLWhereParam implements QueryConditionParam {

	private Condition condition;
	private String name;
	private List<Object> values;
	private boolean openTag;
	private boolean closeTag;
	
	public HQLWhereParam(){
		super();
		this.setCloseTag(false);
		this.setOpenTag(false);
	}
	
	public HQLWhereParam(String name, List<Object> value){
		this();
		this.setName(name);
		this.setValues(value);
	}
	
	public HQLWhereParam(Condition condition, String name, List<Object> values){
		this(name, values);
		this.setCondition(condition);
	}
	
	public HQLWhereParam(Condition condition, String name, boolean openTag, boolean closeTag, List<Object> values){
		this(condition, name, values);
		this.setOpenTag(openTag);
		this.setCloseTag(closeTag);
	}
	
	public Condition getCondition() {
		return this.condition;
	}

	public String getName() {
		return this.name;
	}

	public List<Object> getValues() {
		return this.values;
	}

	public boolean isCloseTag() {
		return this.closeTag;
	}

	public boolean isOpenTag() {
		return this.openTag;
	}

	public String queryCondition() {
		String condition = this.getCondition() != null ? this.getCondition().getName() : "";
		return condition + " " + this.getName();
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param value the value to set
	 */
	public void setValues(List<Object> values) {
		this.values = values;
	}

	/**
	 * @param openTag the openTag to set
	 */
	public void setOpenTag(boolean openTag) {
		this.openTag = openTag;
	}

	/**
	 * @param closeTag the closeTag to set
	 */
	public void setCloseTag(boolean closeTag) {
		this.closeTag = closeTag;
	} 
}
