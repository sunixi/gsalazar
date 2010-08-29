package ar.com.odra.events.attachment
{
	import flash.events.Event;

	public class AttachmentEvent extends Event {
	    public static var DATA_READY:String = "dataReady";
	    public static var LOAD_ERROR:String = "loadError";
	    public static var DOWNLOAD_COMPLETE:String = "downloadComplete";
	    public static var STATUS_CHANGED:String = "statusChanged";
		
		public function AttachmentEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
	}
}