package ar.com.eventos.cumples.beans{
	import mx.collections.IList;
	

/**
 *
 * TODO Comentar.
 *
 * @since	27/02/2010.
 *
 */
[Bindable]
[RemoteClass(alias = "ar.com.productos.beans.ClientType")]
public class Configuracion {

	public var textoInicio:String;
	
	public var descripcion:String;
	public var fotos:IList;
	public var foto:Foto;


}
}