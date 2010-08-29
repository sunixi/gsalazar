package ar.com.odra.utils {
	import mx.collections.IList;
	
	public final class ListUtil{

		public static function remove(list:IList, value:Object):void {
			list.removeItemAt(list.getItemIndex(value));
		}
		public static function addAll(list:IList, value:IList):void {
			for(var o:Object in value.toArray()){
				list.addItem(o);
			}
		}
	}
}