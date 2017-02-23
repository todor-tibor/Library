package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.Role;
import com.edu.library.model.User;
import com.edu.library.util.PasswordEncrypter;

/**
 * Implements a simple authentication process of a user.
 * 
 * @author kiska
 */

@Stateless
@LocalBean
public class LoginManagementBusiness {

	@EJB
	private UserDao userAccess;

	/**
	 * Checks whether the provided password is the same as the stored hashed
	 * password of the user. If the passwords match, the role of the user is
	 * returned, otherwise an exception is thrown.
	 * 
	 * @param userName
	 *            - the user name of the user who wants to log in
	 * @param password
	 *            - the hashed password that the user typed in
	 * @return - the roles (type) of the user if login was not successful,
	 *         otherwise throws an error
	 */
	public List<Role> authentication(String userName, String password) {
		User user = userAccess.getByUserName(userName);
		if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) { //
			return user.getRoles();
		}
		throw new BusinessException(ErrorMessages.ERROR_PASSWORD);
	}
}
