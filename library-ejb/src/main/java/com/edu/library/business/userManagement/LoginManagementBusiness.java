package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.IUserService;
import com.edu.library.LibraryException;
import com.edu.library.PasswordEncrypter;
import com.edu.library.util.EjbException;

import edu.com.library.model.Role;
import edu.com.library.model.User;

/**
 * @author kiska
 * 
 *         Implements a simple authentication process of a user.
 */

@Stateless
@LocalBean
public class LoginManagementBusiness {

	@EJB
	private IUserService userBean;
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
	 * 
	 */
	public List<Role> authentication(String userName, String password) throws LibraryException {
		if (userBean != null) {
			User user = userBean.getByUserName(userName);
			if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) { //
				return user.getRoles();
			}
		}
		throw new EjbException(PASSWORD_MISMATCH);
	}
}
