package com.angel.components.tagCloud{

	[Bindable]
	[RemoteClass(alias="com.angel.TagLink")]
	public class TagLink {

		public static const DEFAULT_SIZE :Number = 5;
		public var label:String;
		public var description:String;
		public var link:String;
		public var occurences:Number;
		public var totalOccurences:Number;

		public function getFontSize():Number {
			if(occurences == 0){
				return DEFAULT_SIZE;
			} else {
				return Math.ceil(((occurences * 100 ) / totalOccurences) * this.getFactor());
			}
		}
		
		public function getFactor():Number {
			return 0.4;
		}
	}
}
