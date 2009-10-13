package ar.com.gsalazar.model {

	import ar.com.gsalazar.beans.Articulo;
	import ar.com.gsalazar.beans.Categoria;

	import com.angel.components.tagCloud.TagSearchContainer;
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
		public var endLoad:Function;
		
		[Bindable]
		private var _tagSearchContainer:TagSearchContainer;
		
		[Bindable]
		private var _proyectos:IList;
		
		[Bindable]
		private var _articulos:IList;
		
		[Bindable]
		private var _categorias:IList;
		
		[Bindable]
		private var _contactosWeb:IList;
		
		[Bindable]
		private var mainPage:main;
	   	
	   	public function get tagsSearchs():IList {
			return _tagSearchContainer.tagsSearchs;
	   	}
	   	
	   	public function get tagSearchContainer():TagSearchContainer {
			return _tagSearchContainer;
	   	}
	   	public function set tagSearchContainer(data:TagSearchContainer):void{
			_tagSearchContainer = data;
	   	}
	   	
	   	public function get proyectos():IList {
			return _proyectos;
	   	}
	   	public function set proyectos(data:IList):void{
			_proyectos = data;
	   	}
	   	
	   	public function get articulos():IList {
			return _articulos;
	   	}
	   	public function set articulos(data:IList):void{
			_articulos = data;
	   	}
	   	
	   	public function get categorias():IList {
			return _categorias;
	   	}
	   	public function set categorias(data:IList):void{
			_categorias = data;
	   	}
	   	
	   	public function get contactosWeb():IList {
			return _contactosWeb;
	   	}
	   	public function set contactosWeb(data:IList):void{
			_contactosWeb = data;
	   	}
	   	
	   	public function buscarSubCategoriasPara(nombreCategoria:String):IList{
			for each(var categoria:Categoria in this.categorias){
				if(categoria.nombre == nombreCategoria){
					return categoria.subCategorias;
				}
			}
			return null;
	   	}

		/**
		 * Inicializa las entidades basicas de la aplicacion para poder accederlas 
		 * desde cualquier lugar de la misma.
		 * 
		 */
		public function inicializar(uiComponent:UIComponent = null):void {
			if(!this.estaInicializado()){
				var transactionalBlock:TransactionalBlock = new TransactionalBlock();
				transactionalBlock.addEventListener("endsLoad", endLoad); 
				transactionalBlock.register("tagSearchService",		"buscarTodos").callbackFunction(inicializarTagSearchCallbackFunction);
				transactionalBlock.register("articuloService",		"findAll").callbackFunction(inicializarArticulosCallbackFunction);
				transactionalBlock.register("proyectoService",		"buscarTodos").callbackFunction(inicializarProyectosCallbackFunction);
				transactionalBlock.register("categoriaService",		"findAll").callbackFunction(inicializarCategoriasCallbackFunction);
				transactionalBlock.register("contactoWebService",	"buscarTodos").callbackFunction(inicializarContactosWebCallbackFunction);
				transactionalBlock.execute(uiComponent);
			}
	   	}
	   	
	   	public function estaInicializado():Boolean {
			return _tagSearchContainer != null || _contactosWeb != null && _categorias != null;
	   	}

		private function inicializarTagSearchCallbackFunction(data:TagSearchContainer):void{
			_tagSearchContainer = data;
		}

		private function inicializarProyectosCallbackFunction(data:IList):void{
			_proyectos = data;				
		}

		private function inicializarCategoriasCallbackFunction(data:IList):void{
			_categorias = data;				
		}

		private function inicializarArticulosCallbackFunction(data:IList):void{
			_articulos = data;				
		}

		private  function inicializarContactosWebCallbackFunction(data:IList):void{
			_contactosWeb = data;
		}

	 	public function clear():void{
	 		ApplicationLocator.instance.articulos = null;
	 		ApplicationLocator.instance.contactosWeb = null;
	 		ApplicationLocator.instance.categorias = null;
	 		ApplicationLocator.instance.tagSearchContainer = null;
	 	}
	 	
	 	public function reemplazarArticulo(_articulo:Articulo):void {
	 		var index:Number = -1;
	 		for each(var a:Articulo in _articulos){
	 			if(a.titulo == _articulo.titulo){
	 				index++;
	 				break;
	 			}
	 			index++;
	 		}
	 		if(index >= 0){
	 			_articulos.removeItemAt(index);
	 			_articulos.addItem(_articulo);
	 		}
	 	}
	}
}

class SingletonLock {
}
