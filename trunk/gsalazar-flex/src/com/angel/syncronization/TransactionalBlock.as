package com.angel.syncronization{

import com.angel.locator.RemoteService;
import com.angel.locator.ScreenBlocker;
import com.angel.locator.ServiceLocator;
import com.angel.utils.RemoteServicesNames;

import flash.events.Event;
import flash.events.EventDispatcher;

import mx.collections.ArrayCollection;
import mx.collections.ArrayList;
import mx.core.UIComponent;

	[Event(name="endsLoad")]
    public class TransactionalBlock extends EventDispatcher {

        private var commands : ArrayList;
        [Bindable]
        private var wasExecuted: Boolean = false;
        [Bindable]
		public var image:Class;

        public function TransactionalBlock() {
            commands = new ArrayList();
        }

        public function register(serviceName : String, methodName : String) : Command {
            var command : Command = new Command(serviceName, methodName);
            commands.addItem(command);
            return command;
        }

        public function execute(uiComponent:UIComponent = null) : void {
			/*[Embed(source="/images/Fx_small.png")]
			var image:Class;*/
			ScreenBlocker.blockWithLoadingScreen(uiComponent, "Loading data...", image);
            var service:RemoteService = ServiceLocator.getService(RemoteServicesNames.MULTIPLE_SERVICE_NAME);
            service.listener = function (results : ArrayCollection):void {
            	trace("wasExecuted Value: " + wasExecuted);
            	if(!wasExecuted){
		            for each(var command : Command in commands.toArray()) {
		                var index : int = commands.getItemIndex(command);
		                command.callback(results.getItemAt(index));
		            }
		            dispatchEvent(new Event("endsLoad"));
		            ScreenBlocker.unblockScreen();
	            } else {
	            	wasExecuted != wasExecuted;
	            }
	        }; 

            service.execute(commands);
        }
    }

}