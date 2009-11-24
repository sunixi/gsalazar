/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.DedicatoriaDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class DedicatoriaSpringHibernateDAO extends GenericSpringHibernateDAO<Dedicatoria, ObjectId> implements DedicatoriaDAO {

	public DedicatoriaSpringHibernateDAO() {
		super(Dedicatoria.class, ObjectId.class);
	}

	public List<Dedicatoria> buscarTodosPorDedicador(Usuario dedicador) {
		return (List<Dedicatoria>) super.findAll("dedicador", dedicador);
	}

	public List<Dedicatoria> buscarTodosPorDedicatorio(Usuario dedicatorio) {
		return (List<Dedicatoria>) super.findAll("dedicatorio", dedicatorio);
	}

	public List<Dedicatoria> buscarTodosPorRangoFechas(Date desde, Date hasta) {
		return (List<Dedicatoria>) super.findAllByCriteria(
				Restrictions.between("desde", desde, desde),
				Restrictions.between("hasta", desde, desde)
		);
	}

	public List<Dedicatoria> buscarTodosPorTexto(Texto texto) {
		return (List<Dedicatoria>) super.findAll("texto", texto);
	}

	public List<Dedicatoria> buscarTodosPorUsuarioTexto(String nombreUsuario) {
		return (List<Dedicatoria>) super.findAll("texto.usuario.name", nombreUsuario);
	}
}
