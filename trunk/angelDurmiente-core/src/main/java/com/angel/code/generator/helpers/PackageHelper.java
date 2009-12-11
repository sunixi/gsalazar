/**
 *
 */
package com.angel.code.generator.helpers;

import com.angel.code.generator.data.impl.as3.converter.FlexDataTypesConverter;
import com.angel.code.generator.data.impl.as3.converter.FlexDataTypesConverter.FlexTypeImport;
import com.angel.common.helpers.StringHelper;



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

	public static String getFlexClassSimpleName(String javaType){
		FlexTypeImport flexTypeImport = FlexDataTypesConverter.getInstance().getFlexTypeFrom(javaType);
		if(flexTypeImport != null){
			String flexType = flexTypeImport.getCanonicalType();
			if(flexType.contains(".")){
				return getClassSimpleName(flexType);
			}
			return flexType;
		}
		return null;
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

	/**
	 * Create a variable with a canonical name. If canonical name is my.package.beans.Bean, the
	 * variable name will be bean. 
	 *  
	 * @param variableCanonicalName to create its variable name.
	 * @return a variable name from a canonical name.
	 */
	public static String getClassSimpleVariableName(String canonicalName) {
		if(StringHelper.isNotEmpty(canonicalName) && canonicalName.length() > 2){
			String simpleName = getClassSimpleName(canonicalName);
			String variableName = simpleName.substring(0, 1).toLowerCase().concat(simpleName.substring(1, simpleName.length()));
			return variableName;
		}
		return canonicalName;
	}
}
