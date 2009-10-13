package com.angel.components.rankeable
{
	import flash.display.DisplayObject;
	
	public interface Rankeable
	{
	
		function getLabel():String;
		
		function getDescription():String;
		
		function getContenido():String;
		
		function getRanking():Number;
		
		function buildDetallePopup(_displayObject:DisplayObject):void;
	}
}