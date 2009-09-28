package ar.com.gsalazar.events {
	import ar.com.gsalazar.beans.Buscable;
	
	import flash.events.Event;

	public class ChangeBuscableComentariosEvent extends Event {
		
		public static const CHANGE_BUSCABLE_COMENTARIOS_EVENT:String = "changeBuscableComentarios";

		private var _buscable:Buscable;

		public function ChangeBuscableComentariosEvent(eventName:String, buscable:Buscable, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_buscable = buscable;
			return;
		}
		
		public function get buscable():Buscable{
			return _buscable;
		}
	}
}