package ar.com.eventos.components
{
  import flash.display.Graphics;
  import flash.display.Sprite;
  import mx.controls.TileList;
  import mx.controls.listClasses.IListItemRenderer;

  public class TileListEx extends TileList
  {

    override protected function drawSelectionIndicator(indicator:Sprite, x:Number, y:Number, width:Number, height:Number, color:uint, itemRenderer:IListItemRenderer):void
    {
    	indicator = null;
    	/*
        var g:Graphics = Sprite(indicator).graphics;
        g.clear();
        g.beginFill(color);
        g.drawCircle(width / 2, width / 2, width / 2);
        g.endFill();
        
        indicator.x = x;
        indicator.y = y;*/
    }
  }
}