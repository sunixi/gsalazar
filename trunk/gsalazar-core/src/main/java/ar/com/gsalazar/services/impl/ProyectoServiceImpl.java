/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.daos.ProyectoDAO;
import ar.com.gsalazar.dtos.ProyectoDTO;
import ar.com.gsalazar.services.ProyectoService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class ProyectoServiceImpl extends GenericServiceImpl implements ProyectoService{

	protected ProyectoDAO getProyectoDAO(){
		return (ProyectoDAO) super.getGenericDAO();
	}

	public List<ProyectoDTO> buscarTodosPorCantidadDesarrolladores(int cantidad) {
		List<Proyecto> proyectos = this.getProyectoDAO().buscarTodosPorCantidadDesarrolladores(cantidad);
		List<ProyectoDTO> proyectosDTO = new ArrayList<ProyectoDTO>();
		for(Proyecto p: proyectos){
			proyectosDTO.add(new ProyectoDTO(p));
		}
		return proyectosDTO;
	}

	public List<ProyectoDTO> buscarTodos() {
		Collection<Proyecto> proyectos = this.getProyectoDAO().findAll();
		List<ProyectoDTO> proyectosDTO = new ArrayList<ProyectoDTO>();
		for(Proyecto o: proyectos){
			proyectosDTO.add(new ProyectoDTO(o));
		}
		return proyectosDTO;
	}	
}
