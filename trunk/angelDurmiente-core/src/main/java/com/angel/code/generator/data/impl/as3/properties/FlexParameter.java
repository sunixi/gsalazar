/**
 * 
 */
package com.angel.code.generator.data.impl.as3.properties;

import com.angel.code.generator.data.impl.as3.annotations.FlexAnnotation;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexParameter extends DataParameter {
//TODO ver si es necesario.
	private boolean importType;
	
	public FlexParameter(String name, String canonicalType){
		super(name, canonicalType);
		this.setImportType(true);
	}
	
	public FlexParameter(String parameterType){
		this(StringHelper.EMPTY_STRING, parameterType);
	}

	public FlexParameter() {
		this(StringHelper.EMPTY_STRING);
	}

	/**
	 * @return the importType
	 */
	public boolean isImportType() {
		return importType;
	}

	/**
	 * @param importType the importType to set
	 */
	public void setImportType(boolean importType) {
		this.importType = importType;
	}

	public void notImportType() {
		this.setImportType(false);
	}
	
	public void importType() {
		this.setImportType(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createAnnotation(String canonicalType) {
		DataAnnotation dataAnnotation = new FlexAnnotation(canonicalType);
		super.addAnnotation(dataAnnotation);
		return (T) dataAnnotation;
	}
}
