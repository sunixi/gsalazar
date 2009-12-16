/**
 * 
 */
package com.angel.code.generator.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * A reader for a single plugin. Is also used by the program to store the plugin
 * later on, since it neatly encapsulates everything.
 * 
 * @author Scott Lawrence
 * 
 */
public class PluginReader {
	private static Logger l = Logger.getLogger("Engine");

	private File file;

	/**
	 * 
	 * <p>
	 * Creates a plugin reader from a single file. Does not actually
	 * 
	 * read the plugin.
	 * </p>
	 * 
	 * @param f
	 *            the file from which the plugin will be read
	 */

	public PluginReader(File f) {

		l.finest("PluginReader created for " + f.toString());

		file = f;

	}

	/**
	 * 
	 * Reads the plugin
	 */

	public void read() {

		l.finest("Reading plugin: " + file.toString());

		try {

			JarFile jar = new JarFile(file);

			JarEntry manifest = null;

			for (JarEntry entry : Collections.list(jar.entries())) {

				if (entry.getName().equals("MANIFEST.GMF"))
					manifest = entry;

			}

			if (manifest == null) {

				l.warning("Plugin could not be read (no manifest): "
						+ file.toString());

				return;

			}

			Scanner input = new Scanner(jar.getInputStream(manifest));

			try {

				input.skip("Main-Class: ");

			} catch (NoSuchElementException e) {

				l.warning("Plugin could not be read (invalid manifest): "
						+ file.toString());

				return;

			}

			String main = input.next();

			main = main.replaceAll("\\\\", ".");

			main = main.replaceAll("\\.", "/");

			main += ".class";

			JarEntry mainClass = jar.getJarEntry(main);

			if (mainClass == null) {

				l.warning("Plugin could not be read (main class not found): "
						+ file.toString());

				return;

			}

			InputStream stream = jar.getInputStream(mainClass);

			byte[] bytes = new byte[(int) (mainClass.getSize())];

			for (int i = 0; i < mainClass.getSize(); i++) {

				bytes[i] = (byte) stream.read();

			}

			JarFileClassLoader myClassLoader = new JarFileClassLoader(jar);

			Class<?> c =

			myClassLoader.loadClass(main.replaceAll("/", ".").replaceAll(
					".class", ""));

			// myClassLoader.createClass(main.replaceAll("/",".").replaceAll(
			// ".class",""),bytes);

			// TODO gplugin=(GPlugin)c.newInstance();

			// now read in all of the other classes

			for (JarEntry entry : Collections.list(jar.entries())) {

				if (entry.getName().endsWith(".class")) {

					if (entry.getName().equals(main))
						continue;

					if (entry.getName().contains("$"))
						continue;

					// load this class

					l.finest("Loading class: " + entry.getName());

					stream = jar.getInputStream(entry);

					bytes = new byte[(int) (entry.getSize())];

					for (int i = 0; i < entry.getSize(); i++) {

						bytes[i] = (byte) stream.read();

					}

					try {

						myClassLoader.createClass(

						entry.getName().replaceAll("/", ".").replaceAll(
								".class", ""), bytes);

					} catch (LinkageError e) {

						l
								.warning("Linkage error occured – possible duplicate class names "
										+

										"when loading "
										+ entry.getName()
										+ " in " + file.toString());

						throw e;

					}

				} else {

					// its some other sort of file, so we'll deal with it later.

				}

			}

			copyFiles(jar);

			// we can't close the jar file, because some classes might want to
			// read from

			// it (for ResourceBundles, etc…)

		} catch (Exception e) {

			l.warning("An unknown error occured: " + e.toString());

			e.printStackTrace();

			return;

		}

		// TODO
		// l.fine("Plugin read: "+gplugin.getName()+" ("+file.toString()+")");

		// TODO gplugin.initialize();

		// TODO EngineUtils.registerPlugin(this);

	}

	/**
	 * 
	 * Copy an entry in a {@link JarFile} to the current directory.
	 * 
	 * @param jar
	 *            the {@link JarFile} of the entry
	 * 
	 * @param entry
	 *            the file that will be copied
	 * 
	 * @deprecated does not appropriately copy the resources…and nearly
	 * 
	 *             deleted my development workspace. Use {@link
	 *             copyFiles(JarFile)}
	 * 
	 *             instead.
	 */

	@SuppressWarnings("unused")
	private void copyEntry(JarFile jar, JarEntry entry) throws IOException {

		InputStream stream = jar.getInputStream(entry);

		byte[] bytes = new byte[(int) (entry.getSize())];

		for (int i = 0; i < entry.getSize(); i++) {

			bytes[i] = (byte) stream.read();

		}

		File f = new File(entry.getName());

		if (f.getParentFile() != null)
			f.getParentFile().mkdirs();

		if (f.exists())
			f.delete();

		f.createNewFile();

		FileOutputStream output = new FileOutputStream(f);

		output.write(bytes);

		output.close();

		f.deleteOnExit(); // so that we don't leave any residue

	}

	/**
	 * 
	 * <p>
	 * Copies files from the specified jar file to a temporary directory
	 * specified
	 * 
	 * by {@link GPluginResourcegetTempPath(GPlugin)}, and then schedules them
	 * for
	 * 
	 * deletion upon application exit.
	 * </p>
	 * 
	 * @param jar
	 * 
	 * @throws IOException
	 *             if problems are encountered while copying files from the
	 * 
	 *             jar to the temporary directory.
	 */

	private void copyFiles(JarFile jar) throws IOException {

		l.finer("Copying files from " + jar.toString());

		// create the relevant temporary directory

		File tmp = new File("c://");

		if (!tmp.mkdir())

			if (tmp.exists())
				;

			else
				throw new IOException("Could not create temporary directory");

		tmp.deleteOnExit();

		for (JarEntry entry : Collections.list(jar.entries())) {

			l.finest("Copying entry " + entry.getName());

			File f = new File("" + entry.getName());

			// check parents

			File p = f.getParentFile();

			Stack<File> files = new Stack<File>();

			while (!p.exists()) {

				files.push(p);

				p = p.getParentFile();

			}

			while (!files.empty()) {

				files.peek().mkdir();
				files.pop().deleteOnExit();
			}
			f.createNewFile();
			FileOutputStream output = new FileOutputStream(f);
			InputStream input = jar.getInputStream(entry);
			for (int i = 0; i < entry.getSize(); i++)
				output.write(input.read());
			f.deleteOnExit();
		}
	}

}