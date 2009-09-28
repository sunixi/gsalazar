package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Comentario")]
	public class Comentario extends PersistentObject{

		public var nombre:String;
		public var comentario:String;
		public var rating:Number;
		public var validado:Boolean;

	}
}
