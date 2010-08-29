package ar.com.odra.events.attachment {
	import flash.events.Event;
	
	import mx.collections.IList;

	/**
	 * Represents an event dispatched by @see ar.com.mindset.flexy.util.attachment.LoaderBuffer when a page download is complete.
	 */
	public class LoaderBufferEvent extends Event {
		
		/**
		 * @eventType pageDownloaded
		 */
	    public static var PAGE_DOWNLOADED:String = "pageDownloaded";
	    public static var ERROR_PAGE_DOWNLOAD:String = "errorPageDownload";
	    
	    private var _pageNumber:int;
	    private var _loadedItems:IList;
		
		/**
		 * 
		 * @param pageNumber Zero-based number of the completed page.
		 * @param loadedItems The attachment items in the completed page.
		 */
		public function LoaderBufferEvent(type:String, pageNumber:int, loadedItems:IList, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
			this._pageNumber = pageNumber;
			this._loadedItems = loadedItems;
		}
		
		/**
		 * Zero-based number of the completed page
		 */
		public function get pageNumber():int {
			return _pageNumber;
		}
		
		/**
		 * The attachment items in the completed page.
		 */
		public function get loadedItems():IList {
			return _loadedItems;
		}
	}
}