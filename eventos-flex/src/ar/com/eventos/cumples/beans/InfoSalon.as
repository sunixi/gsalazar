package ar.com.eventos.cumples.beans{
	import mx.collections.IList;
	

/**
 *
 * TODO Comentar.
 *
 * @since	27/02/2010.
 *
 */
[Bindable]
[RemoteClass(alias = "ar.com.productos.beans.ClientType")]
public class InfoSalon {

	public var tipo:String;
	public var nombre:String;
	public var descripcion:String;
	public var logo:Foto;
	public var fotos:IList;
	public var enlace:String;
	public var direccion:String;
	public var telefono:String;
	public var localidad:String;


public function primerFoto():Foto {
		return (fotos.length > 0 ? Foto(fotos.getItemAt(0)) : null);
	}
	public function ultimaFoto():Foto {
		return (fotos.length > 0 ? Foto(fotos.getItemAt(fotos.length - 1)) : null);
	}
	
	public function fotoUbicada(index:int):Foto {
		return (fotos.length > index ? Foto(fotos.getItemAt(index)) : null);
	}
	
	public function siguienteFoto(value:Foto):Foto {
		if(value == null){
			return null;
		}
		var index:int = fotos.getItemIndex(value);
		if(index == fotos.length - 1){
			return this.primerFoto();
		} else {
			return this.fotoUbicada(index + 1);
		}
	}
	public function anteriorFoto(value:Foto):Foto {
		if(value == null){
			return null;
		}
		var index:int = fotos.getItemIndex(value);
		if(index == 0){
			return this.ultimaFoto();
		} else {
			return this.fotoUbicada(index - 1);
		}
	}
	[Bindable]
	public function indexFoto(value:Foto):Number {
		return fotos.getItemIndex(value);
	}

}
}