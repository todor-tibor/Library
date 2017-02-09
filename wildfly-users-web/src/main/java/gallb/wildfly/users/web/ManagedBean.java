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
<<<<<<< HEAD
 * @author gallb 
 * 
 * User manager.
 * 
=======
 * @author gallb
 * User manager.
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
 */
@Named("userbean")

@SessionScoped
<<<<<<< HEAD
public class ManagedBean implements Serializable {
=======
public class ManagedBean implements Serializable{
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(ManagedBean.class);
	private static final long serialVersionUID = -4702598250751689454L;
<<<<<<< HEAD

=======
	
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
	@Inject
	private IUser oUserBean;
<<<<<<< HEAD

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
=======
	
	private List<User> userList =  new ArrayList<>();
	private User currentUser = null;
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git

<<<<<<< HEAD
	/**
	 * Requests all user objects and stores them in userList.
	 * @return List of all users from persistency.
	 */
=======
	//Setting faces context messages.
	public void error(String message) {
		oLogger.info("**********************Error CALLED***************************");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
    }
	
	public void info(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }
     
    public void warn(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
    }
     
    public void fatal(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
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
    
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
	public List<User> getAll() {
		oLogger.info("--getAllUsers()--");
		userList = new ArrayList<>();
		try {
			oLogger.info("--getAllUsers()--users queried");
			userList = oUserBean.getAll();
<<<<<<< HEAD
		} catch (LibraryException e) {
=======
		} catch (BeanException e) {
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
			this.error("Server internal error.");
		}
		return userList;
	}

<<<<<<< HEAD
	/**
	 * Search for user by username and stores them in userList.
	 * @param p_searchTxt username.
	 * @return List of user objects found.
	 */
=======
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
	public List<User> search(String p_searchTxt) {
		oLogger.info("--search user--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			userList = new ArrayList<>();
			try {
				userList = oUserBean.search(p_searchTxt);
<<<<<<< HEAD
			} catch (LibraryException e) {
=======
			} catch (BeanException e) {
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
				this.error(e.getMessage());
			}
		} else {
			this.error("Keyword too short. Min. 3 characters req.");
		}
		return userList;
	}
<<<<<<< HEAD

	/**
	 * Stores new user with username.
	 * @param p_value - username
	 */
=======
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
			oUserBean.store(tmpUser);
			userList.add(tmpUser);
			this.info("Succesfully added: " + p_value);
		} catch (BeanException e) {
			this.error(e.getMessage());
		}
	}
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
	
<<<<<<< HEAD
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
=======
	public void update(String p_newTxt) {
		oLogger.info("--update user ManagedBean--id:" + currentUser.getUserName() + "new name: " +p_newTxt);
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
		if ((currentUser != null) && (p_newTxt.length() >= 3)) {
			try {
				currentUser.setUserName(p_newTxt);
				oUserBean.update(currentUser);
<<<<<<< HEAD
				userList = oUserBean.getAll();
				oLogger.info("**********************update succesfull************************************");
				this.info("Update succesfull.");
			} catch (LibraryException e) {
=======
				userList = oUserBean.getAll(); 
				oLogger.info("**********************update succesfull************************************");
				this.info("Update succesfull.");
			} catch (BeanException e) {
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
				oLogger.error(e);
				this.error(e.getMessage());
			}
		} else {
			this.error("New name too short.");
		}
	}
<<<<<<< HEAD

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
=======
	

//	public boolean remove() {
//		oLogger.info("--remove user by Id ManagedBean--p_id: " + selectedUser);
//		if (selectedUser == "") {
//			this.error("Empty field");
//			return false;
//		} else {
//			try {
//				getUserBean().remove(selectedUser);
//				userList = getUserBean().getAll(); 
//				this.info("Delete succesfull.");
//			} catch (BeanException e) {
//				oLogger.error(e);
//				this.error(e.getMessage());
//			}
//		}
//		return true;
//	}
>>>>>>> branch 'DataTable' of https://github.com/todor-tibor/Library.git
}
