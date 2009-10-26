/**
 * 
 */
package ar.com.gsalazar.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class SolicitudCV extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String motivo;
	@Column(nullable = false)
	private boolean enviado = false;
	@Column(nullable = true)
	private Date fechaEnviado;
	
	public SolicitudCV(String email, String motivo){
		super();
		this.setEmail(email);
		this.setMotivo(motivo);
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}
	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	/**
	 * @return the enviado
	 */
	public boolean isEnviado() {
		return enviado;
	}
	/**
	 * @param enviado the enviado to set
	 */
	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}
	/**
	 * @return the fechaEnviado
	 */
	public Date getFechaEnviado() {
		return fechaEnviado;
	}
	/**
	 * @param fechaEnviado the fechaEnviado to set
	 */
	public void setFechaEnviado(Date fechaEnviado) {
		this.fechaEnviado = fechaEnviado;
	}
}
