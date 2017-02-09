package gallb.wildfly.users.ejb.userManagement.service;

import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.userManagement.business.LoginManagementBusiness;
import gallb.wildfly.users.ejb.util.ServiceValidation;
import model.RoleType;
/**
 * @author kiska
 *
 */
public class LoginManagementService {
	private static final String INPUT_DATA_VALIDATION_EXCEPTION = "loginManagementService.login.invalidData";
	
	/**
	 * 
	 * @param userName - the user name of the user
	 * @param password - the password the user typed in
	 * @return - the role of the user
	 * @throws EjbException
	 */
	public RoleType login(String userName, String password) throws EjbException{
		if (ServiceValidation.checkString(userName) && ServiceValidation.checkPassword(password)){
			return new LoginManagementBusiness().authentication(userName, password);
		} 
		throw new EjbException(INPUT_DATA_VALIDATION_EXCEPTION);
	}
}
