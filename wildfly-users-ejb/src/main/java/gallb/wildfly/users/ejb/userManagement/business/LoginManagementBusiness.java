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

	/**
	 * 
	 * @param userName - the user name of the user who wants to log in
	 * @param password - the hashed password that the user typed in
	 * @return - the role (type) of the user
		if login was not successful, returns an INVALID type, otherwise returns the highest priority roel
	 * @throws EjbException
	 */
	public RoleType authentication(String userName, String password) throws EjbException {
		User user = userBean.getByName(userName);
		if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) {
			List<Role> roles = user.getRoles();
			//check roles one by one, highest priority role comes first
			if (roles.contains(RoleType.LIBRARIAN.toString())) {
				return RoleType.LIBRARIAN;
			} else if (roles.contains(RoleType.READER.toString())) {
				return RoleType.READER;
			}
		}
		return RoleType.INVALID;
	}
}
