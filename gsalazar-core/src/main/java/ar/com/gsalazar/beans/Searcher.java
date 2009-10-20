/**
 * 
 */
package ar.com.gsalazar.beans;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.architecture.persistence.beans.TagSearch;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name ="discriminator")
@DiscriminatorValue("searcher")
public abstract class Searcher extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = false, unique = false)
	private TagSearch tagSearch;
	
	public Searcher(){
		super();
	}
	
	public Searcher(TagSearch tagSearch){
		super();
		this.setTagSearch(tagSearch);
	}

	/**
	 * @return the tagSearch
	 */
	public TagSearch getTagSearch() {
		return tagSearch;
	}

	/**
	 * @param tagSearch the tagSearch to set
	 */
	public void setTagSearch(TagSearch tagSearch) {
		this.tagSearch = tagSearch;
	}	
}
