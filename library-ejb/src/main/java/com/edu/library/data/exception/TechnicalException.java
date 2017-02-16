package com.edu.library.data.exception;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.jboss.logging.Logger;

public class TechnicalException extends RuntimeException {

	private static final long serialVersionUID = 5633917663462543264L;
	private static Logger oLogger = Logger.getLogger(TechnicalException.class);

	public TechnicalException(PersistenceException e) throws TechnicalException {
		String message = "ejb.message.error";
		if (e instanceof EntityNotFoundException) {
			message = "ejb.message.noEntity";
		} else if (e instanceof NonUniqueResultException) {
			message = "ejb.message.nonUniqResult";
		} else if (e instanceof RollbackException) {
			message = "ejb.message.cantExecute";
		}
		oLogger.error(message);
		throw new TechnicalException(message, e);
	}

	public TechnicalException() {
		super();
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(String message, Throwable cause) {
		super(message);
	}

}
