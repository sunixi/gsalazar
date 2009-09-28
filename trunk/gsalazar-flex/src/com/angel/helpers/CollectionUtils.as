package com.angel.helpers
{
	import flash.utils.Dictionary;
	
	import mx.collections.IList;
	
	public class CollectionUtils
	{
		public function CollectionUtils()
		{
		}
		
		public static function getLength(o:Object):uint {
		    var len:uint = 0;
		    for (var item:* in o)
		        if (item != "mx_internal_uid")
		            len++;
		    return len;
		}
	}
}