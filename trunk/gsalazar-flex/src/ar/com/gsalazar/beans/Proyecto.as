package ar.com.gsalazar.beans {
	import ar.com.gsalazar.popups.BuscablePopup;
	
	import flash.display.DisplayObject;
	import flash.utils.ByteArray;
	
	import mx.collections.IList;
	import mx.core.Application;
	import mx.managers.PopUpManager;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Proyecto")]
	public class Proyecto extends Buscable{

		public var imagenArray:ByteArray;
		public var desarrolladores:IList;
		public var estado:String;
		
		public override function stateName():String{
			return "proyectoState";
		}
		
		public override function buildDetallePopup(_displayObject:DisplayObject):void {
			var _popup:BuscablePopup = BuscablePopup(PopUpManager.createPopUp(_displayObject, BuscablePopup, true));
			_popup.height = Application.application.height * 0.8;
			_popup.width = Application.application.width * 0.5;
			_popup.x = (Application.application.width / 2) - _popup.width / 2;
			_popup.y = (Application.application.height / 2) - _popup.height / 2;
			_popup.buscable = this;
			PopUpManager.bringToFront(_popup);
		} 	
		
		public override function getFullTitulo():String {
			return "Proyecto - " + super.getFullTitulo();
		}
		
		public override function buscableName():String{
			return "Proyecto";
		}
		
		public override function resultImageName():Class{
			[Embed(source='images/proyecto.png')]
			var a:Class; 
			return a;
		}
	}
}
