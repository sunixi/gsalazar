package com.angel.components.tagCloud.strategyView {
	import com.angel.components.tagCloud.TagSearch;

	[Bindable]
	public class DefaultStrategyView implements StrategyView {
		
		public function fontSize(tagSearch:TagSearch, totalOcurrences:Number):Number {
			var percent:Number = Math.ceil((tagSearch.occurrences * 100 ) / totalOcurrences);
			if(percent <= 20){
				return 8;
			}
			if(percent > 20 && percent <= 40){
				return 10;
			}
			if(percent > 40 && percent <= 60){
				return 12;
			}
			if(percent > 60 && percent <= 80){
				return 14;
			}
			if(percent > 80 && percent <= 100){
				return 16;
			}
			return 6;
		}
		
		public function fontColor(tagSearch:TagSearch, totalOcurrences:Number):String {
			return "white";
		}
	}
}
