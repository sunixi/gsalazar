/**
 *
 */
package com.angel.code.generator.exceptions;

import com.angel.architecture.exceptions.BusinessException;

/**
 * @author William
 *
 */
public class CodeGeneratorException extends BusinessException {

	/**
	 *
	 */
	private static final long serialVersionUID = 7740291273209956935L;

	/**
	 * @param cause
	 */
	public CodeGeneratorException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 */
	public CodeGeneratorException(String message) {
		super(message);
	}

	/**
	 * @param string
	 * @param root
	 */
	public CodeGeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

}
