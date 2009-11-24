/**
 * 
 */
package ar.com.angelDurmiente.services;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;
import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.dtos.TextoDTO;
import ar.com.angelDurmiente.helpers.CalendarHelper;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class DedicatoriaServiceTestCase extends AngelDurmienteBaseTestCase{

	@Autowired
	private DedicatoriaService dedicatoriaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TextoService textoService;

	@Test
	public void testCrearDedicatoriaValida(){
		Usuario dedicador = this.getUsuarioService().buscarUnicoPorNombreUsuario("angeldurmiente");
		Usuario dedicatario = this.getUsuarioService().buscarUnicoPorNombreUsuario("larubiamarie");
		Date desde = CalendarHelper.createDate(24, 11, 2009);
		Date hasta = CalendarHelper.createDate(1, 12, 2009);
		Texto texto = this.getTextoService()
			.buscarUnicoPorTitulo("Ricardo Montaner - Ganas de Ti - angeldurmiente - 1");

		Dedicatoria dedicatoria = this.getDedicatoriaService()
			.dedicarTexto(new TextoDTO(texto), dedicatario, dedicador, desde, hasta);
		assertNotNull("La dedicatoria debe ser NO nula.", dedicatoria);

		desde = CalendarHelper.createDate(21, 11, 2009);
		hasta = CalendarHelper.createDate(10, 12, 2009);
		List<Dedicatoria> dedicatorias = this.getDedicatoriaService().buscarTodosPorRangoFechas(desde, hasta);
		
		assertFalse("La lista de dedicatorias NO debe ser vacia.", dedicatorias.isEmpty());
		assertEquals("La cantidad de dedicatorias debe ser igual a 1.", 1, dedicatorias.size());
		Dedicatoria dedicatoriaRealizada = dedicatorias.get(0);
		assertTrue("La dedicatoria debe estar dedicada para larubiamarie.", dedicatoriaRealizada.estaDedicadoPara(dedicatario));
		assertTrue("La dedicatoria debe estar dedicada por angelDurmiente.", dedicatoriaRealizada.estaDedicadoPor(dedicador));
	}

	/**
	 * @return the dedicatoriaService
	 */
	public DedicatoriaService getDedicatoriaService() {
		return dedicatoriaService;
	}

	/**
	 * @param dedicatoriaService the dedicatoriaService to set
	 */
	public void setDedicatoriaService(DedicatoriaService dedicatoriaService) {
		this.dedicatoriaService = dedicatoriaService;
	}

	/**
	 * @return the usuarioService
	 */
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @return the textoService
	 */
	public TextoService getTextoService() {
		return textoService;
	}

	/**
	 * @param textoService the textoService to set
	 */
	public void setTextoService(TextoService textoService) {
		this.textoService = textoService;
	}
}
