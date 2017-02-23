package com.edu.library.data.exception;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import com.edu.library.exception.ErrorLevel;
import com.edu.library.exception.LibraryException;

/**
 * Specify exception for DAO layer.
 * 
 * @author sipost
 */
public class TechnicalException extends LibraryException {

	private static final long serialVersionUID = 5633917663462543264L;

	public TechnicalException(PersistenceException e) {
		String message = "ejb.message.noEntity";
		if (e instanceof EntityNotFoundException) {
			message = "ejb.message.noEntity";
		} else if (e instanceof NonUniqueResultException) {
			message = "ejb.message.nonUniqResult";
		} else if (e instanceof RollbackException) {
			message = "ejb.message.cantExecute";
		}
		new TechnicalException(message, e);
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, ErrorLevel.ERROR);
	}
}
