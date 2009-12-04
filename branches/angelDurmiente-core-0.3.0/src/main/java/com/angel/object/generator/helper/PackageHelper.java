/**
 *
 */
package com.angel.object.generator.helper;



/**
 * @author William
 *
 */
public class PackageHelper {

	public static final String CLASS_PACKAGE_SEPARATOR = ".";
	
	
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
}
