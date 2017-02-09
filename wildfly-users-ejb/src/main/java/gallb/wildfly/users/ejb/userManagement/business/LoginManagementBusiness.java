package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import javax.inject.Inject;

import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.util.PasswordEncrypter;
import model.Role;
import model.RoleType;
import model.User;

/**
 * @author kiska Implements a simple authentication process of a user.
 */
public class LoginManagementBusiness {
	@Inject
	private UserBean userBean;
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
	 * @throws EjbException
	 */
	public List<Role> authentication(String userName, String password) throws EjbException {
		User user = userBean.getByName(userName);
		if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) {
			return user.getRoles();
		}
		throw new EjbException(PASSWORD_MISMATCH);
	}
}
