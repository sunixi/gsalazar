package ar.com.gsalazar.dtos {
	import com.angel.beans.PersistentObject;
	
	import flash.utils.ByteArray;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.ContactoWebDTO")]
	public class ContactoWebDTO {

		public var nombre:String;
		public var descripcion:String;
		public var link:String;
		public var logo:ByteArray;

	}
}
