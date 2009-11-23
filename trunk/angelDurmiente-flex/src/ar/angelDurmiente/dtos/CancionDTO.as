package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	import com.angel.locator.ServiceLocator;
	
	import flash.display.DisplayObject;
	
	import mx.collections.IList;
	import mx.core.Application;
	import mx.managers.PopUpManager;
	

	[Bindable]
	[RemoteClass(alias="ar.com.angelDurmiente.dtos.CancionDTO")]
	public class CancionDTO {

		public var cancion:String;
		public var artista:String;
		public var usuario:String;
		public var album:String;

		public function verTextoCompleto():void {
			
		}
		
	}
}
