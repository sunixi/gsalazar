package ar.angelDurmiente.dtos {
	import com.angel.beans.PersistentObject;
	import com.angel.locator.ServiceLocator;
	
	import flash.display.DisplayObject;
	
	import mx.collections.IList;
	import mx.core.Application;
	import mx.managers.PopUpManager;
	

	[Bindable]
	[RemoteClass(alias="ar.com.angelDurmiente.dtos.TextoDTO")]
	public class TextoDTO {

		public var usuario:String;
		public var subido:Date;

		public function verTextoCompleto():void {
			
		}
		
	}
}
