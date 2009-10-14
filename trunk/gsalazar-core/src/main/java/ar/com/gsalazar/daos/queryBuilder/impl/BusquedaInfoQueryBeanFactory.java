/**
 * 
 */
package ar.com.gsalazar.daos.queryBuilder.impl;

import java.util.List;

import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.queryBuilder.QueryBeanFactory;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class BusquedaInfoQueryBeanFactory implements QueryBeanFactory {

	public BusquedaInfoQueryBeanFactory(){
		super();
	}

	public String createQueryBean(Class<? extends PersistentObject> persistentObject, Object bean) {
		/*
			select p from Eg.NameList list, Eg.Person p
			where p.Name = some elements(list.Names)
		*/
		int filtroNumero = 0;
		BusquedaInfo busquedaInfo = (BusquedaInfo) bean;
		String q = "select l from " + persistentObject.getName() + " l ";
		
		if(busquedaInfo.contieneTagsBuscables() || busquedaInfo.filtraPorDescripcion() ||
				busquedaInfo.filtraPorTitulo()){
			q += " where ";
		}
		
		if(busquedaInfo.contieneTagsBuscables()){
			q += this.buildTagSearchQuery(busquedaInfo.getTagsBuscables());
			filtroNumero++;
		}
		if(busquedaInfo.filtraPorDescripcion()){
			if(filtroNumero > 0){
				q += " and ";
			}
			q += " l.descripcion like '%" + busquedaInfo.getDescripcion() + "%' ";
			filtroNumero++;
		}

		if(busquedaInfo.filtraPorTitulo()){
			if(filtroNumero > 0){
				q += " and ";
			}
			q += " l.titulo like '%" + busquedaInfo.getTitulo() + "%' ";
		}
		return q;
	}
	
	protected String buildTagSearchQuery(List<TagSearch> tagsSearch){
		int size = tagsSearch != null ? tagsSearch.size() : 0;
		String q = "";
		int current = 0;
		for(TagSearch ts: tagsSearch){
			if(size > 1 && current == 0){
				q += " '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
			} else {
				if(size == 1){
					q += " '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";	
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
