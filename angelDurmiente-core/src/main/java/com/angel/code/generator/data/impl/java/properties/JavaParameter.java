/**
 * 
 */
package com.angel.code.generator.data.impl.java.properties;

import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.common.helpers.StringHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaParameter extends DataParameter {

	private boolean importType;
	
	public JavaParameter(String name, String canonicalType){
		super(name, canonicalType);
		this.setImportType(true);
	}
	
	public JavaParameter(String parameterType){
		this(StringHelper.EMPTY_STRING, parameterType);
	}

	public JavaParameter() {
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
	public <T extends DataAnnotation> T createAnnotation() {
		DataAnnotation dataAnnotation = new JavaAnnotation();
		super.addAnnotation(dataAnnotation);
		return (T) dataAnnotation;
	}
}
