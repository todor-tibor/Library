package gallb.wildfly.users.common;

import java.util.List;

import model.BaseEntity;
import model.Role;

/**
 * 
 * @author kiska
 *
 *         The login interface to which the client has access. Defines the login
 *         process.
 */
public interface ILogin{
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
	public List<Role> login(String userName, String password) throws LibraryException;
}
