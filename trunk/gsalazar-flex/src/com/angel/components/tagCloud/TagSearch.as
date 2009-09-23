package com.angel.components.tagCloud{

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.TagSearch")]
	public class TagSearch {

		public static const DEFAULT_SIZE :Number = 5;
		public var label:String;
		public var description:String;
		public var occurrences:Number;
	}
}
