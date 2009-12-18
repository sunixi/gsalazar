/**
 * 
 */
package com.angel.code.generator;


import javassist.ClassPath;
import javassist.ClassPool;

import org.junit.Test;

import com.angel.architecture.comparators.MenuComparator;
import com.angel.code.generator.loader.CustomClassLoader;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class CustomClassLoaderTest {


	@Test
	public void testLoadClass() throws ClassNotFoundException{	
        CustomClassLoader test = new CustomClassLoader();
        test.loadClass(MenuComparator.class.getCanonicalName());
	}
	
	@Test
	public void testClassPool() {
		ClassPool cp = ClassPool.getDefault();
		ClassPath classPath = cp.appendSystemPath();
		
	}


}
