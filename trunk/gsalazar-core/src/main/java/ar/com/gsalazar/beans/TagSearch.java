/**
 * 
 */
package ar.com.gsalazar.beans;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.common.helpers.StringHelper;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class TagSearch extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	@Column(nullable = false, length = 20, unique = true)
	private String label;
	@Column(nullable = false, length = 150, unique = true)
	private String description;
	@Column(nullable = false )
	private Long occurrences = 1L;
	
	public TagSearch(){
		super();
	}
	
	public TagSearch(String label, String description){
		this();
		this.setLabel(label);
		this.setDescription(description);
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasLabel(String label) {
		return StringHelper.isNotEmpty(this.getLabel()) && this.getLabel().equalsIgnoreCase(label);
	}

	/**
	 * @return the occurrences
	 */
	public Long getOccurrences() {
		return occurrences;
	}

	/**
	 * @param occurrences the occurrences to set
	 */
	public void setOccurrences(Long occurrences) {
		this.occurrences = occurrences;
	}
	
	public void incrementarOccurencia(){
		this.occurrences++;
	}
	
	public void incrementarOccurencia(long unidades){
		this.occurrences += unidades;
	}
}
