/**
 * 
 */
package com.angel.architecture.persistence.beans;

import java.util.List;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class TagSearchContainer extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	private Long totalOcurrencias = 0L;
	private List<TagSearch> tagsSearchs;
	
	public TagSearchContainer(List<TagSearch> tagsSearchs){
		super();
		this.setTagsSearchs(tagsSearchs);
		Long totalOccurences = 0L;
		for(TagSearch tagSearch: tagsSearchs){
			totalOccurences += tagSearch.getOccurrences();
		}
		this.setTotalOcurrencias(totalOccurences);
	}

	/**
	 * @return the tagsSearchs
	 */
	public List<TagSearch> getTagsSearchs() {
		return tagsSearchs;
	}

	/**
	 * @param tagsSearchs the tagsSearchs to set
	 */
	public void setTagsSearchs(List<TagSearch> tagsSearchs) {
		this.tagsSearchs = tagsSearchs;
	}

	/**
	 * @return the totalOcurrencias
	 */
	public Long getTotalOcurrencias() {
		return totalOcurrencias;
	}

	/**
	 * @param totalOcurrencias the totalOcurrencias to set
	 */
	public void setTotalOcurrencias(Long totalOcurrencias) {
		this.totalOcurrencias = totalOcurrencias;
	}
}
