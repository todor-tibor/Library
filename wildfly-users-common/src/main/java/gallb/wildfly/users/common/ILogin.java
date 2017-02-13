package gallb.wildfly.users.common;

import java.util.List;

import model.BaseEntity;

/**
 * 
 * @author kiska
 *
 *         The login interface to which the client has access. Defines the login
 *         process.
 */
public interface ILogin<X extends BaseEntity> {
	/**
	 * Does a validation on the input data. If these are correct, tries to
	 * authenticate the user.
	 * 
	 * @param userName
	 *            - the user name of the user
	 * @param password
	 *            - the password the user typed in
	 * @return - the roles of the user
	 * @throws LibraryException
	 */
	public List<X> login(String userName, String password) throws LibraryException;
}
