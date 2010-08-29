package ar.com.odra.utils {
	
	
	public final class StringUtil{

		public static function generateRandomString(newLength:Number):String{
			var a:String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			var alphabet:Array = a.split("");
			var randomLetter:String = "";
			for (var i:Number = 0; i < newLength; i++){
				randomLetter += alphabet[Math.floor(Math.random() * alphabet.length)];
			}
			return randomLetter;
		}
		
		public static function generateRandom(newLength:uint = 1, userAlphabet:String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"):String{
			var alphabet:Array = userAlphabet.split("");
			var alphabetLength:int = alphabet.length;
			var randomLetters:String = "";
			for (var i:uint = 0; i < newLength; i++){
				randomLetters += alphabet[int(Math.floor(Math.random() * alphabetLength))];
			}
			return randomLetters;
		}


	}
}