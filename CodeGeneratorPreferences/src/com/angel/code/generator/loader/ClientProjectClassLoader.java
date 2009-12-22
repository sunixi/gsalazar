/**
 * 
 */
package com.angel.code.generator.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.jdt.core.ICompilationUnit;

/**
 * @author C088347
 * 
 */
public class ClientProjectClassLoader {
	private URLClassLoader innerCL;

	/** an automatic generated delegate method */
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return innerCL.loadClass(name);
	}

	public ClientProjectClassLoader(ICompilationUnit c, ClassLoader parent, String projectCompileDirectory) {
		String unitPath = c.getResource().getProject().getLocationURI()
				.toString();
		URL binURI = null;
		try {
			binURI = new URL(unitPath + projectCompileDirectory);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		innerCL = new URLClassLoader(new URL[] { binURI }, parent);
	}

}
