package ar.com.gsalazar.events {
	import ar.com.gsalazar.beans.Buscable;
	
	import flash.events.Event;
	
	import mx.collections.IList;

	public class BusquedaInfoRealizadaEvent extends Event {
		
		public static const CHANGE_BUSCABLE_COMENTARIOS_EVENT:String = "busquedaInfoRealizada";

		private var _tagsSearchs:IList;

		public function BusquedaInfoRealizadaEvent(eventName:String, tagsSearchs:IList, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_tagsSearchs = tagsSearchs;
			return;
		}
		
		public function get tagsSearchs():IList{
			return _tagsSearchs;
		}
	}
}