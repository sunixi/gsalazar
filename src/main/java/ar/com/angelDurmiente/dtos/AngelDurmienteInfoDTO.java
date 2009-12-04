/**
 * 
 */
package ar.com.angelDurmiente.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class AngelDurmienteInfoDTO implements Serializable {

	private static final long serialVersionUID = 6936830093090143041L;

	private long usuarios;
	private long canciones;
	private long cancionesConAcordes;
	private long poemasYPoesias;
	private long usuariosConectados;
	private long historiasConMoralejas;
	private long tusHistorias;
	private Date fecha;
	
	public AngelDurmienteInfoDTO(){
		super();
	}

	/**
	 * @return the usuarios
	 */
	public long getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(long usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the canciones
	 */
	public long getCanciones() {
		return canciones;
	}

	/**
	 * @param canciones the canciones to set
	 */
	public void setCanciones(long canciones) {
		this.canciones = canciones;
	}

	/**
	 * @return the cancionesConAcordes
	 */
	public long getCancionesConAcordes() {
		return cancionesConAcordes;
	}

	/**
	 * @param cancionesConAcordes the cancionesConAcordes to set
	 */
	public void setCancionesConAcordes(long cancionesConAcordes) {
		this.cancionesConAcordes = cancionesConAcordes;
	}

	/**
	 * @return the poemasYPoesias
	 */
	public long getPoemasYPoesias() {
		return poemasYPoesias;
	}

	/**
	 * @param poemasYPoesias the poemasYPoesias to set
	 */
	public void setPoemasYPoesias(long poemasYPoesias) {
		this.poemasYPoesias = poemasYPoesias;
	}

	/**
	 * @return the usuariosConectados
	 */
	public long getUsuariosConectados() {
		return usuariosConectados;
	}

	/**
	 * @param usuariosConectados the usuariosConectados to set
	 */
	public void setUsuariosConectados(long usuariosConectados) {
		this.usuariosConectados = usuariosConectados;
	}

	/**
	 * @return the historiasConMoralejas
	 */
	public long getHistoriasConMoralejas() {
		return historiasConMoralejas;
	}

	/**
	 * @param historiasConMoralejas the historiasConMoralejas to set
	 */
	public void setHistoriasConMoralejas(long historiasConMoralejas) {
		this.historiasConMoralejas = historiasConMoralejas;
	}

	/**
	 * @return the tusHistorias
	 */
	public long getTusHistorias() {
		return tusHistorias;
	}

	/**
	 * @param tusHistorias the tusHistorias to set
	 */
	public void setTusHistorias(long tusHistorias) {
		this.tusHistorias = tusHistorias;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
