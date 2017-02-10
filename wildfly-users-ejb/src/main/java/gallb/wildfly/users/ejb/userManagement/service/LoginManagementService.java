package gallb.wildfly.users.ejb.userManagement.service;

import java.util.List;

import gallb.wildfly.users.common.ILogin;
import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.userManagement.business.LoginManagementBusiness;
import gallb.wildfly.users.ejb.util.ServiceValidation;
import model.Role;
import model.RoleType;
/**
 * @author kiska
 *Implements the basics of user login. 
 *Validates the given the input data.
 */
public class LoginManagementService implements ILogin<Role>{
	/**
	 * Error message for incorrect data
	 */
	private static final String INPUT_DATA_VALIDATION_EXCEPTION = "loginManagementService.login.invalidData";

	public List<Role> login(String userName, String password) throws EjbException{
		if (ServiceValidation.checkString(userName) && ServiceValidation.checkPassword(password)){
			return new LoginManagementBusiness().authentication(userName, password);
		} 
		throw new EjbException(INPUT_DATA_VALIDATION_EXCEPTION);
	}
}
