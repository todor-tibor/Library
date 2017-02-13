/**
 * 
 */
package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.common.LibraryException;
import model.User;

/**
 * User manager.
 * 
 * @author gallb
 * @author sipost
 * 
 */
@Named("userbean")

@SessionScoped
public class UserMB implements Serializable {

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(UserMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IUser oUserBean;

	/**
	 * 
	 */
	private List<User> userList = new ArrayList<>();// Currently displayed
													// users.
	private User currentUser = null;// Currently selected user.

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
		userList.clear();
		try {
			oLogger.info("--getAllUsers()--users queried");
			userList = oUserBean.getAll();
		} catch (LibraryException e) {
			MessageService.error("Server internal error.");
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
	public List<User> search(String p_searchTxt) {
		oLogger.info("--search user--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			userList.clear();
			try {
				userList = oUserBean.search(p_searchTxt);
			} catch (LibraryException e) {
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
		return userList;
	}

	/**
	 * Stores new user with username.
	 * 
	 * @param p_name - username, p_pass - password, p_idx - loyalty index
	 *         
	 */

	public void store(String p_name, String p_pass, int p_idx) {
		oLogger.info("--store user--");
		oLogger.info("--store param: " + p_name);
		if (p_name.isEmpty()) {
			MessageService.error("Empty field");
			return;
		}
		if (p_name == "") {
			MessageService.error("Empty field");
		return;
		}
		if ((p_idx > 10)  || (p_idx < 0)) {
			MessageService.error("Loyalty index must be an integer beteen 0..10");
			return;
		}
		try {
			User tmpUser = new User();
			tmpUser.setUserName(p_name);
			tmpUser.setLoyaltyIndex(p_idx);
			tmpUser.setPassword(p_pass);
			oUserBean.store(tmpUser);
			userList.add(tmpUser);
			MessageService.info("Succesfully added: " + p_name);
		} catch (LibraryException e) {
			MessageService.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected user.
	 * 
	 * @param p_newTxt
	 *            - new username.
	 */
	public void update(String p_newTxt) {
		oLogger.info("--update user ManagedBean--id:" + currentUser.getUserName() + "new name: " + p_newTxt);
		if ((currentUser != null) && (p_newTxt.length() >= 3)) {
			try {
				currentUser.setUserName(p_newTxt);
				oUserBean.update(currentUser);
				userList = oUserBean.getAll();
				oLogger.info("**********************update succesfull************************************");
				MessageService.info("Update succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.error("New name too short.");
		}
	}

	/**
	 * Deletes currently selected user from persistency.
	 */
	public void remove() {
		oLogger.info("--remove user by Id ManagedBean--p_id: " + currentUser.getUserName());
		if (currentUser == null) {
			MessageService.error("Empty field");
		} else {
			try {
				oUserBean.remove(currentUser.getUuid());
				userList.remove(currentUser);
				MessageService.info("Delete succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		}
	}
}