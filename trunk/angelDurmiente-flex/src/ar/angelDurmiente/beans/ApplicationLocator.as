package ar.angelDurmiente.beans {

	import mx.collections.IList;
	
	public class ApplicationLocator
	{
		private static const _instance:ApplicationLocator = new ApplicationLocator(SingletonLock);
		
 		public function ApplicationLocator(lock:Class) {
 			// verify that the lock is the correct class reference.
   			if ( lock != SingletonLock ) {
    			throw new Error( "Invalid Singleton access. Use ApplicationLocator.instance." );
    		}
 		}
 		
 		public static function get instance():ApplicationLocator {
	   		return _instance;
	   	}
		
		[Bindable]
		private var mainPage:main;
		
		[Bindable]
		public var textos:IList;
		[Bindable]
		public var masVotados:IList;
		[Bindable]
		public var masComentados:IList;

		public static const titleApplication:String = "El Angel Durmiente";
		
		public static const urlApplication:String = "ElAngelDurmiente.com.ar";
		
		public static const tooltipTitle:String = "ElAngelDurmiente.com.ar";

		public static const initStateApplication:String = "aplicacion";

		public static const initVisibleChangeApplicationConfiguration:Boolean = false;
	}
}

class SingletonLock {
}
