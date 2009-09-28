package com.angel.helpers
{
	import flash.utils.Dictionary;
	
	import mx.collections.IList;
	
	public class StringUtils
	{
		public function StringUtils()
		{
		}
		
		public static function plainParametersWithDictionary(parameters:Dictionary, parameterSeparatorString:String):String{
			var parametersString:String = "";
			var size: int = (parameters != null )? parameters.length: 0 ;
			for(var key:Object in parameters){
				if(parameters[key] == parameters[size]){
					parametersString += key + "=" + parameters[key];
				} else {
					parametersString += key + "=" + parameters[key] + parameterSeparatorString;
				}
			}			
			return parametersString;
	   	}
	   	
	   	public static function plainParametersWithList(parameters:IList, parameterSeparatorString:String):String{
			var parametersString:String = "";
			var size: int = (parameters != null )? parameters.length: 0 ;
			var i:int = 1;
			for each(var key:String in parameters){
				if(i == 1 && size > 1){
					parametersString += key + parameterSeparatorString;
				} else {
					if(size == 1){
						parametersString += key;
					} else {
						if(i == size){
							parametersString += key;
						} else {
							parametersString += key + parameterSeparatorString;
						}
					}
				}
				i++;
			}			
			return parametersString;
	   	}
	}
}