package ar.com.gsalazar.dtos {
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.BusquedaInfo")]
	public class BusquedaInfo {

		public var contenido:String;
		public var rating:Number;
		public var titulo:String;
		public var descripcion:String;
		public var visualizado:Number;
		public var tagsBuscables:IList;

	}
}
