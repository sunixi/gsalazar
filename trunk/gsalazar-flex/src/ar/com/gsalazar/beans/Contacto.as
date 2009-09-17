package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Contacto")]
	public class Contacto extends PersistentObject {

		public var nombres:String;
		public var apellido:String;
		public var nombreCompania:String;
		public var email:String;
		public var telefono:String;
		public var direccion:String;
		public var localidad:String;
		public var asunto:String;
		public var descripcion:String;
			

	}
}
