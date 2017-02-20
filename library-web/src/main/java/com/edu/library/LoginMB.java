package com.edu.library;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Role;
import com.edu.library.model.RoleType;
import com.edu.library.model.User;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.PropertyProvider;

/**
 * @author kiska
 * 
 *         Login
 * 
 */
@Named("loginbean")
@ApplicationScoped
public class LoginMB implements Serializable {

	private Logger oLogger = Logger.getLogger(LoginMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IUserService oUserBean;
	@Inject
	private ILoginService oLoginBean;
	@Inject
	private ExceptionHandler exceptionHandler;
	/**
	 * 
	 */
	private List<User> userList = new ArrayList<>();// Currently displayed
													// users.
	private User currentUser = null;// Currently selected user.
	private String password;
	private String user_name;
	private List<Role> roles;
	private String currentRole;

	public List<User> getUserList() {
		return userList;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public String isAdmin() {
		if (RoleType.LIBRARIAN.name().equals(currentRole)) {
			return "index";
		} else
			return "";
	}

	private void checkRole() {
		Role tmp = new Role();
		tmp.setRole("LIBRARIAN");

		if (roles.contains(tmp)) {
			setCurrentRole("LIBRARIAN");

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
				FacesContext.getCurrentInstance().getViewRoot()
						.setLocale(FacesContext.getCurrentInstance().getViewRoot().getLocale());
			} catch (IOException e) {
				oLogger.error(e);
				MessageService.fatal(e.getMessage());
			}
		} else {
			tmp.setRole("READER");

			if (roles.contains(tmp)) {
				setCurrentRole("READER");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("publication_user.xhtml");
				} catch (IOException e) {
					oLogger.error(e);
					MessageService.fatal(e.getMessage());
				}

			} else {
				setCurrentRole("INVALID");
			}
		}
	}

	public String processAdmin() {
		return "index?facesRedirect=true";
	}

	public String processReader() {
		return "publication_user?facesRedirect=true";
	}

	/**
	 * Requests all user objects and stores them in userList.
	 * 
	 * @return List of all users from persistency.
	 */
	public List<User> getAll() {
		userList = new ArrayList<>();
		try {
			userList = oUserBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
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
	public void search() {
		if (this.getUser_name().length() >= 3) {
			try {
				roles = oLoginBean.login(this.getUser_name(), this.getPassword());
				checkRole();
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		}
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