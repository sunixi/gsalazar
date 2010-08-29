package ar.com.odra.attachments.beans {
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	[Event(name="complete", type="flash.events.Event")]
	public class ImageLoader extends EventDispatcher {
	
	    public var targets:ArrayCollection = new ArrayCollection();
	    //public var retries:int = 3;
	    //public var batchSize:int = 20;
	
	    //public var errorImage:Class;
	    //public var completeEffect:Effect = new Effect();
	
	    //private var imagesLoaded:int = 0;
	    //private var maxImagePedida:int = 0;
	
	    //public var cantInitialImagesLoaded:int = 20;
	
	    //public var throwInitialImagesLoadedEvent:Boolean = true;
	
	
	    public function load():void {
	        for each (var lr:LoadRequest in targets) {
	            lr.addEventListener(Event.COMPLETE, imageLoaded);
	            lr.load();
	        }
	    }
	
	    public function addRequest(_url:String, _imageSourceContainer:*, _imageSourceProperty:String, _imageFoundProperty:String = null, _tries:int = 2, _errorImageSource:BitmapData = null):void {
	        var request:LoadRequest = new LoadRequest();
	        request.imageSourceContainer = _imageSourceContainer;
	        request.imageSourceProperty = _imageSourceProperty;
	        request.imageFoundProperty = _imageFoundProperty;
	        request.url = _url;
	        request.tries = _tries;
	        request.errorImageSource = _errorImageSource;
	
	        request.imageLoader = this;
	        targets.addItem(request);
	    }
	
	    /*
	     private function pedirImagenes():void {
	     //var iTo:int = Math.min(desde + batchSize, targets.length);
	
	
	     /*
	     for (var i:int = desde; i < iTo; i++) {
	     var target:LoadRequest = targets.getItemAt(i) as LoadRequest;
	     target.addEventListener(LoadRequest.LOADED, imageLoaded);
	     target.load();
	     }
	
	     maxImagePedida = iTo - 1;
	     } */
	
	    private function imageLoaded(e:Event):void {
	        e.stopPropagation();
	        //imagesLoaded++;
	        /*
	         if (batchSize > targets.length) {
	         return;
	         }*/
	
	        for each (var lr:LoadRequest in targets) {
	            if (!lr.loaded) {
	                return;
	            }
	        }
	
	        this.dispatchEvent(new Event(Event.COMPLETE, true));
	
	        /*
	
	         //if (imagesLoaded >= targets.length && throwInitialImagesLoadedEvent) {
	         if (imagesLoaded >= targets.length && throwInitialImagesLoadedEvent) {
	         var loaded:Boolean = true;
	         for (var i:int = 0; i < batchSize; i++) {
	         var target:LoadRequest = targets.getItemAt(i) as LoadRequest;
	         if (!target.loaded) {
	         loaded = false;
	         break;
	         }
	         }
	         if (loaded) {
	         throwInitialImagesLoadedEvent = false;
	         this.dispatchEvent(new Event("loaded", true));
	         }
	         }
	         */
	        /*
	         if (imagesLoaded - 1 == maxImagePedida) {
	         pedirImagenes(maxImagePedida++);
	         } */
	    }
	}
}
