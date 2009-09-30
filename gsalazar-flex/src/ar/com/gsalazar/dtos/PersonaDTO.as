package ar.com.gsalazar.dtos {
	import flash.utils.ByteArray;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.PersonaDTO")]
	public class PersonaDTO extends BuscableDTO {

		public var imagen:ByteArray;
		public var nombre:String;
		public var apellido:String;
		public var email:String;

	}
}
