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
public abstract class DataException implements CodeConvertible, Importable{

	private String canonicalType;
	
	public DataException(){
		super();
	}

	public String convertCode() {
		return this.getSimpleType();
	}

	public List<String> getImportsType() {
		List<String> importsType = new ArrayList<String>();
		importsType.add(this.getCanonicalType());
		return importsType;
	}
	
	public String getSimpleType(){
		return PackageHelper.getClassSimpleName(this.getCanonicalType());
	}

	/**
	 * @return the canonicalType
	 */
	public String getCanonicalType() {
		return canonicalType;
	}

	/**
	 * @param canonicalType the canonicalType to set
	 */
	public void setCanonicalType(String canonicalType) {
		this.canonicalType = canonicalType;
	}

	public boolean hasCanonicalType(String canonicalType){
		return this.getCanonicalType().equalsIgnoreCase(canonicalType);
	}
}
