package com.angel.components.tagCloud {
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.TagSearchContainer")]
	public class TagSearchContainer {

		public var totalOcurrencias:Number;
		public var tagsSearchs:IList;

	}
}
