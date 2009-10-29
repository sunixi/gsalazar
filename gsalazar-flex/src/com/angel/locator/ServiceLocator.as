package com.angel.locator
{
	import mx.core.UIComponent;
	
	public class ServiceLocator
	{
		/**
		 * getService()
		 * Retorna una instancia de un objeto remoto que es un servicio del lado 
		 * servidor.
		 * El parámetro name debe matchear con el id de spring que se configura del otro lado.
		 */ 
		public static function getService(serviceName:String, blockScreen:UIComponent = null):RemoteService {
			if(blockScreen != null){
				ScreenBlocker.blockScreen(blockScreen);
			}
			 var o:RemoteService = new RemoteService("defaultRemoteObject");
			 o.endpoint = "../messagebroker/amf?serviceName=" + serviceName;
			 return o;
		}
		
		public static function getServiceWithCallback(serviceName:String, callbackFunction:Function, blockScreen:UIComponent = null):RemoteService {
			 var remoteService:RemoteService = getService(serviceName, blockScreen);
			 remoteService.listener = callbackFunction;
			 return remoteService;
		}
	}
}