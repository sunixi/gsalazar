/**
 * 
 */
package ar.com.gsalazar.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.angel.architecture.persistence.base.PersistentObject;
import com.mysql.jdbc.Blob;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Categoria extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String descripcion;
	@OneToOne(optional = true)
	private Categoria subCategoria;
	@OneToMany()
	private List<ItemCategoria> items;

	public Categoria(String nombre, String descripcion, Categoria subCategoria){
		this(nombre, descripcion);
		this.setSubCategoria(subCategoria);
	}
	
	public Categoria(String nombre, String descripcion){
		super();
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setItems(new ArrayList<ItemCategoria>());
	}
	
	public Categoria(){
		super();
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the subCategoria
	 */
	public Categoria getSubCategoria() {
		return subCategoria;
	}
	/**
	 * @param subCategoria the subCategoria to set
	 */
	public void setSubCategoria(Categoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public boolean hasSubCategoria(){
		return this.getSubCategoria() != null;
	}
	
	public void agregarItemCategoria(ItemCategoria itemCategoria){
		this.getItems().add(itemCategoria);
	}
	
	public void agregarItemCategoria(String nombre, String descripcion, String fileName, Blob imagen){
		ItemCategoria itemCategoria = new ItemCategoria();
		itemCategoria.setDescripcion(descripcion);
		itemCategoria.setNombre(nombre);
		itemCategoria.setImagenItemCategoria(fileName, imagen);
		this.agregarItemCategoria(itemCategoria);
	}

	public void contieneItemCategoria(ItemCategoria itemCategoria){
		this.getItems().remove(itemCategoria);
	}
	
	public void eliminarItemCategoria(ItemCategoria itemCategoria){
		this.getItems().remove(itemCategoria);
	}
	
	/**
	 * @return the items
	 */
	public List<ItemCategoria> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<ItemCategoria> items) {
		this.items = items;
	}
}
