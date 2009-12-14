package com.angel.code.generator.plugin.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * The activator class controls the plug-in life cycle
 */
public class CodeGeneratorCommand extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object applicationContext = event.getApplicationContext();
		return null;
	}

}
