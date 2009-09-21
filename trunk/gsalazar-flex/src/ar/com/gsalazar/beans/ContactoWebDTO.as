package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.ContactoWeb")]
	public class ContactoWebDTO extends PersistentObject {

		public var nombre:String;
		public var descripcion:String;
		public var link:String;
		public var logo:Object;

	}
}
