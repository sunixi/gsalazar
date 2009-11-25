/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.List;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.CancionAcorde;
import ar.com.angelDurmiente.daos.CancionAcordeDAO;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;
import ar.com.angelDurmiente.services.CancionAcordeService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class CancionAcordeServiceImpl extends GenericServiceImpl implements CancionAcordeService{
	
	public List<CancionAcorde> buscarTodosPorContenido(String contenido) {
		return this.getCancionAcordeDAO().buscarTodosPorContenido(contenido);
	}

	public List<CancionAcorde> buscarTodosPorTitulo(String titulo) {
		return this.getCancionAcordeDAO().buscarTodosPorTitulo(titulo);
	}
	
	public List<CancionInfoDTO> buscarTodos(){
		return this.getCancionAcordeDAO().buscarTodos();
	}

	protected CancionAcordeDAO getCancionAcordeDAO(){
		return (CancionAcordeDAO) super.getGenericDAO();
	}

	public CancionAcorde buscarUnicoPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		return this.getCancionAcordeDAO().buscarUnicoPorTituloArtistaYAlbum(titulo, artista, album);
	}
	
	public CancionAcorde buscarUnicoONuloPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		return this.getCancionAcordeDAO().buscarUnicoONuloPorTituloArtistaYAlbum(titulo, artista, album);
	}
	
	public List<CancionAcorde> buscarTodosPorAcordes(List<Acorde> acordes){
		return this.getCancionAcordeDAO().buscarTodosPorAcordes(acordes);
	}
}
