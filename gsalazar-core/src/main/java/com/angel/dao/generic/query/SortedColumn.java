package com.angel.dao.generic.query;

import java.io.Serializable;



/**
 *
 * @author Guille Salazar
 * @since 16/Jul/2009
 */
public class SortedColumn implements Serializable{
	private static final long serialVersionUID = -1367131046428932183L;

	private String name;
	private String orderType;
	private Integer position;

	public SortedColumn(String name, String orderType, Integer position) {
        this(name, orderType);
        this.setPosition(position);
    }
	
	public SortedColumn(String name, String orderType) {
        super();
        this.setName(name);
        this.setOrderType(orderType);
    }


    public SortedColumn() {
        super();
        this.setOrderType(QueryStatement.DESC_ORDER_TYPE);
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
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
}
