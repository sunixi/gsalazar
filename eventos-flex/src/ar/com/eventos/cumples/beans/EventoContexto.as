package ar.com.eventos.cumples.beans {
	import mx.collections.IList;
	
	
	

	[Bindable]
	[RemoteClass(alias="ar.com.productos.beans.ClientContext")]
	public class EventoContexto {

		public var categoriasFotos:IList;
		public var infoPersonal:IList;
		public var infoSalon:InfoSalon;
		public var videos:IList;
		
		public function valorInfoPersonal(nombre:String):String {
			for each(var ip:InfoPersonal in infoPersonal.toArray()){
				if(ip.nombre.toUpperCase() == nombre.toUpperCase()){
					return ip.valor;
				}
			}
			return null;
		} 
		

	}
}
