package com.edu.library.business.exception;

import com.edu.library.LibraryException;

/**
 * Specify exception for business layer.
 * 
 * @author sipost
 */
public class BusinessException extends LibraryException {

	private static final long serialVersionUID = 5633917663462543264L;

	public BusinessException(String message) {
		super(message);
		setLevel(2);
	}
}
