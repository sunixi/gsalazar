package com.angel.helpers
{
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.net.navigateToURL;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	
	public class ExportUtils
	{
		private static const _instance:ExportUtils= new ExportUtils(SingletonLock);
		
		[Bindable]
        private var arrayCollection:ArrayCollection; 

        [Bindable]
        private var paramCollection:ArrayCollection; 

        private var urlRequest:URLRequest;
        private var urlLoader:URLLoader; 


		public function ExportUtils(lock:Class) {
 			// verify that the lock is the correct class reference.
   			if ( lock != SingletonLock ) {
    			throw new Error( "Invalid Singleton access. Use FileProxy.instance." );
    		}
 		}
 		
 		public static function get instance():ExportUtils {
	   		return _instance;
	   	}

	 	public function export(searchStrategyName:String, exportFileCommandName:String, exportRowCommandName:String, parameterSeparatorString:String, parameters:Dictionary, columnNamesSeparatorString:String, columnNames:IList, url:String = "../downloadExportFile"):void{
			var sendVars:URLVariables = new URLVariables();					
			sendVars.searchStrategyName = searchStrategyName;
			sendVars.exportFileCommandName = exportFileCommandName;
			sendVars.exportRowCommandName = exportRowCommandName;
			sendVars.separatorParameters = parameterSeparatorString;
			sendVars.exportParameters = StringUtils.plainParametersWithDictionary(parameters, parameterSeparatorString);
			sendVars.columnNamesSeparator = columnNamesSeparatorString;
			sendVars.columnNames = StringUtils.plainParametersWithList(columnNames, columnNamesSeparatorString);			
			trace(sendVars);
			var request:URLRequest = new URLRequest();
			request.data = sendVars;
		    request.url = url;
		    request.method = URLRequestMethod.POST;	    				    
		    navigateToURL(request,"_parent");
	   	}
	   	
	   	public function convertirAString(arrayCollection:ArrayCollection, listener:Function = null, separator:String = "#"):String {
	   		var arrayString:String = "";
			if(arrayCollection != null){
				for each(var o:Object in arrayCollection){
					var str:String = (listener != null) ? listener.call(this, o) : o.toString();
					var idx:int = arrayCollection.getItemIndex(o);
					var idx2:int = arrayCollection.length - 1;
					if( idx == idx2){
						arrayString += str;
					} else {
						arrayString += str + separator;
					}
				}					
			}
			return arrayString;
		}
	   	
  
	}
}
	class SingletonLock {
}