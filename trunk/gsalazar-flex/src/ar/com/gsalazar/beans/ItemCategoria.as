package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.ItemCategoria")]
	public class ItemCategoria extends PersistentObject {

		public var nombre:String;
		public var descripcion:String;
		public var comienzo:Date;
		public var fin:Date;

	}
}
