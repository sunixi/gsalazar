package ar.angelDurmiente.dtos {
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.angelDurmiente.dtos.CancionInfoDTO")]
	public class CancionInfoDTO {

		public var cancion:String;
		public var artista:String;
		public var textosDTO:IList;
		public var album:String;

		public function verTextoCompleto():void {
			
		}
		
	}
}
