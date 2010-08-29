package ar.com.odra.utils
{ 
	import flash.display.Stage;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.Dictionary;
	
	import mx.core.Container;
	import mx.core.EdgeMetrics;
	
	public class AutoScrollingManager
	{		
		private static var managers:Dictionary = new Dictionary();
		
		public var scrollSpeed:int = 10;			
		
		public var scrollFunction:Function = horizontalScroll;
		
		private var _bound:EdgeMetrics;
		
		public var bound:EdgeMetrics = new EdgeMetrics(20,20,20,20);
		
		public static function addAutoScroll(container:Container,scrollSpeed:int = 10,bound:EdgeMetrics = null):void
		{						
			var manager:AutoScrollingManager = new AutoScrollingManager();
			
			manager.scrollSpeed = scrollSpeed;
			if(bound)manager.bound = bound;
			
			managers[container] = manager;
					
			container.systemManager.addEventListener(MouseEvent.MOUSE_MOVE,onMouseMove);
			container.systemManager.addEventListener(MouseEvent.MOUSE_UP,onMouseUp);
		}
		
		public static function removeAutoScroll(container:Container):void
		{			
			container.systemManager.removeEventListener(MouseEvent.MOUSE_MOVE,onMouseMove);
			container.systemManager.removeEventListener(MouseEvent.MOUSE_UP,onMouseUp);
			if(managers[container])
			{
				container.systemManager.removeEventListener(Event.ENTER_FRAME,managers[container].scrollFunction);
				managers[container] = null;
			}
		}
		
		private static function onMouseMove(event:MouseEvent):void
		{		
			var pt:Point = new Point(event.localX,event.localY);											
			var container:Container = findContainer(pt,event.currentTarget.stage);	
			
			if(!container)
				return;
						
			var manager:AutoScrollingManager = managers[container];
							
			if(!event.buttonDown)
			{
				container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
				return;
			}
			
			var vm:EdgeMetrics = container.viewMetrics;			
			var _bound:EdgeMetrics = new EdgeMetrics();
			_bound.bottom = manager.bound.bottom;
			_bound.left = manager.bound.left;
			_bound.right = manager.bound.right;
			_bound.top = manager.bound.top;
			
			if(container.horizontalScrollBar)_bound.bottom += container.horizontalScrollBar.height;
			if(container.verticalScrollBar)_bound.right += container.verticalScrollBar.width;
			
			if(vm.bottom!=0) _bound.bottom += vm.bottom;
			if(vm.left!=0) _bound.left += vm.left;
			if(vm.right!=0) _bound.right += vm.right;
			if(vm.top!=0) _bound.top += vm.top;
			
			pt = event.target.localToGlobal(pt);
			pt = container.globalToLocal(pt);
			
			var insideLeftTopCorner:Boolean;				
			var insideLeftBottomCorner:Boolean ;							
			var insideRightTopCorner:Boolean; 				
			var insideRightBottomCorner:Boolean ;
			
			insideLeftTopCorner = pt.x < _bound.left && pt.y < _bound.top;
			insideLeftBottomCorner = pt.x < _bound.left && pt.y > container.height - _bound.bottom;
			insideRightTopCorner = pt.x > container.width - _bound.right && pt.y < _bound.top;
			insideRightBottomCorner = pt.x > container.width - _bound.right&& pt.y > container.height - _bound.bottom;
			
			if(insideLeftTopCorner||insideLeftBottomCorner||insideRightTopCorner||insideRightBottomCorner)
			{
				container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
				return;
			}

			if(container.horizontalScrollBar)
			{	
				if(pt.y > container.height - container.horizontalScrollBar.height - vm.bottom)	
				{
					return;
				}
				manager.scrollFunction = horizontalScroll;
				if(pt.x < _bound.left)
				{					
					if( container.horizontalScrollPosition!=0)
					{
						container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						container.systemManager.addEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						manager.scrollSpeed = Math.abs(manager.scrollSpeed)*-1;
					}
				}
				else if((pt.x > container.width-_bound.right && pt.x < container.width - (container.verticalScrollBar?container.verticalScrollBar.width:0) - vm.right) || pt.x > container.width - vm.right)
				{					
					if(container.horizontalScrollPosition!=container.maxHorizontalScrollPosition)
					{
						container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						container.systemManager.addEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						manager.scrollSpeed = Math.abs(manager.scrollSpeed);							
					}
				}
				else
				{
					container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
				}					
			}
			if(container.verticalScrollBar)
			{
				if(pt.x > container.width - container.verticalScrollBar.width - vm.right)	
				{
					return;
				}
				manager.scrollFunction = verticalScroll;
				if(pt.y < _bound.top)
				{
					if(container.verticalScrollPosition!=0)
					{
						container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						container.systemManager.addEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						manager.scrollSpeed = Math.abs(manager.scrollSpeed)*-1;
					}
				}
				else if((pt.y > container.height - _bound.bottom && pt.y < container.height - (container.horizontalScrollBar?container.horizontalScrollBar.height:0) - vm.bottom) || pt.y > container.height - vm.bottom)
				{
					if(container.verticalScrollPosition!=container.maxVerticalScrollPosition)
					{
						container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						container.systemManager.addEventListener(Event.ENTER_FRAME,manager.scrollFunction);
						manager.scrollSpeed = Math.abs(manager.scrollSpeed);
					}
				}
				else
				{
					container.systemManager.removeEventListener(Event.ENTER_FRAME,manager.scrollFunction);
				}
			}					
		}
		
		private static function onMouseUp(event:MouseEvent):void
		{
			event.currentTarget.removeEventListener(Event.ENTER_FRAME,horizontalScroll);
			event.currentTarget.removeEventListener(Event.ENTER_FRAME,verticalScroll);
		}
		
		private static function horizontalScroll(event:Event):void
		{
			var pt:Point = new Point(event.currentTarget.stage.mouseX,event.currentTarget.stage.mouseY);
			var container:Container = findContainer(pt,event.currentTarget.stage);
			if(!container)
				return;
			container.horizontalScrollPosition += managers[container].scrollSpeed;
		}	
		
		private static function verticalScroll(event:Event):void
		{
			var pt:Point = new Point(event.currentTarget.stage.mouseX,event.currentTarget.stage.mouseY);
			var container:Container = findContainer(pt,event.currentTarget.stage);
			if(!container)
				return;
			container.verticalScrollPosition += managers[container].scrollSpeed;
		}
		
		private static function findContainer(pt:Point,stage:Stage):Container
		{
			var objects:Array = stage.getObjectsUnderPoint(pt);
			
			for each(var object:Object in objects)
			{
				var result:Object = doFind(object);
				if(result!=null)
				{
					return  Container(result);
				}
			}
			return null;
		}
		
		private static function doFind(object:Object):Container		
		{
			if(object.parent && object.parent is Container)
			{
				if(managers[object.parent] != null)
				{
					return object.parent;
				}
				else
				{
					return doFind(object.parent);
				}
			}
			return null;
		}
	}
}
