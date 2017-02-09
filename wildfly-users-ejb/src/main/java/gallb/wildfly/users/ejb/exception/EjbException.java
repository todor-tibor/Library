package gallb.wildfly.users.ejb.exception;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.LibraryException;

public class EjbException extends LibraryException {

	private static final long serialVersionUID = 5633917663462543264L;
	private static Logger oLogger = Logger.getLogger(EjbException.class);

	public EjbException(PersistenceException e) throws EjbException {
		String message = "ejb.message.error";
		if (e instanceof EntityNotFoundException) {
			message = "ejb.message.noEntity";
		} else if (e instanceof NonUniqueResultException) {
			message = "ejb.message.nonUniqResult";
		} else if (e instanceof RollbackException) {
			message = "ejb.message.cantExecute";
		}
		oLogger.error(message);
		throw new EjbException(message, e);
	}

	public EjbException() {
		super();
	}

	public EjbException(String message) {
		super(message);
	}

	public EjbException(String message, Throwable cause) {
		super(message);
	}

}
