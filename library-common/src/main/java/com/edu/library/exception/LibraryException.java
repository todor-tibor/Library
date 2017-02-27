package com.edu.library.exception;

/**
 * A custom RuntimeException from which other exceptions are derived.
 *
 * @author sipost
 *
 */
public class LibraryException extends RuntimeException {

	private static final long serialVersionUID = -2068564507434923735L;

	/**
	 * The level of the exception that can be thrown. These are enumerated in
	 * the ErrorLevel class.
	 */
	private final ErrorLevel level;

	public LibraryException(final String message, final Throwable cause, final ErrorLevel level) {
		super(message, cause);
		this.level = level;
	}

	public LibraryException(final String message, final ErrorLevel level) {
		super(message);
		this.level = level;
	}

	public LibraryException() {
		super();
		this.level = ErrorLevel.ERROR;
	}

	public ErrorLevel getLevel() {
		return this.level;
	}
}
