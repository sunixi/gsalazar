/**
 *
 */
package com.angel.code.generator.helpers;



/**
 * @author William
 *
 */
public class PackageHelper {

	public static final String CLASS_PACKAGE_SEPARATOR = ".";

	/**
	 * Get simple data type name from a canonical data type.
	 * 
	 * @param classType canonical type to get its simple data type name.
	 * @return a simple data type name.
	 */
	public static String getClassSimpleName(String classType){
		String[] packages = classType.split("\\.");
		if(packages != null && packages.length > 0){
			return packages[packages.length - 1];
		}
		return classType;
	}

	private PackageHelper(){
		super();
	}

	/**
	 * Get package name for a canonical data type.
	 * 
	 * @param canonicalName to get its package name.
	 * @return a package name of a canonical data type.
	 */
	public static String getPackageOf(String canonicalName) {
		int lastIndex = canonicalName.lastIndexOf(".");
		String basePackage = "";
		if(lastIndex > 0){
			basePackage = canonicalName.substring(0, lastIndex);
		}
		return basePackage;
	}
}
