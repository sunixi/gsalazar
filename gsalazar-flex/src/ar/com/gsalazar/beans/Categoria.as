package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Categoria")]
	public class Categoria extends PersistentObject {

		public var nombre:String;
		public var descripcion:String;
		public var subCategorias:IList;
		public var items:IList;

	}
}
