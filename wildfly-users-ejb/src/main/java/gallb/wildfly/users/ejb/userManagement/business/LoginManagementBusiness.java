package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.common.LibraryException;
import gallb.wildfly.users.common.PasswordEncrypter;
import gallb.wildfly.users.ejb.exception.EjbException;
import model.Role;
import model.User;

/**
 * @author kiska
 * 
 *         Implements a simple authentication process of a user.
 */
<<<<<<< Upstream, based on origin/PublicationWEB
@Stateless
@LocalBean
@Remote(ILoginBusiness.class)
public class LoginManagementBusiness implements ILoginBusiness {

	@EJB
=======
public class LoginManagementBusiness {
	@Inject
>>>>>>> b4fa023 not good
	private IUser userBean;
	/**
	 * Error message for the case when passwords don't match.
	 */
	private static final String PASSWORD_MISMATCH = "loginManagementBusiness.authentication.passwordMismatch";

	/**
	 * Checks whether the provided password is the same as the stored hashed
	 * password of the user. If the passwords match, the role of the user is
	 * returned
	 * 
	 * @param userName
	 *            - the user name of the user who wants to log in
	 * @param password
	 *            - the hashed password that the user typed in
	 * @return - the roles (type) of the user if login was not successful,
	 *         otherwise throws an error
<<<<<<< Upstream, based on origin/PublicationWEB
	 * @throws LibraryException
=======
	 * @throws LibraryException 
>>>>>>> b4fa023 not good
	 */
	public List<Role> authentication(String userName, String password) throws LibraryException {
<<<<<<< Upstream, based on origin/PublicationWEB

		if (userBean != null) {
			User user = userBean.getByUserName(userName);
			if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) { //
				return user.getRoles();
			}
=======
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^    "  + userName);
		User user = userBean.getByUserName(userName);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^    "  + userName+ "------------------");
		if (PasswordEncrypter.encypted(password, " ").equals(user.getPassword())) {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^    passwirds match" );
			return user.getRoles();
>>>>>>> b4fa023 not good
		}
		throw new EjbException(PASSWORD_MISMATCH);
	}
}
