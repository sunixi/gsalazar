package ar.com.gsalazar.dtos {
	import flash.utils.ByteArray;
	
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.ProyectoDTO")]
	public class ProyectoDTO extends BuscableDTO {

		public var imagen:ByteArray;
		public var desarrolladores:IList;
	 	
	}
}
