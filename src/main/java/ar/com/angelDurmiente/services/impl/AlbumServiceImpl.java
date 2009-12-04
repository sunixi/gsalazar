/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.AlbumDAO;
import ar.com.angelDurmiente.services.AlbumService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class AlbumServiceImpl extends GenericServiceImpl implements AlbumService {


	protected AlbumDAO getAlbumDAO(){
		return (AlbumDAO) super.getGenericDAO();
	}

	public Album buscarUnicoPorCancion(String cancion) {
		return this.getAlbumDAO().buscarUnicoPorCancion(cancion);
	}

	public Album buscarUnicoPorCancion(Cancion cancion) {
		return this.getAlbumDAO().buscarUnicoPorCancion(cancion);
	}

	public Album buscarUnicoPorTitulo(String titulo) {
		return this.getAlbumDAO().buscarUnicoPorTitulo(titulo);
	}
}
