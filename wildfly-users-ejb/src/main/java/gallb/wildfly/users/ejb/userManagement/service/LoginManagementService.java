package gallb.wildfly.users.ejb.userManagement.service;

import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.userManagement.business.LoginManagementBusiness;
import model.RoleType;
/**
 * @author kiska
 *
 */
public class LoginManagementService {
	public RoleType login(String userName, String password) throws EjbException{
		return new LoginManagementBusiness().authentication(userName, password);
	}
}
