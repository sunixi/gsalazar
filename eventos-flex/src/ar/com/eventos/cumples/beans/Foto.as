package ar.com.eventos.cumples.beans{

	import ar.com.odra.attachments.beans.ImageAttachment;
	
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
public class Foto {

	public var nombre:String;
	public var descripcion:String;
	public var foto:ImageAttachment;
	public var comentarios:IList;
	public var fecha:Date;

	public function fueBajada():Boolean {
		return foto.wasDownloaded();
	}
	
	public function agregarComentario(comentario:Comentario):void {
		comentarios.addItem(comentario);
	}
}
}