package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.ILoginService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.userManagement.LoginManagementBusiness;
import com.edu.library.model.Role;

/**
 * Implements the basics of user login. Validates the given input data.
 * 
 * @author kiska
 */
@Stateless
public class LoginManagementFacade implements ILoginService {
	
	@EJB
	private LoginManagementBusiness loginBusniess;

	public List<Role> login(String userName, String password) {
		ServiceValidation.checkString(userName);
		ServiceValidation.checkPassword(password);
		return loginBusniess.authentication(userName, password);
	}
}
