package ar.com.gsalazar.beans {
	import ar.com.gsalazar.stateless.Stateless;
	
	import com.angel.beans.PersistentObject;
	import com.angel.components.rankeable.Rankeable;
	import com.angel.components.tagCloud.TagSearch;
	
	import flash.display.DisplayObject;
	
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Buscable")]
	public class Buscable extends PersistentObject implements Rankeable, Stateless{

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

		}
		
		public function cantidadComentarios():Number {
			return this.comentarios != null ? this.comentarios.length : 0;
		}
		
		public function stateName():String{
			return "";
		}
		
		public function getFullTitulo():String {
			return this.titulo;
		}
	}
}
