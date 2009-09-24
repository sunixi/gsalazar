package com.angel.components.windowsShade {
	import com.angel.events.ChangeStateWindowShadeEvent;
	
	import flexlib.containers.WindowShade;
	

	[Event(name="changeState", type="com.angel.events.ChangeStateWindowShadeEvent")]
	[Bindable]
	public class XWindowShade extends WindowShade {
			
			
			public function openWindow():void {
				if(!super.opened){
					super.opened = true;
					//this.checkIn.setFocus();
				}
			}
			
			public function closeWindow():void {
				if(super.opened){
					super.opened = false;
				}
			}
			
			public override function set opened(_opened:Boolean):void{
				dispatchEvent(new ChangeStateWindowShadeEvent("changeState", _opened));
				if(_opened){
					this.openWindow();
				} else {
					this.closeWindow();
				}
			}
			
			public override function get opened():Boolean{
				return super.opened;
			}

	}
}
