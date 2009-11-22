/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.factories.CancionFactory;
import ar.com.angelDurmiente.factories.UsuarioFactory;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class CancionDAOTestCase extends AngelDurmienteBaseTestCase{

	@Autowired
	private CancionDAO cancionDAO;
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	public void testCrearCancionValido(){
		Usuario usuario = this.beforeTestCrearCancionValido();
		Cancion cancion = CancionFactory.crearCancion(usuario);
		String titulo = cancion.getTitulo();
		this.getCancionDAO().persist(cancion);

		List<Cancion> cancionesCargadas = this.getCancionDAO().buscarTodosPorTitulo(titulo);		
		assertTrue("Las Canciones cargadas con el titulo [" + titulo + "] debe ser una.", cancionesCargadas.size() == 1);
	//	Cancion cancionCargada = cancionesCargadas.get(0);
		//assertEquals("El usuario de la cancion cargada debe ser igual.", cancion.getUsuario().getName(), cancionCargada.getUsuario().getName());
	}
	
	public Usuario beforeTestCrearCancionValido(){
		Usuario usuario = UsuarioFactory.crearUsuario();
		return this.getUsuarioDAO().persist(usuario);
	}

	/**
	 * @return the cancionDAO
	 */
	public CancionDAO getCancionDAO() {
		return cancionDAO;
	}

	/**
	 * @param cancionDAO the cancionDAO to set
	 */
	public void setCancionDAO(CancionDAO cancionDAO) {
		this.cancionDAO = cancionDAO;
	}

	/**
	 * @return the usuarioDAO
	 */
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	/**
	 * @param usuarioDAO the usuarioDAO to set
	 */
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
}
