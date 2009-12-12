/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.helpers.PackageHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class DataException implements CodeConvertible, Importable{

	private String canonicalName;
	
	public DataException(String canonicalName){
		super();
		this.setCanonicalName(canonicalName);
	}

	public String convertCode() {
		return this.getSimpleName();
	}

	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		importsType.add(this.getCanonicalName());
		return importsType;
	}
	
	public String getSimpleName(){
		return PackageHelper.getClassSimpleName(this.getCanonicalName());
	}

	/**
	 * @return the canonicalName
	 */
	public String getCanonicalName() {
		return canonicalName;
	}

	/**
	 * @param canonicalName the canonicalName to set
	 */
	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	public boolean hasCanonicalName(String canonicalName){
		return this.getCanonicalName().equalsIgnoreCase(canonicalName);
	}	
}
