package com.edu.library.model.util;

/**
 * Specific exception for JAXB import/export and Jasper reports
 *
 * @author sipost
 */
public class JpaException extends RuntimeException {

	private static final long serialVersionUID = -8655379517301321218L;

	public JpaException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public JpaException(final String message) {
		super(message);
	}
}
