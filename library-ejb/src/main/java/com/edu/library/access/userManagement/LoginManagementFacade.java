package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.ILoginService;
import com.edu.library.LibraryException;
import com.edu.library.business.userManagement.LoginManagementBusiness;
import com.edu.library.model.Role;
import com.edu.library.util.EjbException;
import com.edu.library.util.ServiceValidation;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class LoginManagementFacade implements ILoginService {
	/**
	 * Error message for incorrect data
	 */

	@EJB
	private LoginManagementBusiness loginBusniess;

	public List<Role> login(String userName, String password) {
		ServiceValidation.checkString(userName);
		ServiceValidation.checkPassword(password);
		return loginBusniess.authentication(userName, password);
	}
}
