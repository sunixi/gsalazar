package ar.angelDurmiente.events{
	import flash.events.Event;

	public class AttachInfoEvent extends Event {
		
		private var _object:Object;

		public static const ATTACH_INFO:String ="attachInfo";

		public function AttachInfoEvent(eventName:String, info:Object, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_object = info;
			return;
		}
		
		public function get info():Object{
			return _object;
		}
		
		public function hasInfo():Boolean {
			return _object != null && _object.toString().length > 0;
		}
	}
}