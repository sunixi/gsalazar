package ar.com.gsalazar.beans {
	import flash.utils.ByteArray;
	
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.ProyectoDTO")]
	public class ProyectoDTO extends Buscable{

		public var imagen:ByteArray;
		public var desarrolladores:IList;
	 	
	}
}
