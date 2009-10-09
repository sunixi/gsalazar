package ar.com.gsalazar.beans {
	import mx.collections.IList;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.Proyecto")]
	public class Proyecto extends Buscable{

		public var desarrolladores:IList;
		
		public override function stateName():String{
			return "proyectoState";
		}
 	
	}
}
