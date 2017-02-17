package com.edu.library.business.exception;

import com.edu.library.LibraryException;

public class BusinessException extends LibraryException {

	private static final long serialVersionUID = 5633917663462543264L;

	public BusinessException() {
		super();
		setLevel(2);
	}

	public BusinessException(String message) {
		super(message);
		setLevel(2);
	}

	public BusinessException(String message, Throwable cause) {
		super(message);
		setLevel(2);
	}

}
