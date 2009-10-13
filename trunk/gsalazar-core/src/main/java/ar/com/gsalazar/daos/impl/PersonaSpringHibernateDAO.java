/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import org.hibernate.Query;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.PersonaDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class PersonaSpringHibernateDAO extends GenericSpringHibernateDAO<Persona, ObjectId> implements PersonaDAO {

	public PersonaSpringHibernateDAO() {
		super(Persona.class, ObjectId.class);
	}

	public List<Persona> buscarTodosPorApellido(String apellido) {
		return (List<Persona>) super.findAll("apellido", apellido);
	}

	public Persona buscarUnicoPorEmail(String email) {
		return super.findUnique("email", email);
	}

	public Persona buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}
	
	@SuppressWarnings("unchecked")
	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		int size = tagsSearch != null ? tagsSearch.size() : 0;
		String q = "select l from " + super.getPersistentClass().getName() + " l ";
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
		Query query = super.getSession().createQuery(q);
		return query.list();
	}
}
