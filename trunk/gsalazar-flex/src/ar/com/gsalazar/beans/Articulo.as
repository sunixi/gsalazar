package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	import com.angel.components.rankeable.Rankeable;
	
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Articulo")]
	public class Articulo extends PersistentObject implements Rankeable{

		public var nombre:String;
		public var titulo:String;
		public var contenido:String;
		public var descripcion:String;
		public var fuentes:IList;
		public var tagsBuscables:IList;

		public function getLabel():String {
			return this.nombre;
		}
		
		public function getDescription():String {
			return this.descripcion;
		} 	
	}
}
