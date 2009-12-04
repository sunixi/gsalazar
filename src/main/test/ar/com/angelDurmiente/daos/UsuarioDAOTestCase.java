/**
 * 
 */
package ar.com.angelDurmiente.daos;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.factories.UsuarioFactory;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class UsuarioDAOTestCase extends AngelDurmienteBaseTestCase{

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	public void testCrearUsuarioValido(){
		Usuario usuario = UsuarioFactory.crearUsuario();
		this.getUsuarioDAO().persist(usuario);
		
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
