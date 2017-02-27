package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.ILoginService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.userManagement.LoginManagementBusiness;
import com.edu.library.model.Role;

/**
 * Implements the basics of user login. Validates the given input data and calls
 * the business layer if params are valid
 *
 * @author kiska
 */
@Stateless
public class LoginManagementFacade implements ILoginService {

	@EJB
	private LoginManagementBusiness loginBusniess;

	@Override
	public List<Role> login(final String userName, final String password) {
		ServiceValidation.checkString(userName);
		ServiceValidation.checkPassword(password);
		return this.loginBusniess.authentication(userName, password);
	}
}
