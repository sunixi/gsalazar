package ar.com.gsalazar.beans {
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Articulo")]
	public class Articulo extends Buscable{

		public var contenido:String;
		public var fuentes:IList;

		public override function getContenido():String {
			return this.contenido;
		}  
 	
	}
}
