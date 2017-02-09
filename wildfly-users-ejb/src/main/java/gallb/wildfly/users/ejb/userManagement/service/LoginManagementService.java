package gallb.wildfly.users.ejb.userManagement.service;

import gallb.wildfly.users.ejb.userManagement.business.LoginManagementBusiness;
import model.RoleType;

public class LoginManagementService {
	public RoleType login(String userName, String password){
		return new LoginManagementBusiness().authentication(userName, password);
	}
}
