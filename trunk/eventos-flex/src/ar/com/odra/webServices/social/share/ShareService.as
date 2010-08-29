package ar.com.odra.webServices.social.share
{
	
	import ar.com.odra.webServices.WebService;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.utils.Dictionary;
	

  public class ShareService implements WebService {

	[Bindable]
	protected var params:Dictionary = new Dictionary();
	[Bindable]
	protected var url:String;


    private function buildURL():String{
    	var fullURL:String = url;
    	for (var param:Object in params) {
    		fullURL += param.toString() + '=' + params[param] + '&';
			trace(fullURL);
		}
		return fullURL.substr(0, fullURL.length - 1);
		
    }	
    public function service():void{
    	this.doShare();
    }
    
    protected function doShare():void {
    	var url:String = this.buildURL();
		navigateToURL(new URLRequest(url),'_blank');
    }
  }
}