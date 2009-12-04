/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.daos.ArtistaDAO;

import com.angel.architecture.exceptions.NonBusinessException;
import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ArtistaSpringHibernateDAO extends GenericSpringHibernateDAO<Artista, ObjectId> implements ArtistaDAO {

	public ArtistaSpringHibernateDAO() {
		super(Artista.class, ObjectId.class);
	}

	public List<Artista> buscarTodosPorAlbum(String album) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Artista> buscarTodosPorAlbum(Album album) {
		return (List<Artista>) super.findAllByCriteria(Restrictions.in("albums", new Object[]{album}));
	}

	public Artista buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}

	public Artista buscarUnicoPorNombreYAlbum(String nombre, Album album) {
		List<Artista> artistas = (List<Artista>) super.findAllByCriteria(
				Restrictions.eq("nombre", nombre),
				Restrictions.in("albums", new Object[]{album})
				);
		if(artistas.size() > 1){
			throw new NonBusinessException("No se encontro un unico artista con el nombre [" + nombre + "] y el album [" + album.getNombre() + "].");
		} else {
			return artistas.get(0);
		}
	}
	
	public Artista buscarUnicoONuloPorNombre(String nombre){
		return super.findUniqueOrNull("nombre", nombre);
	}
}
