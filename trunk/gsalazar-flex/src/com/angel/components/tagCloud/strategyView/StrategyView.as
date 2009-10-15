package com.angel.components.tagCloud.strategyView
{
	import com.angel.components.tagCloud.TagSearch;
	
	public interface StrategyView
	{
	
		function fontSize(tagSearch:TagSearch, totalOcurrences:Number):Number;
		
		function fontColor(tagSearch:TagSearch, totalOcurrences:Number):uint;

	}
}