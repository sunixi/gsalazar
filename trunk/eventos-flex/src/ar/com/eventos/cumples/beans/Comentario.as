package ar.com.eventos.cumples.beans{

/**
 *
 * TODO Comentar.
 *
 * @since	27/02/2010.
 *
 */
[Bindable]
[RemoteClass(alias = "ar.com.productos.beans.ClientInformation")]
public class Comentario {

	public var comentario:String;
	public var usuario:String;
	public var fecha:Date = new Date();
	public var email:String;

}
}