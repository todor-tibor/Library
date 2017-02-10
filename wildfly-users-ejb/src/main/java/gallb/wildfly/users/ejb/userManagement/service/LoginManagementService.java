package gallb.wildfly.users.ejb.userManagement.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import gallb.wildfly.users.common.ILogin;
import gallb.wildfly.users.common.LibraryException;
import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.userManagement.business.LoginManagementBusiness;
import gallb.wildfly.users.ejb.util.ServiceValidation;
import model.Role;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class LoginManagementService implements ILogin {
	/**
	 * Error message for incorrect data
	 */
	private static final String INPUT_DATA_VALIDATION_EXCEPTION = "loginManagementService.login.invalidData";

	public List<Role> login(String userName, String password) throws LibraryException {
		System.out.println("/////********---------------------     " );
		//if (ServiceValidation.checkString(userName)) {
			System.out.println("/////********---------------------     " + userName);
			//if (ServiceValidation.checkPassword(password)) {
				System.out.println("/////********---------------------     " + password);
				return new LoginManagementBusiness().authentication(userName, password);
			//}
		//}
	//	throw new EjbException(INPUT_DATA_VALIDATION_EXCEPTION);
	}
}
