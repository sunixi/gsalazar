package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	
	import flash.utils.ByteArray;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.PersonaDTO")]
	public class PersonaDTO extends PersistentObject{

		public var imagen:ByteArray;
		public var nombre:String;
		public var apellido:String;
		public var email:String;

	}
}
