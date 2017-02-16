package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.business.userManagement.LoginManagementBusiness;
import com.edu.library.util.EjbException;
import com.edu.library.util.ServiceValidation;

import gallb.wildfly.users.common.ILoginService;
import gallb.wildfly.users.common.LibraryException;
import model.Role;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class LoginManagementFacade implements ILoginService {
	/**
	 * Error message for incorrect data
	 */
	private static final String INPUT_DATA_VALIDATION_EXCEPTION = "loginManagementService.login.invalidData";
	@EJB
	private LoginManagementBusiness loginBusniess;

	public List<Role> login(String userName, String password) throws LibraryException {
		if (ServiceValidation.checkString(userName)) {
			if (ServiceValidation.checkPassword(password)) {
				return loginBusniess.authentication(userName, password);
			}
		}
		throw new EjbException(INPUT_DATA_VALIDATION_EXCEPTION);
	}
}
