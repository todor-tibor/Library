/**
 * 
 */
package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Role;
import com.edu.library.model.User;
import com.edu.library.util.ExceptionHandler;

/**
 * User manager.
 * 
 * @author gallb
 * @author sipost
 * @author kiska
 * 
 */
@Named("userbean")

@SessionScoped
public class UserMB implements Serializable {

	private Logger oLogger = Logger.getLogger(UserMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IUserService oUserBean;

	@Inject
	LocaleManager localeManager;

	@Inject
	MessageService message;

	@Inject
	ExceptionHandler exceptionHandler;
	
	@Inject
	LoginMB loginMB;

	public void change() {
		oLogger.info("-----tab changed");
	}

	private List<User> userList = new ArrayList<>();// Currently displayed
													// users.
	private User loggedInUser=null, currentUser = null;// Currently selected user.

	private List<Role> currentRoles = new ArrayList<>();

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
	 * @return List of all users from database.
	 */
	public List<User> getAll() {
		userList.clear();
		try {
			userList = oUserBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
		return userList;
	}

	/**
	 * Search for user by user name and stores them in userList.
	 * 
	 * @param p_searchTxt
	 *            username.
	 * @return List of user objects found.
	 */
	public List<User> search(String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			userList.clear();
			try {
				userList = oUserBean.search(p_searchTxt);
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			message.warn("managedbean.string");
		}
		return userList;
	}

	/**
	 * Stores new user with user name.
	 * 
	 * @param p_name
	 *            - username, p_pass - password, p_idx - loyalty index
	 * 
	 */

	public void store(String p_name, String p_pass, int p_idx) {
		if (p_name.isEmpty() || "".equals(p_name)) {
			message.warn("managedbean.empty");
			return;
		}
		if ((p_idx > 10) || (p_idx < 0)) {
			message.warn("managedbean.loyalty");
			return;
		}
		if (currentRoles.isEmpty()) {
			message.warn("managedbean.empty");
			return;
		}

		User tmpUser = new User();
		tmpUser.setUserName(p_name);
		tmpUser.setLoyaltyIndex(p_idx);
		tmpUser.setPassword(p_pass);
		tmpUser.setRoles(currentRoles);
		try {
			oUserBean.store(tmpUser);
			userList.add(tmpUser);
			message.info("managedBean.storeSuccess");
		} catch (Exception e) {
			oLogger.error(e);
			message.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected user.
	 * 
	 * @param p_newTxt
	 *            - new user name.
	 */
	public void update(String p_newTxt) {
		if ((currentUser == null) || (p_newTxt.length() <= 3)) {
			message.warn("managedbean.string");
			return;
		}
		currentUser.setUserName(p_newTxt);
		try {
			oUserBean.update(currentUser);
			userList = oUserBean.getAll();
			message.info("managedbean.updateSuccess");
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}

	}

	/**
	 * Deletes currently selected user from database.
	 */
	public void remove() {
		if (currentUser == null) {
			message.error("Empty field");
		} else {
			try {
				oUserBean.remove(currentUser.getUuid());
				userList.remove(currentUser);
				message.info("managedbean.deleteSuccess");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Get user by name
	 * 
	 * @param p_username
	 */
	public void getByUserName() {
		if (loginMB.getUserName() != null && loginMB.getUserName().length() <= 3) {
			message.warn("managedbean.string");
			return;
		}
		try {
			loggedInUser = oUserBean.getByUserName(loginMB.getUserName());
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
	}

	public void getCurrentLang() {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(localeManager.getUserLocale());

	}

	public List<Role> getCurrentRoles() {
		currentRoles.clear();
		if (currentUser != null) {
			currentRoles = currentUser.getRoles();
		}
		return currentRoles;
	}

	public void setCurrentRoles(List<Role> currentRoles) {
		this.currentRoles = currentRoles;
	}

	/**
	 * Checks whether a user is selected.
	 * 
	 * @return - true if it is, false otherwise
	 */
	public Boolean isSelected() {
		if (this.currentUser == null) {
			return false;
		} else {
			return true;
		}
	}
 
	public User getLoggedInUser() {
		return loggedInUser;
	}
}
