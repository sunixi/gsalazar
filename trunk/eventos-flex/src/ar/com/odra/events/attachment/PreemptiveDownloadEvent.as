package ar.com.odra.events.attachment {
	import flash.events.Event;
	
	import mx.collections.IList;
	
	/**
	 * Event sent to the preemtive downloader callback when all items are innitialized.
	 */
	public class PreemptiveDownloadEvent extends Event {
	    public static var COMPLETE:String = "complete";
		
		private var _loadedItems:IList;
		
		public function PreemptiveDownloadEvent(type:String, loadedItems:IList, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
			this._loadedItems = loadedItems;
		}

		/**
		 * The downloaded preemptive items.
		 */
		public function get loadedItems():IList {
			return _loadedItems;
		}
	}
}