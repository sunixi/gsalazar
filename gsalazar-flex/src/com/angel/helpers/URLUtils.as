package com.angel.helpers
{
	import flash.utils.Dictionary;
	
	import mx.utils.ArrayUtil;
	
	public class URLUtils
	{
		
		public static const REQUEST_PARAMETER_SEPARATOR:String = '&';
		public function URLUtils()
		{
		}
		
		public static function plainRequestParametersWithDictionary(parameters:Dictionary):String{
			var parametersString:String = "";
			var size:uint = CollectionUtils.getLength(parameters);
			var index:uint = 0;
			for(var key:Object in parameters){
				if(index == 0 && size > 1){
					parametersString += key + "=" + parameters[key] + REQUEST_PARAMETER_SEPARATOR;
				} else {
					if(index == 0 || index == size - 1){
						parametersString += key + "=" + parameters[key];
					} else {
						parametersString += key + "=" + parameters[key] + REQUEST_PARAMETER_SEPARATOR;
					}
				}
				index++;
			}			
			return parametersString;
	   	}
	}
}