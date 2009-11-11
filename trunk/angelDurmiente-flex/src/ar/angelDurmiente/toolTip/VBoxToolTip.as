package ar.angelDurmiente.toolTip {
	import mx.containers.VBox;
	import mx.core.IToolTip;
	


       public class VBoxToolTip extends VBox implements IToolTip {
       	
               public function VBoxToolTip() {
                   mouseEnabled = false;
                   mouseChildren = false;
                   setStyle("paddingLeft", 10);
                   setStyle("paddingTop", 10);
                   setStyle("paddingBottom", 10);
                   setStyle("paddingRight", 10);
               }

               public function get text():String {     
               		return null; 
               }
               public function set text(value:String):void {}
       }
}