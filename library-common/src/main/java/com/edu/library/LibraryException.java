package com.edu.library;

/**
 * 
 * @author sipost
 *
 */
public class LibraryException extends RuntimeException {

	private static final long serialVersionUID = -2068564507434923735L;

	public LibraryException() {
		super();
	}

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}

	public LibraryException(String message) {
		super(message);
	}

}
