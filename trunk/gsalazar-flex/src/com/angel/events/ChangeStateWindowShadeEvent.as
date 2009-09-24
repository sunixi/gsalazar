package com.angel.events {
	import flash.events.Event;

	public class ChangeStateWindowShadeEvent extends Event {
		
		public static const CHANGE_STATE_EVENT:String = "changeState";

		private var _opened:Boolean;

		public function ChangeStateWindowShadeEvent(eventName:String, opened:Boolean, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_opened = opened;
			return;
		}
		
		public function get opened():Boolean{
			return _opened;
		}
	}
}