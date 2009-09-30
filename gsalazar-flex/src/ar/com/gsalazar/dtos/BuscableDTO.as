package ar.com.gsalazar.dtos {
	import ar.com.gsalazar.model.ApplicationLocator;
	import ar.com.gsalazar.popups.BuscablePopup;
	
	import com.angel.beans.PersistentObject;
	import com.angel.components.rankeable.Rankeable;
	import com.angel.components.tagCloud.TagSearch;
	import com.angel.locator.ServiceLocator;
	
	import flash.display.DisplayObject;
	
	import mx.collections.IList;
	import mx.core.Application;
	import mx.managers.PopUpManager;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.dtos.BuscableDTO")]
	public class BuscableDTO implements Rankeable{

		public var titulo:String;
		public var descripcion:String;
		public var tagsBuscables:IList;
		public var comentarios:IList;
		public var visualizado:Number;

		public function getLabel():String {
			return this.titulo;
		}
		
		public function getDescription():String {
			return this.descripcion;
		}
		
		public function getContenido():String {
			return this.descripcion;
		}
		
		public function getRanking():Number{
			var raking:Number = 0;
			var total:Number = 0;
			if(comentarios != null && comentarios.length > 0){
				for each(var c:Comentario in comentarios){
					raking += c.rating;
					total++;
				}
			} else {
				return 0;
			}
			var rating:Number = raking / total;
			return rating;
		}
		
		public function getTagsSearchString():String {
			var tags:String = "";
			for each(var b:TagSearch in this.tagsBuscables){
				tags += b.label + ' ';
			}
			return tags;
		}
		
		public function buildDetallePopup(_displayObject:DisplayObject):void {
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
		
		public function cantidadComentarios():Number {
			return this.comentarios != null ? this.comentarios.length : 0;
		}
	}
}
