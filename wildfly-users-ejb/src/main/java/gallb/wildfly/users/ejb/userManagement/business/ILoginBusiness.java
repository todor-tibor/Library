package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import gallb.wildfly.users.common.LibraryException;
import model.Role;

/**
 * @author kiska Interface that the client module sees for login management
 */
public interface ILoginBusiness {
	/**
	 * Authenticates the use with user name given by {@code userName} and
	 * password {@code password}. Returns the roles of the user.
	 * 
	 * @param userName
	 *            - the userName given by the user
	 * @param password
	 *            - the password given by the user
	 * @return
	 * @throws LibraryException
	 */
	List<Role> authentication(String userName, String password) throws LibraryException;
}
