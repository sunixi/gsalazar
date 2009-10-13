package ar.com.gsalazar.beans {
	import flash.utils.ByteArray;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Persona")]
	public class Persona extends Buscable {

		public var imagenArray:ByteArray;
		public var nombre:String;
		public var apellido:String;
		public var email:String;

	}
}
