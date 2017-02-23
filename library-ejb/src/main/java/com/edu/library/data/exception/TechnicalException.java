package com.edu.library.data.exception;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.jboss.logging.Logger;

import com.edu.library.LibraryException;
/**
 * Specify exception for DAO layer.
 * 
 * @author sipost
 */
public class TechnicalException extends LibraryException {

	private static final long serialVersionUID = 5633917663462543264L;
	private static Logger oLogger = Logger.getLogger(TechnicalException.class);

	public TechnicalException(PersistenceException e) throws TechnicalException {
		String message = "ejb.message.noEntity";
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

	public TechnicalException(String message, Throwable cause) {
		super(message);
		setLevel(1);
	}

}
