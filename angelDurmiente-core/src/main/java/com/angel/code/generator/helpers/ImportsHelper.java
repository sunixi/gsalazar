/**
 *
 */
package com.angel.code.generator.helpers;

import java.util.ArrayList;
import java.util.List;



/**
 * @author William
 *
 */
public class ImportsHelper {

	public static final String IMPORT_PREFIX = "import ";

	public static final String END_OF_LINE = ";";
	
	private static final List<String> JAVA_PRIMITIVES_CLASSES = new ArrayList<String>();
	static {
		JAVA_PRIMITIVES_CLASSES.add("byte");
		JAVA_PRIMITIVES_CLASSES.add("short");
		JAVA_PRIMITIVES_CLASSES.add("int");
		JAVA_PRIMITIVES_CLASSES.add("long");
		JAVA_PRIMITIVES_CLASSES.add("float");
		JAVA_PRIMITIVES_CLASSES.add("double");
		JAVA_PRIMITIVES_CLASSES.add("boolean");
		JAVA_PRIMITIVES_CLASSES.add("char");
	}
	
	
	public static void addImport(List<String> imports, String typeClass){
		if(!isJavaPrimitiveType(typeClass)){
			String className = typeClass;
			if(!typeClass.startsWith(IMPORT_PREFIX)){
				className = IMPORT_PREFIX + typeClass + END_OF_LINE;
			}
			if(!imports.contains(className)){
				imports.add(className);
			}
		}
	}
	
	public static boolean isJavaPrimitiveType(String typeClass){
		String cleanTypeClass = typeClass.trim().replaceAll(IMPORT_PREFIX, "").replaceAll(END_OF_LINE, "").trim();
		return JAVA_PRIMITIVES_CLASSES.contains(cleanTypeClass);
	}

	private ImportsHelper(){
		super();
	}
}
