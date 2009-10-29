package ar.com.gsalazar.beans {
	import ar.com.gsalazar.popups.DesarrolladorPopup;
	
	import flash.display.DisplayObject;
	import flash.utils.ByteArray;
	
	import mx.core.Application;
	import mx.managers.PopUpManager;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Persona")]
	public class Persona extends Buscable {

		public var imagenArray:ByteArray;
		public var nombre:String;
		public var apellido:String;
		public var email:String;
		public var nacimiento:Date;
		public var tituloUniversitario:String;
		public var trabajando:Boolean;
		public var empresa:String;

		public override function buscableName():String{
			return "Desarrollador";
		}
		
		public override function resultImageName():Class{
			[Embed(source='images/desarrollador.png')]
			var a:Class; 
			return a;
		}
		
		public function trabajandoActualmente():String {
			return trabajando ? 'Sí' : 'No';
		}
		
		public function empresaTrabajando():String {
			return trabajando ? this.empresa : '-';
		}
		
		public override function buildDetallePopup(_displayObject:DisplayObject):void {
			var _popup:DesarrolladorPopup = DesarrolladorPopup(PopUpManager.createPopUp(_displayObject, DesarrolladorPopup, true));
			_popup.height = Application.application.height * 0.5;
			_popup.width = Application.application.width * 0.5;
			_popup.x = (Application.application.width / 2) - _popup.width / 2;
			_popup.y = (Application.application.height / 2) - _popup.height / 2;
			_popup.desarrollador = this;
			PopUpManager.bringToFront(_popup);
		}
		
		public override function getFullTitulo():String {
			return 'Información Personal de ' + this.titulo;
		}
		
		public override function getRanking():Number{
			return -1;
		}

		public override function get visualizado():Number{
			return -1;
		}
		
		public override function set visualizado(valor:Number):void{
			super.visualizado = valor;
		}
		
	}
}
