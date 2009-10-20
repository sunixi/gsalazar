/**
 * 
 */
package ar.com.gsalazar.daos.queryBuilder.impl;

import java.util.List;

import ar.com.gsalazar.daos.queryBuilder.QueryBeanFactory;

import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.architecture.persistence.beans.TagSearch;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class TagsSearchQueryBeanFactory implements QueryBeanFactory {

	public TagsSearchQueryBeanFactory(){
		super();
	}

	@SuppressWarnings("unchecked")
	public String createQueryBean(Class<? extends PersistentObject> persistentObject, Object bean) {
		List<TagSearch> tagsSearch = (List<TagSearch>) bean;
		String q = "select l from " + persistentObject.getName() + " l ";
		q += this.buildTagSearchQuery(tagsSearch);
		return q;
	}
	
	protected String buildTagSearchQuery(List<TagSearch> tagsSearch){
		/*
			select p from Eg.NameList list, Eg.Person p
			where p.Name = some elements(list.Names)
		 */
		int size = tagsSearch != null ? tagsSearch.size() : 0;
		String q = "";
		int current = 0;
		for(TagSearch ts: tagsSearch){
			if(size > 1 && current == 0){
				q += " where '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
			} else {
				if(size == 1){
					q += " where '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";	
				} else {
					if(current > 0 && current != size){
						q += " and '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
					} else {
						q += " and '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
					}
				}
			}
			current++;
		}
		return q;
	}	
}
