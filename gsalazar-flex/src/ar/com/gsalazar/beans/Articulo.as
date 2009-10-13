package ar.com.gsalazar.beans {
	import ar.com.gsalazar.model.ApplicationLocator;
	import ar.com.gsalazar.popups.BuscablePopup;
	
	import com.angel.locator.ServiceLocator;
	
	import flash.display.DisplayObject;
	
	import mx.collections.IList;
	import mx.core.Application;
	import mx.managers.PopUpManager;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Articulo")]
	public class Articulo extends Buscable{

		public var contenido:String;
		public var fuentes:IList;

		public override function getContenido():String {
			return this.contenido;
		}  
		
		public override function buildDetallePopup(_displayObject:DisplayObject):void {
			ServiceLocator.getServiceWithCallback("articuloService", updateVisualizarArticuloCallbackFunction).updateVisualizarArticulo(this.titulo);
			var _popup:BuscablePopup = PopUpManager.createPopUp(_displayObject, BuscablePopup, true) as BuscablePopup;
			_popup.height = Application.application.height * 0.95;
			_popup.width = Application.application.width * 0.85;
			_popup.x = (Application.application.width / 2) - _popup.width / 2;
			_popup.y = (Application.application.height / 2) - _popup.height / 2;
			_popup.buscable = this;
			PopUpManager.bringToFront(_popup);
		}
		
		private function updateVisualizarArticuloCallbackFunction(_buscable:Buscable):void {
			ApplicationLocator.instance.reemplazarArticulo(_buscable as Articulo);
		}
 		
 		public override function stateName():String{
			return "articuloState";
		}
		
		public override function getFullTitulo():String {
			return "Articulo - " + super.getFullTitulo();
		}
	}
}
