package ar.com.gsalazar.beans {
	import com.angel.beans.PersistentObject;
	
	import mx.formatters.DateFormatter;
	

	[Bindable]
	[RemoteClass(alias="ar.com.gsalazar.beans.ItemCategoria")]
	public class ItemCategoria extends PersistentObject {

		public var nombre:String;
		public var descripcion:String;
		public var comienzo:Date;
		public var fin:Date;

		public function mostrarFechas():String {
			var fechaFormatter:DateFormatter = new DateFormatter();
			fechaFormatter.formatString = "DD/MM/YYYY";
			if(this.comienzo == null){
				return "";
			}
			if(this.comienzo.getDay() == this.fin.getDay() && this.comienzo.getMonth() == this.fin.getMonth()
				&& this.comienzo.fullYear == this.fin.fullYear){
				return "(" + fechaFormatter.format(this.comienzo) + ")";
			} else {
				return "(" + fechaFormatter.format(this.comienzo) + " - " + fechaFormatter.format(this.fin) + " )";
			}
		}
	}
}
