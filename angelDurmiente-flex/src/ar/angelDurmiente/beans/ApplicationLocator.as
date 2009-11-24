package ar.angelDurmiente.beans {

	import ar.angelDurmiente.dtos.AngelDurmienteInfoDTO;
	
	import com.angel.syncronization.TransactionalBlock;
	
	import mx.collections.IList;
	import mx.core.UIComponent;
	
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
		public var finishLoad:Function;
		
		[Bindable]
		public var textos:IList;
		[Bindable]
		public var masVotados:IList;
		[Bindable]
		public var masComentados:IList;
		[Bindable]
		public var canciones:IList;
		[Bindable]
		public var artistas:IList;
		[Bindable]
		public var infoAngelDurmiente:AngelDurmienteInfoDTO;

		public static const titleApplication:String = "El Angel Durmiente";
		
		public static const urlApplication:String = "ElAngelDurmiente.com.ar";
		
		public static const tooltipTitle:String = "ElAngelDurmiente.com.ar";

		public static const initStateApplication:String = "aplicacion";

		public static const initVisibleChangeApplicationConfiguration:Boolean = false;
		
		
		public function inicializar(uiComponent:UIComponent = null):void {
			if(!this.estaInicializado()){
				var transactionalBlock:TransactionalBlock = new TransactionalBlock();
				transactionalBlock.addEventListener("endsLoad", finishLoad); 
				transactionalBlock.register("cancionService",		"buscarTodos").callbackFunction(inicializarFindAllArtistasCallback);
				transactionalBlock.register("estadisticaService",	"buscarInformacionAngelDurmiente").callbackFunction(inicializarInformacionAngelDurmienteCallback);
				transactionalBlock.execute(uiComponent);
			}
	   	}
	   	
	   	private function inicializarInformacionAngelDurmienteCallback(data:AngelDurmienteInfoDTO): void {
	   		infoAngelDurmiente = data;
	   	}
	   	
	   	private function inicializarFindAllArtistasCallback(data:IList): void {
	   		canciones = data;
	   	}
	   	
	   	public function estaInicializado():Boolean {
			return artistas != null;
	   	}
	}
}

class SingletonLock {
}
