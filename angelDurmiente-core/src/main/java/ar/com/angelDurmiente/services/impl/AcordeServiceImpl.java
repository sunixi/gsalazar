/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.List;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.daos.AcordeDAO;
import ar.com.angelDurmiente.services.AcordeService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class AcordeServiceImpl extends GenericServiceImpl implements AcordeService {

	protected AcordeDAO getAcordeDAO(){
		return (AcordeDAO) super.getGenericDAO();
	}

	public List<Acorde> buscarTodosPorNombre(String nombre) {
		return this.getAcordeDAO().buscarTodosPorNombre(nombre);
	}

	public Acorde buscarUnicoONuloPorNombreYOpcion(String nombre, int opcion) {
		return this.getAcordeDAO().buscarUnicoONuloPorNombreYOpcion(nombre, opcion);
	}

	public Acorde buscarUnicoPorNombreYOpcion(String nombre, int opcion) {
		return this.getAcordeDAO().buscarUnicoPorNombreYOpcion(nombre, opcion);
	}
}
