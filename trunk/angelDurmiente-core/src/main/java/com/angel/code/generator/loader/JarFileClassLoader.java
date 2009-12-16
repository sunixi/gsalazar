/**
 * 
 */
package com.angel.code.generator.loader;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileClassLoader extends ClassLoader {

	private JarFile jar;

	public JarFileClassLoader(JarFile j) {

		super(JarFileClassLoader.class.getClassLoader());
		jar = j;
	}

	public Class<?> createClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}

	public Class<?> findClass(String oname) throws ClassNotFoundException {
		String name = oname.replaceAll("\\.", "/").concat(".class");
		if (jar.getEntry(name) == null) {
			if (jar.getEntry(oname) == null)
				throw new ClassNotFoundException();
			else
				name = oname;
		}
		try {
			JarEntry mainClass = (JarEntry) jar.getEntry(name);
			InputStream stream = jar.getInputStream(mainClass);
			byte[] bytes = new byte[(int) (mainClass.getSize())];
			for (int i = 0; i < mainClass.getSize(); i++) {
				bytes[i] = (byte) stream.read();
			}
			Class<?> c = createClass(oname, bytes);
			return c;
		} catch (Exception e) {
			if (e instanceof ClassNotFoundException)
				throw (ClassNotFoundException) e;
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}

	public URL findResource(String name) {
		if (jar.getEntry(name) == null)
			return null;
		try {
			return new URL("jar:file:///" + jar.getName() + "!/" + name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}