package ar.com.odra.attachments.beans {
	import ar.com.odra.events.attachment.AttachmentEvent;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.utils.ByteArray;
	
	import mx.events.PropertyChangeEvent;
	import mx.utils.UIDUtil;
	
	[Bindable]
	[Event(name="dataReady",		type="ar.com.odra.events.attachment.AttachmentEvent")]
	[Event(name="loadError",		type="ar.com.odra.events.attachment.AttachmentEvent")]
	[Event(name="downloadComplete", type="ar.com.odra.events.attachment.AttachmentEvent")]
	[Event(name="statusChanged", 	type="ar.com.odra.events.attachment.AttachmentEvent")]
	[Event(name="progress",			type="flash.events.ProgressEvent")]
	public class AbstractAttachment extends EventDispatcher implements IAttachment {
	
	    public static var INITIAL:String = "initial";
	    public static var NO_IMAGE:String = "noImage";
	    public static var LOADING:String = "loading";
	    public static var READY:String = "ready";
	    public static var DOWNLOADING:String = "downloading";
	
	    public var fileReference:FileReference;
	
	    public var cacheable:Boolean;
	    public var url:String;
	    public var fileName:String;
	    public var internalFileName:String;
	    public var data:ByteArray;

	    private var _toBeRemoved:Boolean;
	    private var _toBeInserted:Boolean;
	    
	    public function get toBeRemoved():Boolean {
	    	return _toBeRemoved;
	    }
	    
	    private function set toBeRemoved(v:Boolean):void {
	    	_toBeRemoved = true;
	    }
	
	    public function get toBeInserted():Boolean {
	    	return _toBeInserted;
	    }
	    
	    private function set toBeInserted(v:Boolean):void {
	    	_toBeInserted = true;
	    }
	
	    public var status:String = INITIAL;
	
		public function get dataProperty():String {
			return "data";
		}
	
	    public function AbstractAttachment():void {
	        fileReference = new FileReference();
	        fileReference.addEventListener(Event.SELECT, fileSelected);
	        fileReference.addEventListener(Event.COMPLETE, onComplete);

	        fileReference.addEventListener(ProgressEvent.PROGRESS, onProgress);

	        fileReference.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityError);
	        fileReference.addEventListener(IOErrorEvent.IO_ERROR, onIOError);
	        
	        this.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, function (e:PropertyChangeEvent):void {
	            if (e.property == "status") {
	                if (e.newValue != e.oldValue) {
	                    dispatchEvent(new AttachmentEvent(AttachmentEvent.STATUS_CHANGED));
	                }
	            }
	        });
	    }
	
	    private function fileSelected(e:Event):void {
	        status = LOADING;
	        fileName = fileReference.name;
	    	fileReference.load();
	    }
	    
	    private function onComplete(e:Event = null):void {
    		this[dataProperty] = fileReference.data;
	        internalFileName = UIDUtil.createUID() + fileName;
	        toBeInserted = true;
    		status = READY;
            dispatchEvent(new AttachmentEvent(AttachmentEvent.DATA_READY));
	    }
	    
	    public function set uploadData(_data:ByteArray):void {
	    	this.data = _data;
	    	this.toBeInserted = true;
	    	this.internalFileName = UIDUtil.createUID() + fileName;
	    	
	    }

	    protected function onSecurityError(event:Event):void {
	    	handleError();
	    }
	    
	    protected function onIOError(event:Event):void {
	    	handleError();
	    }
	    
	    private function handleError():void {
    		removeLoading();
	        dispatchEvent(new AttachmentEvent(AttachmentEvent.LOAD_ERROR));
	    }
	
		private function removeLoading():void {
	        status = NO_IMAGE;
	        fileName = null;
    		this[dataProperty] = null;
		}


	    public function remove():void {
	    	if(status == READY) {
	    		toBeRemoved = true;
	    	}

	    	if(status == LOADING) {
	    		fileReference.cancel();
	    		removeLoading();
	    	} else if(status == READY) {
	    		removeLoading();
	    	}
	    }
	    
	    protected function onProgress(event:ProgressEvent):void {
	    	dispatchEvent(event);
	    }
	    
	    protected function get additionalParameters():String {
	    	return "";
	    }
	
	    public function browse(description:String, _extension:String):void {
	    	var extension:String = _extension + ";" + _extension.toUpperCase()
	        fileReference.browse([new FileFilter(description, extension)]);
	    }
	    
		public function download(params:String):void {
	    	status = DOWNLOADING;
	    	doDownload(params);
		}
		
		protected function doDownload(params:String):void {
			throw new Error("Override me!!");
		}
		
		protected function onDownloadComplete(event:Event):void {
			status = READY;
            dispatchEvent(new AttachmentEvent(AttachmentEvent.DOWNLOAD_COMPLETE));
		}

		public function wasDownloaded():Boolean {
			return data != null;
		}		
	}
}
