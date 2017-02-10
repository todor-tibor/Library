package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import javax.inject.Inject;

import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.common.LibraryException;
import gallb.wildfly.users.common.PasswordEncrypter;
import gallb.wildfly.users.ejb.exception.EjbException;
import model.Role;
import model.User;

/**
 * @author kiska
 * 
 *         Implements a simple authentication process of a user.
 */
public class LoginManagementBusiness {
	@Inject
	private IUser userBean;
	/**
	 * Error message for the case when passwords don't match.
	 */
	private static final String PASSWORD_MISMATCH = "loginManagementBusiness.authentication.passwordMismatch";

	/**
	 * Checks whether the provided password is the same as the stored hashed
	 * password of the user. If the passwords match, the role of the user is
	 * returned
	 * 
	 * @param userName
	 *            - the user name of the user who wants to log in
	 * @param password
	 *            - the hashed password that the user typed in
	 * @return - the roles (type) of the user if login was not successful,
	 *         otherwise throws an error
	 * @throws LibraryException 
	 */
	public List<Role> authentication(String userName, String password) throws LibraryException {
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^    "  + userName);
		User user = userBean.getByUserName(userName);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^    "  + userName+ "------------------");
		if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^    passwirds match" );
			return user.getRoles();
		}
		throw new EjbException(PASSWORD_MISMATCH);
	}
}
