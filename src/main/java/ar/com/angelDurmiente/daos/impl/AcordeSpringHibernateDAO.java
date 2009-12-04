/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.daos.AcordeDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class AcordeSpringHibernateDAO extends GenericSpringHibernateDAO<Acorde, ObjectId> implements AcordeDAO {

	public AcordeSpringHibernateDAO() {
		super(Acorde.class, ObjectId.class);
	}

	public List<Acorde> buscarTodosPorNombre(String nombre) {
		return (List<Acorde>) super.findAll("nombre", nombre);
	}

	public Acorde buscarUnicoONuloPorNombreYOpcion(String nombre, int opcion) {
		Map<String, Object> propertiesValues = new HashMap<String, Object>();
		propertiesValues.put("nombre", nombre);
		propertiesValues.put("opcion", opcion);
		return super.findUniqueOrNull(propertiesValues);
	}

	public Acorde buscarUnicoPorNombreYOpcion(String nombre, int opcion) {
		Map<String, Object> propertiesValues = new HashMap<String, Object>();
		propertiesValues.put("nombre", nombre);
		propertiesValues.put("opcion", opcion);
		return super.findUnique(propertiesValues);
	}	
}
