package com.edu.library.business.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 5633917663462543264L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message);
	}

}
