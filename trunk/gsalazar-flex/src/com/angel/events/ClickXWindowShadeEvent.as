package com.angel.events {
	import com.angel.components.windowsShade.XWindowShade;
	
	import flash.events.Event;

	public class ClickXWindowShadeEvent extends Event {
		
		public static const CHANGE_STATE_EVENT:String = "clickXWindowShade";

		private var _xWindowShade:XWindowShade;

		public function ClickXWindowShadeEvent(eventName:String, xWindowShade:XWindowShade, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_xWindowShade = xWindowShade;
			return;
		}
		
		public function get xWindowShade():XWindowShade{
			return _xWindowShade;
		}
	}
}