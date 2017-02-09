package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import javax.inject.Inject;

import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.util.PasswordEncrypter;
import model.Role;
import model.RoleType;
import model.User;

/**
 * @author kiska
 *
 */
public class LoginManagementBusiness {
	@Inject
	private UserBean userBean;

	public RoleType authentication(String userName, String password) throws EjbException {
		User user = userBean.getByName(userName);
		if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) {
			List<Role> roles = user.getRoles();
			if (roles.contains(RoleType.LIBRARIAN.toString())) {
				return RoleType.LIBRARIAN;
			} else if (roles.contains(RoleType.READER.toString())) {
				return RoleType.READER;
			}
		}
		return RoleType.INVALID;
	}
}
