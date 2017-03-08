package com.edu.library.model.util;

/**
 * Specific exception for JAXB import/export
 *
 * @author sipost
 */
public class JaxbException extends RuntimeException {

	private static final long serialVersionUID = -8655379517301321218L;

	public JaxbException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public JaxbException(final String message) {
		super(message);
	}
}
