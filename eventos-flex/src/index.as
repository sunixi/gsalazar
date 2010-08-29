package {
	import br.com.stimuli.loading.BulkLoader;
	import br.com.stimuli.loading.BulkProgressEvent;
	
	import com.controls.MenuImages;
	import com.controls.VideoPlayback;
	import com.events.MouseMenu;
	import com.site.elements.Camera2D;
	
	import flash.display.GradientType;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.geom.Matrix;
	
	import gs.OverwriteManager;
	import gs.TweenMax;
	
	[SWF(backgroundColor="0x000000", frameRate="25")]
	
	public class index extends Sprite
	{
		private var _width:Number;
		private var _height:Number;
		private var _xml:XML;
		
		private var background:Sprite;
		private var container:Sprite;
		private var containerCB:Sprite;
		
		private var camera:Camera2D;
		private var video:VideoPlayback;
		
		public function index()
		{
			this._width = 800;
			this._height = 600;
			
			if (stage != null)
				addedToStageHandler()
			else
				addEventListener(Event.ADDED_TO_STAGE, addedToStageHandler, false, 0, true);
		}
		
		protected function addedToStageHandler(evt:Event=null):void 
		{
			if (evt)
				removeEventListener(Event.ADDED_TO_STAGE, addedToStageHandler);
			
			OverwriteManager.init(1);
			
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.align = StageAlign.TOP_LEFT;
			
			background = new Sprite();
			container = new Sprite();
			containerCB = new Sprite();
			
			var matrix:Matrix = new Matrix();
			matrix.createGradientBox(1.5*_width, 1.3*_height, 0, -0.2*_width, -0.1*_height);
			var degradado:Sprite = new Sprite();
			degradado.graphics.beginGradientFill(GradientType.RADIAL, [0xAAAAAA, 0x000000, 0x030303], [1.0, 1.0, 1.0], [0, 220, 255], matrix);
			degradado.graphics.drawRect(0, 0, _width, _height);
			background.addChild(degradado);
			
			var mesa:Sprite = new Sprite()
			matrix.createGradientBox(1.30*_width, 0.5*_height, 0, -0.20*_width, -0.25*_height);
			mesa.graphics.beginGradientFill(GradientType.RADIAL, [0x666666, 0x030303], [1.0, 1.0], [0, 255], matrix);
			mesa.y = 0.6*_height;
			mesa.graphics.drawRect(-_width, 0, 3*_width, _height);
			container.addChild(mesa);
			update();
			
			this.addChild(background);
			this.addChild(container);
	 		this.addChild(containerCB);
	 		
			stage.addEventListener(Event.RESIZE, onResizeHandler);
			var loader:BulkLoader = new BulkLoader("loader");
			
			loader.add(getUrl("imagenes/camara.png"), {id:"camaraHD"});
			loader.add(getUrl("xml/galery.xml"), {id:"galeryXml"});
			
			loader.addEventListener(BulkProgressEvent.PROGRESS, onProgressLoader, false, 0, true);
			loader.addEventListener(BulkProgressEvent.COMPLETE, onCompleteLoader, false, 0, true);
			loader.start();
			
		}
			
		private function onProgressLoader(evt:BulkProgressEvent):void {
			var ratio:Number = evt.weightPercent;
		}
		
		private function onCompleteLoader(evt:BulkProgressEvent):void {
			var loader:BulkLoader = BulkLoader.getLoader("loader");
			loader.removeEventListener(BulkProgressEvent.PROGRESS, onProgressLoader);
			loader.removeEventListener(BulkProgressEvent.COMPLETE, onCompleteLoader);
			_xml = loader.getXML("galeryXml", true);
			
			camera = new Camera2D(loader.getBitmapData("camaraHD", true), 700);
			camera.x = 0.75*_width;
			camera.y = 0.65*_height;
			container.addChild(camera);
			
			var relPosX:Number = Number(_xml.@posX.toString());
			var relPosY:Number = Number(_xml.@posY.toString());
			
			video = new VideoPlayback();
			video.x = relPosX*_width;
			video.y = relPosY*_height;
			container.addChild(video);
			
			var menu:MenuImages = new MenuImages(40, 0.5, _xml);
			containerCB.addChild(menu);
			menu.addEventListener(MouseMenu.MOUSE_UP_MENU, onSelectMenu);
			
			TweenMax.delayedCall(2.0, function():void{menu.show = true;update()});
			camera.reset(-_width, - 1.5*_height, -30);
			camera.show(2);
			
		}
		
		private function onSelectMenu(evt:MouseMenu):void {
			if (evt.order == -1) {
				video.stop();
			} else {
				video.play(getUrl(_xml.item[evt.order].video.toString()));
			}
		}
		
		protected function onResizeHandler(evt:Event):void {
			update();
		}
		
		protected function update():void {
	 		background.width = stage.stageWidth;
			background.height = stage.stageHeight;
			var _x:Number = 0.5*(stage.stageWidth - _width);
			var _y:Number = 0.5*(stage.stageHeight - _height);
			(_x<0)?container.x = 0:container.x=_x;
			(_y<0)?container.y = 0:container.y=_y;
			
			containerCB.x = 0.5*stage.stageWidth;
			containerCB.y = (stage.stageHeight<_height)?_height:stage.stageHeight;
		}
		
		private function getUrl(url:String):String {
			if (!(url.indexOf(":") > -1 || url.indexOf("/") == 0 || url.indexOf("\\") == 0)) {
				var rootURL:String;
				if (root) {
					rootURL = root.loaderInfo.url;
					if (rootURL) {
						var lastIndex: int= Math.max(rootURL.lastIndexOf("\\"), rootURL.lastIndexOf("/"));
						if (lastIndex != -1) {
							url = rootURL.substr(0, lastIndex + 1) + url;
						}
					}
				}
			}
			return url;
		}
		
	}
}
