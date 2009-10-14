package ar.com.gsalazar.events {
	import ar.com.gsalazar.beans.Buscable;
	
	import flash.events.Event;
	
	import mx.collections.IList;

	public class ResultadoBusquedaEvent extends Event {
		
		public static const CHANGE_BUSCABLE_COMENTARIOS_EVENT:String = "changeBuscableComentarios";

		private var _buscables:IList;

		public function ResultadoBusquedaEvent(eventName:String, buscables:IList, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_buscables = buscables;
			return;
		}
		
		public function get buscables():IList{
			return _buscables;
		}
	}
}