/**
 * 
 */
package ar.com.gsalazar.daos.queryBuilder;

import com.angel.architecture.persistence.base.PersistentObject;


/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface QueryBeanFactory {

	public String createQueryBean(Class<? extends PersistentObject> persistentObject, Object bean);

}
