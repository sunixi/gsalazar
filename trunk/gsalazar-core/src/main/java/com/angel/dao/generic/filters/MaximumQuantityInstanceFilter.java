/**
 *
 */
package com.angel.dao.generic.filters;

import com.angel.dao.generic.interfaces.FindAllInstancesFilter;

/**
 *
 * @author William
 *
 */
public class MaximumQuantityInstanceFilter<H extends Object> implements FindAllInstancesFilter<H>{

	private Long quantityMaximum = -1L;
	private Long currentQuantity = 0L;
	private Integer priorityLevel;

	public MaximumQuantityInstanceFilter(Long quantityMaximum, Integer priorityLevel){
		super();
		this.quantityMaximum = quantityMaximum;
		this.priorityLevel = priorityLevel;
		this.currentQuantity = 0L;
	}
	public MaximumQuantityInstanceFilter(Long quantityMaximum, Integer priorityLevel, Long currentQuantity){
		super();
		this.quantityMaximum = quantityMaximum;
		this.priorityLevel = priorityLevel;
		this.currentQuantity = currentQuantity;
	}


	public boolean appliesFilter(H instance) {
		boolean applies = true;
		if((currentQuantity + 1) > quantityMaximum){
			applies = false;
		}
		currentQuantity++;
		return applies;
	}


	public Integer priorityLevel() {
		return this.priorityLevel;
	}


}

