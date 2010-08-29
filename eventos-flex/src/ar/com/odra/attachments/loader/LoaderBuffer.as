package ar.com.odra.attachments.loader {
	import ar.com.odra.attachments.beans.IAttachment;
	import ar.com.odra.events.attachment.AttachmentEvent;
	import ar.com.odra.events.attachment.LoaderBufferEvent;
	import ar.com.odra.events.attachment.PreemptiveDownloadEvent;
	import ar.com.odra.utils.ListUtil;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.ProgressEvent;
	
	import mx.collections.ArrayList;
	import mx.collections.IList;

	/**
	 * Dispatched when an item could not be downloaded.
	 * @eventType ar.com.mindset.flexy.event.LoaderBufferEvent.
	 */
	[Event(name="errorPageDownload", type="ar.com.odra.events.attachment.LoaderBufferEvent")]
	/**
	 * Dispatched when <code>pageSize</code> or all items have been loaded, whatever happens first.
	 * @eventType ar.com.mindset.flexy.event.LoaderBufferEvent.PAGE_DOWNLOADED
	 */
	[Event(name="pageDownloaded", type="ar.com.odra.events.attachment.LoaderBufferEvent")]

	/**
	 * Indicates progress for all toLoad items.
	 */
	[Event(name="progress", type="flash.events.ProgressEvent")]

	/**
	 * Indicates progress only for items in the current page.
	 */
	[Event(name="pageProgress", type="flash.events.ProgressEvent")]
	
	/**
	 * Allows a set of @see ar.com.mindset.flexy.bean.attachment.AbstractAttachment objects to be downloaded progressively, by setting a pageSize.
	 * This object dispatches events as it completes downloads, both for the active page and globally.
	 * It also supports adding higher priority items, see the @see addPriorizedItem and @see addPriorizedItems methods.
	 */
	public class LoaderBuffer extends EventDispatcher {
		
		public static const PAGE_PROGRESS:String = "pageProgress";
		
		//private static var logger:ILogger = Log4j.getLogger(LoaderBuffer);
		
		private var _maxPageSize:int;
		
		private var _maxConcurrentRequests:int;

		private var _finishedPages:int = 0;
		
		private var _additionalParams:String;
		
		/**
		 * Items waiting to be downloaded.
		 */
		[ArrayElementType("ar.com.odra.attachments.beans.IAttachment")]
		public var toLoad:IList = new ArrayList();

		/**
		 * Items being currently downloaded.
		 */
		[ArrayElementType("ar.com.odra.attachments.beans.IAttachment")]
		public var loading:IList = new ArrayList();

		/**
		 * Items already downloaded.
		 */
		[ArrayElementType("ar.com.odra.attachments.beans.IAttachment")]
		public var loaded:IList = new ArrayList();
		
		private var depthLevel:int;
		private var preemptiveLoader:LoaderBuffer;
		private var isLoading:Boolean = false;
		
		/**
		 * The max number of items to be fetched in a block. Items of the next page will not be requested until all from the current page have been received.
		 */
		public function get maxPageSize():int {
			return _maxPageSize;
		}

		/**
		 * The maximum number of requests to be active simultaneusly.
		 */
		public function get maxConcurrentRequests():int {
			return _maxConcurrentRequests;
		}

		/**
		 * The number of pages that have already been received.
		 */
		public function get finishedPages():int {
			return _finishedPages;
		}

		/**
		 * Additionals params to be passed to each attachment download url.
		 */
		public function get additionalParams():String {
			return _additionalParams;
		}

		/**
		 * The number of items that have already been received.
		 */
		public function get totalLoadedItems():int {
			return loaded.length + this.finishedPages * this.maxPageSize;
		}
		
		/**
		 * The number of items in the current page.
		 */
		public function get currentPageSize():int {
			return toLoad.length + loading.length + loaded.length < this.maxPageSize ? toLoad.length + loading.length + loaded.length : this.maxPageSize;
		}
		
		/**
		 * Builds a new LoaderBuffer object with the passed params configuration.
		 * 
		 * @param _maxPageSize The max number of items to be fetched in a block. Items of the next page will not be requested until all from the current page have been received.
		 * @param _maxConcurrentRequests The maximum number of requests to be active simultaneusly.
		 * @param _additionalParams Additionals params to be passed to each attachment download url.
		 */
		public function LoaderBuffer(_maxPageSize:int = 20, _maxConcurrentRequests:int = 5, _additionalParams:String = "", _depthLevel:int = 0, target:IEventDispatcher=null) {
			super(target);
			this._maxPageSize = _maxPageSize;
			this._maxConcurrentRequests = _maxConcurrentRequests;
			this._additionalParams = _additionalParams;
			this.depthLevel = _depthLevel;
		}
		
		/**
		 * Suspends the current page download and resumes it when <b>item</b> finishes downloading.
		 */
		public function addPriorizedItem(item:IAttachment, callbackFunction:Function = null):void {
			var items:IList = new ArrayList();
			items.addItem(item);
			addPriorizedItems(items, callbackFunction);
		}
		
		/**
		 * Suspends the current page download and resumes it when all <b>items</b> finish downloading.
		 */
		public function addPriorizedItems(items:IList, callbackFunction:Function = null):void {
			if(preemptiveLoader != null) {
				preemptiveLoader.addPriorizedItems(items, callbackFunction);
			} else {
				preemptiveLoader = new LoaderBuffer(items.length, this.maxConcurrentRequests, this.additionalParams, depthLevel + 1);
				ListUtil.addAll(preemptiveLoader.toLoad, items);
				preemptiveLoader.addEventListener(LoaderBufferEvent.PAGE_DOWNLOADED, function(event:LoaderBufferEvent):void {
					if(callbackFunction != null) {
						callbackFunction(new PreemptiveDownloadEvent(PreemptiveDownloadEvent.COMPLETE, event.loadedItems));
					}
					preemptiveLoader = null;
					if(isLoading) {
						doLoadPage();
					}
				});
				preemptiveLoader.loadPage();
			}
		}
		
		/**
		 * Starts downloading up to <b>maxPageSize</b> items. When they are downloaded, <b>pageDownloaded</b> event is dispatched.
		 * Following pages should be requested on the <b>pageDownloaded</b> event handler.
		 */
		public function loadPage():void {
			isLoading = true;
			if(toLoad.length == 0) {
				pageDownloaded();
			} else {
				doLoadPage();
			}
		}
		
		private function doLoadPage():void {
			while(preemptiveLoader == null && loading.length < this.maxConcurrentRequests && loaded.length + loading.length < this.maxPageSize && toLoad.length > 0) {
				downloadNextItem();
			}
		}

		private function pageDownloaded():void {
			this._finishedPages++;
			var loadedItems:IList = new ArrayList();
			ListUtil.addAll(loadedItems, loaded);
			loaded.removeAll();
			isLoading = false;
			dispatchEvent(new LoaderBufferEvent(LoaderBufferEvent.PAGE_DOWNLOADED, this.finishedPages - 1, loadedItems));
		}
		
		private function downloadNextItem():void {
			var item:IAttachment = IAttachment(toLoad.getItemAt(0));
			trace("About to download " + item.url);
			loading.addItem(item);
			ListUtil.remove(toLoad, item);
			item.addEventListener(AttachmentEvent.DOWNLOAD_COMPLETE, itemDownloaded);
			item.addEventListener(AttachmentEvent.LOAD_ERROR, itemErrorDownload);
			item.download(additionalParams);
		}
		
		private function itemErrorDownload(event:AttachmentEvent):void {
			var errorEvent:LoaderBufferEvent = new LoaderBufferEvent(LoaderBufferEvent.ERROR_PAGE_DOWNLOAD, this.finishedPages - 1, loaded);
			dispatchEvent(errorEvent);
		}
		
		private function itemDownloaded(event:AttachmentEvent):void {
			var item:IAttachment = IAttachment(event.target);
			item.removeEventListener(AttachmentEvent.DOWNLOAD_COMPLETE, itemDownloaded);
			loaded.addItem(item);
			ListUtil.remove(loading, item);
			var kItems:int = toLoad.length + loaded.length + loading.length;
			trace("Finished downloading " + item.url + " (" + loaded.length + "/" + kItems + ")");
			dispatchEvent(new ProgressEvent(ProgressEvent.PROGRESS, false, false, totalLoadedItems, kItems + this.finishedPages * this.maxPageSize));
			dispatchEvent(new ProgressEvent(PAGE_PROGRESS, false, false, loaded.length, currentPageSize));
			if(loaded.length == maxPageSize || (toLoad.length == 0 && loading.length == 0)) {
				pageDownloaded();
			} else {
				doLoadPage();
			}
		}
		
	}
}