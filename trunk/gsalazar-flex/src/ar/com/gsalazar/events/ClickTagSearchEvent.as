package ar.com.gsalazar.events {
	import ar.com.gsalazar.beans.Buscable;
	
	import com.angel.components.tagCloud.TagSearch;
	
	import flash.events.Event;

	public class ClickTagSearchEvent extends Event {
		
		public static const CLICK_TAG_SEARCH_EVENT:String = "clickTagSearch";

		private var _tagSearch:TagSearch;

		public function ClickTagSearchEvent(eventName:String, tagSearch:TagSearch, event:Boolean = true, flexEvent:Boolean = true) {
			super(eventName, true, false);
			_tagSearch = tagSearch;
			return;
		}
		
		public function get tagSearch():TagSearch {
			return _tagSearch;
		}
	}
}