package com.edu.library.exception;

/**
 * A custom RuntimeException from which other exceptions are derived.
 * 
 * @author sipost
 *
 */
public class LibraryException extends RuntimeException {
	private ErrorLevel level;

	private static final long serialVersionUID = -2068564507434923735L;

	public LibraryException(String message, Throwable cause, ErrorLevel level) {
		super(message, cause);
		this.level = level;
	}

	public LibraryException(String message, ErrorLevel level) {
		super(message);
		this.level = level;
	}

	public LibraryException() {
		super();
		this.level = ErrorLevel.ERROR;
	}

	/**
	 * The level of the exception that can be thrown. These are enumerated in
	 * the ErrorLevel class.
	 * 
	 * @return
	 */
	public ErrorLevel getLevel() {
		return level;
	}
}
