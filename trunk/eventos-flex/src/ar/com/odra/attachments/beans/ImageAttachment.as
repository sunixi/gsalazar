package ar.com.odra.attachments.beans {
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.events.Event;
	
	[Bindable]
    [RemoteClass(alias="ar.com.mindset.flexy.bean.attachment.ImageAttachment")]
	public class ImageAttachment extends AbstractAttachment {
	
		private var _additionalParameters:String = "";
	    public var errorImageData:BitmapData;
	
	
	    public function ImageAttachment():void {
	    	super();
	    }
	
	    protected override function doDownload(params:String):void {
	        var imageLoader:ImageLoader = new ImageLoader();
	        imageLoader.addRequest(this.url + additionalParameters + params, this, "data", null, 3, errorImageData);
	        imageLoader.addEventListener(Event.COMPLETE, onDownloadComplete);
	        imageLoader.load();
	    }
	
	    protected override function get additionalParameters():String {
	    	return _additionalParameters;
	    }
	    
	}
}
