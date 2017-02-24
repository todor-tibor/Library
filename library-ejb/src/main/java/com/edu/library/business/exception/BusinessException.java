package com.edu.library.business.exception;

import com.edu.library.exception.ErrorLevel;
import com.edu.library.exception.LibraryException;

/**
 * Specify exception for business layer.
 *
 * @author sipost
 */
public class BusinessException extends LibraryException {

	private static final long serialVersionUID = 5633917663462543264L;

	public BusinessException(final String message) {
		super(message, ErrorLevel.WARNING);
	}
}
