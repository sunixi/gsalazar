package ar.com.eventos.cumples.beans{

	import mx.collections.IList;

/**
 *
 * TODO Comentar.
 *
 * @author	Guillermo Daniel Salazar.
 * @since	27/02/2010.
 * @email	guillesalazar@gmail.com.
 *
 */
[Bindable]
[RemoteClass(alias = "ar.com.productos.beans.Client")]
public class XVideo {

	public var nombre:String;
	public var descripcion:String;
	public var comentarios:IList;
	public var videoURL:String;

}
}