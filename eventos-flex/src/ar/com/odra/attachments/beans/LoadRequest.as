package ar.com.odra.attachments.beans {
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.net.URLRequest;
	import flash.system.LoaderContext;
	import flash.system.Security;
	
	[Event(name="complete", type="flash.events.Event")]
	public class LoadRequest extends EventDispatcher {
	
	    public var imageSourceContainer:*;
	    public var imageSourceProperty:String;
	    public var imageFoundProperty:String;
	    public var url:String;
	    public var imageLoader:ImageLoader;
	    public var loaded:Boolean = false;
	    public var tries:int;
	    public var errorImageSource:BitmapData;
	
	    protected var triesLeft:int = -1;
	
	    public function load():void {
	        if (triesLeft == -1) {
	            triesLeft = tries;
	        }
	
	        var loader:Loader = new Loader();
	
	        loader.contentLoaderInfo.addEventListener(Event.COMPLETE, function(e:Event):void {
	            imageSourceContainer[imageSourceProperty] = e.target.bytes;
	            if(imageFoundProperty!= null) {
		            imageSourceContainer[imageFoundProperty] = true;
	            }
	            e.stopPropagation();
	            dispatchLoadEvent();
	        });
	
	        loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, function(e:IOErrorEvent):void {
	            if (triesLeft == 0) {
	    	        if(imageFoundProperty!= null) {
			            imageSourceContainer[imageFoundProperty] = false;
		           	} else if (errorImageSource != null) {
	                    imageSourceContainer[imageSourceProperty] = errorImageSource;
	                }
	                dispatchLoadEvent();
	            }
	            else {
	                triesLeft--;
	                load();
	            }
	            e.stopPropagation();
	        });
	
			var context:LoaderContext = new LoaderContext();
			context.checkPolicyFile = true;
			//Security.allowDomain("c0198651.cdn.cloudfiles.rackspacecloud.com");
	        loader.load(new URLRequest(url), context);
	    }
	
	    /*
	     public function get errorImage():Class {
	     return imageLoader.errorImage;
	     }
	
	     public function get completeEffect():Effect {
	     return imageLoader.completeEffect;
	     } */
	
	    private function dispatchLoadEvent():void {
	        this.loaded = true;
	        this.dispatchEvent(new Event(Event.COMPLETE));
	    }
	}
}
