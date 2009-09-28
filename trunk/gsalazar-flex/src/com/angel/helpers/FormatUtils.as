package com.angel.helpers
{
	import flash.utils.Dictionary;
	
	public class FormatUtils
	{
		public function FormatUtils()
		{
		}
		
		/**
		 * ciber
		 * convierte los caracteres  
		 * las ñ y los tildes
		 * 
		*/ 
		 public static function convertirCaracteres(txt:String):String{
		 	var map:Dictionary = new Dictionary();
		 
				 map["Á"] = "$Aacute";
				 map["á"] = "$aacute";
				 map["É"] = "$Eacute";
				 map["é"] = "$eacute";
				 map["Í"] = "$Iacute";
				 map["í"] = "$iacute";
				 map["Ó"] = "$Oacute";
				 map["ó"] = "$oacute";
				 map["Ú"] = "$Uacute";
				 map["ú"] = "$uacute";
				 map["Ñ"] = "$Ntilde";
				 map["ñ"] = "$ntilde";
				 
			var str:String ="";	
			var strAux:String = "";
			for(var i:int=0; i < txt.length;i++){
				strAux = txt.charAt(i); 	
				if((map[strAux] != null) && (map[strAux] != "")){
					//if (!strAux.match(" "))
						str = str + map[strAux];
				}else{
			   	 	str = str +  strAux;	
			  	}
		  }
		  return  str;
		 }
		 
		
		
		
		public static function achicarTexto(txt:String, cantCaracteres:int = 20):String{
				var txtFinal:String;
				txtFinal = txt.substr(0,cantCaracteres);
				if (txt.length > cantCaracteres) 
					txtFinal = txtFinal + "...";
				return txtFinal;
		}		
		
		/**
		 * Funcion que transforma un numero arabigo en uno romano.
		 * @param numero Arabigo
		 * @return String con numero romano
		 */
		public static function arabicToRoman(prefix:String, numero:Number):String{
			var roman:String = prefix+" ";
			var arabic:Number = numero;
			while (arabic - 1000000 >= 0){
				roman += "m";
				arabic -= 1000000;
			}
			while (arabic - 900000 >= 0){
				roman += "cm";
				arabic -= 900000;
			}
			while (arabic - 500000 >= 0){
				roman += "d";
				arabic -= 500000;
			}
			while (arabic - 400000 >= 0){
				roman += "cd";
				arabic -= 400000;
			}
			while (arabic - 100000 >= 0){
				roman += "c";
				arabic -= 100000;
			}
			while (arabic - 90000 >= 0){
				roman += "xc";
				arabic -= 90000;
			}
			while (arabic - 50000 >= 0){
				roman += "l";
				arabic -= 50000;
			}
			while (arabic - 40000 >= 0){
				roman += "xl";
				arabic -= 40000;
			}
			while (arabic - 10000 >= 0){
				roman += "x";
				arabic -= 10000;
			}
			while (arabic - 9000 >= 0){
				roman += "Mx";
				arabic -= 9000;
			}
			while (arabic - 5000 >= 0){
				roman += "v";
				arabic -= 5000;
			}
			while (arabic - 4000 >= 0){
				roman += "Mv";
				arabic -= 4000;
			}
			while (arabic - 1000 >= 0){
				roman += "M";
				arabic -= 1000;
			}
			while (arabic - 900 >= 0){
				roman += "CM";
				arabic -= 900;
			}
			while (arabic - 500 >= 0){
				roman += "D";
				arabic -= 500;
			}
			while (arabic - 400 >= 0){
				roman += "CD";
				arabic -= 400;
			}
			while (arabic - 100 >= 0){
				roman += "C";
				arabic -= 100;
			}
			while (arabic - 90 >= 0){
				roman += "XC";
				arabic -= 90;
			}
			while (arabic - 50 >= 0){
				roman += "L";
				arabic -= 50;
			}
			while (arabic - 40 >= 0){
				roman += "XL";
				arabic -= 40;
			}
			while (arabic - 10 >= 0){
				roman += "X";
				arabic -= 10;
			}
			while (arabic - 9 >= 0){
				roman += "IX";
				arabic -= 9;
			}
			while (arabic - 5 >= 0){
				roman += "V";
				arabic -= 5;
			}
			while (arabic - 4 >= 0){
				roman += "IV";
				arabic -= 4;
			}
			while (arabic - 1 >= 0){
				roman += "I";
				arabic -= 1;
			}
			return (roman);
		}

	}
}