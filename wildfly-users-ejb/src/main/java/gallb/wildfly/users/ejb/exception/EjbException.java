package gallb.wildfly.users.ejb.exception;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.LibraryException;

public class EjbException extends LibraryException {
	private final static String DELETE_ERROR_MESSAGE = "Cannot delete role (have user with coresponding role)";

	private static final long serialVersionUID = 5633917663462543264L;
	private static Logger oLogger = Logger.getLogger(EjbException.class);

	public static void getCause(PersistenceException e) throws EjbException {
		Throwable throwable = e;
		while (throwable != null && !(throwable instanceof SQLException)) {
			throwable = throwable.getCause();
		}
		if (throwable instanceof SQLException) {
			SQLException sqlex = (SQLException) throwable;
			String mess = sqlex.getMessage();
			mess = mess.substring(0, sqlex.getMessage().indexOf("for"));
			oLogger.error("------------------" + throwable.getClass() + "------------");

			if (sqlex.getMessage().indexOf("Cannot delete") >= 0) {
				mess = DELETE_ERROR_MESSAGE;
			}
			throw new EjbException(mess, sqlex);
		}
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
