package com.angel.locator
{
	
	import com.angel.alert.CustomAlert;
	import com.angel.beans.ErrorMessage;
	import com.angel.exception.ApplicationError;
	import com.angel.exception.BusinessError;
	import com.angel.utils.RemoteServicesNames;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;

	public dynamic class RemoteService extends RemoteObject
	{
		private var lastListener:Function;
		private var lastFaultListener:Function;
		
		public function RemoteService(destination:String=null)
		{
			super(destination);
			this.addEventListener(ResultEvent.RESULT, callback);
			this.addEventListener(FaultEvent.FAULT,faultEventCallback);
			this.lastFaultListener = faultEventCallback;
		}
		
		public function set listener(callback:Function):void{
			lastListener = callback;	
		}
		
		public function set faultListener(_callback:Function):void{
			if (this.lastFaultListener != null) {
				this.removeEventListener(FaultEvent.FAULT, this.lastFaultListener);
			}
			this.addEventListener(FaultEvent.FAULT, _callback);
		}
		
		
		private function callback(event:ResultEvent):void{
			if(lastListener != null){
				if(event.result == null)
					lastListener.call(this);
				else
					lastListener.call(this,event.result);
			}
			ScreenBlocker.unblockScreen();
		}
		
		public function faultEventCallback(event:FaultEvent):void{
			ScreenBlocker.unblockScreen();//Esto por las dudas que este blockeante
			if (event.fault.rootCause is BusinessError) {
				var error:ApplicationError = event.fault.rootCause as BusinessError;
				CustomAlert.error(error.popupMessage);
				return;
			} else {
				//CustomAlert.error('Ocurrio un error inesperado. Intente en unos segundos.');
				this.logErrorMessage(event.fault);
			}
			trace("Error desconocido: " + event.fault.faultString);
		}
		
		private function logErrorMessage(_error:Error):void {
			var errorMessage:ErrorMessage = new ErrorMessage();
			errorMessage.buildErrorMessage(_error, "Destination: " + super.destination + " - Endpoint: " + super.endpoint + " - Function: " + this.lastListener);
			trace("Error desconocido: " + _error.message);
			ServiceLocator.getServiceWithCallback(RemoteServicesNames.ERROR_MESSAGE_SERVICE_NAME, createErrorMessageCallbackFunction).create(errorMessage);
		}
		
		private function createErrorMessageCallbackFunction(_errorMessage:ErrorMessage):void {
			var errorMessageString:String = "Ha ocurrido un error interno. Por favor inténtelo en unos minutos. El ID de identificacion interno es: " + _errorMessage.versionNumber;
			CustomAlert.error(errorMessageString);
		}

	}
}