package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.ILogin;
import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.common.LibraryException;
import model.User;

/**
 * @author kiska
 * 
 *         Login
 * 
 */
@Named("loginbean")

@SessionScoped
public class LoginManagedBean implements Serializable {

	private Logger oLogger = Logger.getLogger(ManagedBean.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IUser oUserBean;
	@Inject
	private ILogin oLoginBean;
	/**
	 * 
	 */
	private List<User> userList = new ArrayList<>();// Currently displayed
													// users.
	private User currentUser = null;// Currently selected user.
	private String password;
	private String user_name;

	public List<User> getUserList() {
		return userList;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Requests all user objects and stores them in userList.
	 * 
	 * @return List of all users from persistency.
	 */
	public List<User> getAll() {
		oLogger.info("--getAllUsers()--");
		userList = new ArrayList<>();
		try {
			oLogger.info("--getAllUsers()--users queried");
			userList = oUserBean.getAll();
		} catch (LibraryException e) {
			// this.error("Server internal error.");
		}
		return userList;
	}

	/**
	 * Search for user by username and stores them in userList.
	 * 
	 * @param p_searchTxt
	 *            username.
	 * @return List of user objects found.
	 */
	public User search() {
		oLogger.info("--search user--" + this.getUser_name());
		if (this.getUser_name().length() >= 3) {
			try {
				System.out.println("/*/*-/*-/ " + this.getUser_name() + "    " + this.getPassword());
				oLoginBean.login(this.getUser_name(), this.getPassword());
				System.out.println("///**********-----------    success    -*-*-*-*-*-");
			} catch (LibraryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// this.error("Keyword too short. Min. 3 characters req.");
		}
		return currentUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
