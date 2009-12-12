/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.helpers.ImportsHelper;
import com.angel.code.generator.helpers.PackageHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class DataInterface implements Importable, CodeConvertible {

	private String canonicalName;
	
	public DataInterface(String canonicalName){
		super();
		this.setCanonicalName(canonicalName);
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
	
	public String getSign(){
		return this.getSimpleName();
	}

	public String getSimpleName(){
		return PackageHelper.getClassSimpleName(this.getCanonicalName());
	}

	public String convertCode(){
		return this.getSign();
	}

	public List<String> getImportsType(){
		List<String> importsType = new ArrayList<String>();
		ImportsHelper.addImport(importsType, this.getCanonicalName());
		return importsType;
	}

	public boolean hasCanonicalName(String canonicalName) {
		return this.getCanonicalName().equalsIgnoreCase(canonicalName);
	}

	public boolean hasName(String name) {
		return this.getName().equalsIgnoreCase(name);
	}

	public String getName(){
		return PackageHelper.getClassSimpleName(this.getCanonicalName());
	}
}
