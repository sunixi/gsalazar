package com.angel.components.tagCloud{

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.TagSearch")]
	public class TagSearch {

		public static const DEFAULT_SIZE :Number = 5;
		public var label:String;
		public var description:String;
		public var occurrences:Number;

		public function getFontSize(totalOcurrences:Number):Number {
			if(occurrences == 0){
				return DEFAULT_SIZE;
			} else {
				return Math.ceil(((occurrences * 100 ) / totalOcurrences) * this.getFactor());
			}
		}
		
		public function getFactor():Number {
			return 0.6;
		}
	}
}
