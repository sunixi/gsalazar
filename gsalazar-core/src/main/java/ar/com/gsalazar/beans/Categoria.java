/**
 * 
 */
package ar.com.gsalazar.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Categoria extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	@Column(nullable = false, length = 50, unique = true)
	private String nombre;
	@Column(nullable = false, length = 250)
	private String descripcion;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Categoria> subCategorias;
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(value = "comienzo desc, fin desc")
	private List<ItemCategoria> items;

	public Categoria(String nombre, String descripcion, List<Categoria> subCategorias){
		this();
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.getSubCategorias().addAll(subCategorias);
	}
	
	public Categoria(String nombre, String descripcion){
		this();
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
	}
	
	public Categoria(){
		super();
		this.setSubCategorias(new ArrayList<Categoria>());
		this.setItems(new ArrayList<ItemCategoria>());
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

	public void addSubCategoria(Categoria subCategoria){
		this.getSubCategorias().add(subCategoria);
	}

	public void removeSubCategoria(Categoria subCategoria){
		this.getSubCategorias().remove(subCategoria);
	}

	/**
	 * @return the subCategorias
	 */
	public List<Categoria> getSubCategorias() {
		return subCategorias;
	}

	/**
	 * @param subCategorias the subCategorias to set
	 */
	public void setSubCategorias(List<Categoria> subCategorias) {
		this.subCategorias = subCategorias;
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

	public void addItemCategoria(ItemCategoria itemCategoria){
		this.getItems().add(itemCategoria);
	}
	
	public void addItemCategoria(String nombre, String descripcion, Date comienzo, Date fin){
		ItemCategoria itemCategoria = new ItemCategoria();
		itemCategoria.setNombre(nombre);
		itemCategoria.setDescripcion(descripcion);
		itemCategoria.setComienzo(comienzo);
		itemCategoria.setFin(fin);
		this.addItemCategoria(itemCategoria);
	}
	
	public void addItemCategoria(String nombre, String descripcion){
		this.addItemCategoria(nombre, descripcion, null, null);
	}
}
