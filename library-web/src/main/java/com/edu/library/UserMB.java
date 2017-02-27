/**
 *
 */
package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Role;
import com.edu.library.model.User;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

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

	private final Logger logger = Logger.getLogger(UserMB.class);
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
		this.logger.info("--tab changed--");
	}
  
	/**
	 * Currently displayed users.
	 */
	private List<User> userList = new ArrayList<>();

	/**
	 * Currently logged in user and currently selected user.
	 */
	private User loggedInUser = null, currentUser = null;

	/**
	 * Currently selected user roles
	 */
	private List<Role> currentRoles = new ArrayList<>();

	/**
	 * Requests all user objects and stores them in userList.
	 *
	 * @return List of all users from database.
	 */
	public List<User> getAll() {
		this.userList.clear();
		try {
			this.userList = this.oUserBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.userList;
	}

	/**
	 * Search for user by user name and stores them in userList.
	 *
	 * @param p_searchTxt
	 *            username.
	 * @return List of user objects found.
	 */
	public List<User> search(final String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			this.userList.clear();
			try {
				this.userList = this.oUserBean.search(p_searchTxt);
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.warn("managedbean.string");
		}
		return this.userList;
	}

	/**
	 * Stores new user.
	 *
	 * @param name
	 *            - username, password - password, loyaltyIndex - loyalty index, enail- e-mail
	 *
	 */

	public void store(final String name, final String password, final int loyaltyIndex, final String email) {
		if (name.isEmpty() || "".equals(name)) {
			this.message.warn("managedbean.empty");
			return;
		}
		if ((loyaltyIndex > 10) || (loyaltyIndex < 0)) {
			this.message.warn("managedbean.loyalty");
			return;
		}
		if (this.currentRoles.isEmpty()) {
			this.message.warn("managedbean.empty");
			return;
		}

		final User tmpUser = new User();
		tmpUser.setUserName(name);
		tmpUser.setLoyaltyIndex(loyaltyIndex);
		tmpUser.setPassword(password);
		tmpUser.setRoles(this.currentRoles);
		tmpUser.setEmail(email);
		try {
			this.oUserBean.store(tmpUser);
			this.userList.add(tmpUser);
		} catch (final Exception e) {
			this.logger.error(e);
			this.message.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected user.
	 *
	 * @param newName
	 *            - new user name., email - e-mail
	 */
	public void update(final String newName, final String email) {
		if ((this.currentUser == null) || (newName.length() <= 3)) {
			this.message.warn("managedbean.string");
			return;
		}
		this.currentUser.setUserName(newName);
		if (!email.isEmpty()) {
			currentUser.setEmail(email);
		}
		try {
			this.oUserBean.update(this.currentUser);
			this.userList = this.oUserBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Deletes currently selected user from database.
	 */
	public void remove() {
		if (this.currentUser == null) {
			this.message.error("Empty field");
		} else {
			try {
				this.oUserBean.remove(this.currentUser.getUuid());
				this.userList.remove(this.currentUser);
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Get logged in user by name
	 */
	public void getByUserName() {
		if (this.loginMB.getUserName() != null && this.loginMB.getUserName().length() <= 3) {
			this.message.warn("managedbean.string");
			return;
		}
		try {
			this.loggedInUser = this.oUserBean.getByUserName(this.loginMB.getUserName());
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
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

	/**
	 * Get roles of currently selected user
	 *
	 * @return list of roles
	 */
	public List<Role> getCurrentRoles() {
		this.currentRoles.clear();
		if (this.currentUser != null) {
			this.currentRoles = this.currentUser.getRoles();
		}
		return this.currentRoles;
	}

	/*
	 * Getters and setters for private attributes
	 */
	public void setCurrentRoles(final List<Role> currentRoles) {
		this.currentRoles = currentRoles;
	}

	public User getLoggedInUser() {
		return this.loggedInUser;
	}

	public List<User> getUserList() {
		return this.userList;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(final User currentUser) {
		this.currentUser = currentUser;
	}
}
