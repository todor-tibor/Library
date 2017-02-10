/**
 * 
 */
package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.common.LibraryException;
import model.User;

/**
 * @author gallb 
 * 
 * User manager.
 * 
 */
@Named("userbean")

@SessionScoped
public class ManagedBean implements Serializable {

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(ManagedBean.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IUser oUserBean;

	/**
	 * 
	 */
	private List<User> userList = new ArrayList<>();// Currently displayed users.
	private User currentUser = null;// Currently selected user.

	/**
	 * Sets faces context error message.
	 * @param message
	 */
	public void error(String message) {
		oLogger.info("**********************Error CALLED***************************");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}


	/**
	 * Sets faces context info message.
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
	}


	/**
	 * Sets faces context warning message.
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
	}


	/**
	 * Sets faces context fatal error message.
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
	}

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
	 * @return List of all users from persistency.
	 */
	public List<User> getAll() {
		oLogger.info("--getAllUsers()--");
		userList = new ArrayList<>();
		try {
			oLogger.info("--getAllUsers()--users queried");
			userList = oUserBean.getAll();
		} catch (LibraryException e) {
			this.error("Server internal error.");
		}
		return userList;
	}

	/**
	 * Search for user by username and stores them in userList.
	 * @param p_searchTxt username.
	 * @return List of user objects found.
	 */
	public List<User> search(String p_searchTxt) {
		oLogger.info("--search user--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			userList = new ArrayList<>();
			try {
				userList = oUserBean.search(p_searchTxt);
			} catch (LibraryException e) {
				this.error(e.getMessage());
			}
		} else {
			this.error("Keyword too short. Min. 3 characters req.");
		}
		return userList;
	}

	/**
	 * Stores new user with username.
	 * @param p_value - username
	 */
	
	public void store(String p_value) {
		oLogger.info("--store user--");
		oLogger.info("--store param: " + p_value);
		if (p_value.isEmpty()) {
			this.error("Empty field");
		}
		if (p_value == "") {
			this.error("Empty field");
		}
		try {
			User tmpUser = new User();
			tmpUser.setUserName(p_value);
			tmpUser.setLoyaltyIndex(10);
			tmpUser.setPassword("password");
			oUserBean.store(tmpUser);
			userList.add(tmpUser);
			this.info("Succesfully added: " + p_value);
		} catch (LibraryException e) {
			this.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected user.
	 * @param p_newTxt - new username.
	 */
	public void update(String p_newTxt) {
		oLogger.info("--update user ManagedBean--id:" + currentUser.getUserName() + "new name: " + p_newTxt);
		if ((currentUser != null) && (p_newTxt.length() >= 3)) {
			try {
				currentUser.setUserName(p_newTxt);
				oUserBean.update(currentUser);
				userList = oUserBean.getAll();
				oLogger.info("**********************update succesfull************************************");
				this.info("Update succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				this.error(e.getMessage());
			}
		} else {
			this.error("New name too short.");
		}
	}

	/**
	 * Deletes currently selected user from persistency.
	 */
	public void remove() {
		oLogger.info("--remove user by Id ManagedBean--p_id: " + currentUser.getUserName());
		if (currentUser == null) {
			this.error("Empty field");
		} else {
			try {
				oUserBean.remove(currentUser.getUuid());
				userList = oUserBean.getAll();
				this.info("Delete succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				this.error(e.getMessage());
			}
		}
	}
}
