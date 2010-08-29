package ar.com.odra.attachments.beans
{
	import flash.events.IEventDispatcher;
	
	public interface IAttachment extends IEventDispatcher {
		function download(params:String):void;
		function get url():String;
	}
}