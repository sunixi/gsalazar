/**
 * 
 */
package ar.com.gsalazar.daos.queryBuilder.impl;

import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.common.helpers.StringHelper;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ArticulosBusquedaInfoQueryBeanFactory extends BusquedaInfoQueryBeanFactory{

	public ArticulosBusquedaInfoQueryBeanFactory(){
		super();
	}

	@Override
	public String createQueryBean(Class<? extends PersistentObject> persistentObject, Object bean) {
		String q = super.createQueryBean(persistentObject, bean);
		BusquedaInfo busquedaInfo = (BusquedaInfo) bean;
		if(busquedaInfo.filtraPorContenido()){
			String contenido = busquedaInfo.getContenido();
			q += " and l.contenido like '%" + StringHelper.replaceAll(contenido, " ", "%") + "%' ";
		}
		return q;
	}
}
