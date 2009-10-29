package com.angel.locator
{
	import com.angel.components.popups.LoadingPopup;
	
	import mx.core.IFlexDisplayObject;
	import mx.core.UIComponent;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;
	
	
	/**
	 * ScreenBlocker crea un PopUp transparente que ocupa toda la pantalla.
	 * Este PopUp es modal, por lo que ninguuna ventana que este situada debajo del PopUp
	 * puede recibir eventos del raton.
	 * Para evitar el efecto de "blur" que Flex aplica cuando se crea un PopUp modal, usar:
	 * 	setStyle("modalTransparency",0);
	   	setStyle("modalTransparencyBlur",0);
	 **/
	
		
	public class ScreenBlocker
	{	
		private static var blockerPopUp : IFlexDisplayObject;
		
		/** Bloquea la pantalla mediante un PopUp transparente modal
		 * inutilizando en la practica la interaccion del ususario con
		 * la aplicacion 
		 * 
		 * @params
		 * 		_component: Componente sobre el cual se creara el PopUp
		 * **/
		public static function blockScreen(_component:UIComponent):void
		{
			//Crea un PopUp transparente, ocupando toda la pantalla
			blockerPopUp = PopUpManager.createPopUp(_component,UIComponent,true);			
			//Mostramos el cursor de espera
			CursorManager.setBusyCursor();
		}
		
		public static function blockWithLoadingScreen(_parent:UIComponent, _message:String, image:Class):void {
			var popup:LoadingPopup = PopUpManager.createPopUp(_parent, LoadingPopup, true) as LoadingPopup;
			popup.message = _message;
			popup.imageSource = image;
			blockerPopUp = popup;
			PopUpManager.centerPopUp(popup);			
			//Mostramos el cursor de espera
			CursorManager.setBusyCursor();
		}

		
		/** Destruye el ultimo PopUp creado mediante blockScreen **/
		public static function unblockScreen():void
		{
			//Destruimos el PopUp
			PopUpManager.removePopUp(blockerPopUp);
			
			//Quitamos el cursor de espera
			CursorManager.removeBusyCursor();
		}
			
		
	}
}