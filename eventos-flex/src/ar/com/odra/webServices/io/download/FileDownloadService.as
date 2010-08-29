package ar.com.odra.webServices.io.download
{
	import ar.com.odra.events.DataCarrierEvent;
	import ar.com.odra.webServices.WebService;
	
	import flash.events.Event;
	import flash.events.HTTPStatusEvent;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	
	import mx.binding.utils.BindingUtils;
	import mx.controls.ProgressBar;
	import mx.core.UIComponent;
	
	[Event(name="downloadError",type="ar.com.odra.events.DataCarrierEvent")]
	[Event(name="downloadProgress",type="ar.com.odra.events.DataCarrierEvent")]
	[Event(name="downloadComplete",type="ar.com.odra.events.DataCarrierEvent")]
	[Event(name="downloadCancel",type="ar.com.odra.events.DataCarrierEvent")]
	public class FileDownloadService extends UIComponent implements WebService {

         private var fr:FileReference;  
         // Define reference to the download ProgressBar component.  
         private var pb:ProgressBar;
         [Bindable]
         public var progress:Number = 0;
         [Bindable]
         public var inProgress:Boolean = progress > 0;
         [Bindable]
         public var url:String = "";
         
         public static const DOWNLOAD_ERROR_EVENT:String = "downloadError";
         public static const DOWNLOAD_PROGRESS_EVENT:String = "downloadProgress";
         public static const DOWNLOAD_COMPLETE_EVENT:String = "downloadComplete";
         public static const DOWNLOAD_CANCEL_EVENT:String = "downloadCancel";
         

		public function FileDownloadService(){
			super();
			pb = new ProgressBar();
			fr = new FileReference();               
			 //Dispatched when download is complete or when upload generates an HTTP status code of 200.
             fr.addEventListener(Event.COMPLETE, onCompleteEvent);
             //Dispatched when a file upload or download is canceled through the file-browsing dialog box by the user
             fr.addEventListener(Event.CANCEL, onCancelEvent);
             //Dispatched when an upload fails and an HTTP status code is available to describe the failure.
             fr.addEventListener(HTTPStatusEvent.HTTP_STATUS, onHttpStatusEvent);
             //Dispatched when the upload or download fails.
             fr.addEventListener(IOErrorEvent.IO_ERROR, onIOErrorEvent);
             //Dispatched when an upload or download operation starts.  
             fr.addEventListener(Event.OPEN, onOpenEvent);
             //Dispatched periodically during the file upload or download operation.
             fr.addEventListener(ProgressEvent.PROGRESS, onProgressEvent);
             //Dispatched when a call to the FileReference.upload() or FileReference.download() method tries to upload a file to a server or get a file from a server that is outside the caller's security sandbox.
             fr.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityErrorEvent);
		}

         public function service():void {  
             this.startDownload();
         }
  
         /** 
          * Immediately cancel the download in progress and disable the cancel button. 
          */  
         public function cancelDownload():void {  
             fr.cancel();
         }
   
         /** 
          * Begin downloading the file specified in the DOWNLOAD_URL constant. 
          */  
         private function startDownload():void {  
             var request:URLRequest = new URLRequest();  
             request.url = url;
             fr.download(request);  
         }  
   
         /** 
          * When the OPEN event has dispatched, change the progress bar's label 
          * and enable the "Cancel" button, which allows the user to abort the 
          * download operation. 
          */  
         private function onOpenEvent(event:Event):void {  
             pb.label = "DOWNLOADING %3%%";  
         }  
   
         /** 
          * While the file is downloading, update the progress bar's status and label. 
          */  
         private function onProgressEvent(event:ProgressEvent):void {  
             pb.setProgress(event.bytesLoaded, event.bytesTotal);  
             progress = pb.value;
         }  
   
         /** 
          * Once the download has completed, change the progress bar's label and 
          * disable the "Cancel" button since the download is already completed. 
          */  
         private function onCompleteEvent(event:Event):void {
             pb.setProgress(0, 100);
             dispatchEvent(new DataCarrierEvent(DOWNLOAD_COMPLETE_EVENT, "[" + event.type + "]."));
         }
         
         private function onCancelEvent(event:Event):void {
         	  dispatchEvent(new DataCarrierEvent(DOWNLOAD_CANCEL_EVENT, "[" + event.type + "]."));
         }
         
         private function onHttpStatusEvent(event:HTTPStatusEvent):void {
         	  dispatchEvent(new DataCarrierEvent(DOWNLOAD_ERROR_EVENT, event.status + " [" + event.type + "]."));
         }

         private function onIOErrorEvent(event:IOErrorEvent):void {
         	dispatchEvent(new DataCarrierEvent(DOWNLOAD_ERROR_EVENT, event.text + " [" + event.type + "]."));	  
         }
         private function onSecurityErrorEvent(event:SecurityErrorEvent):void {
         	  dispatchEvent(new DataCarrierEvent(DOWNLOAD_ERROR_EVENT, event.text + " [" + event.type + "]."));
         }
  }
}