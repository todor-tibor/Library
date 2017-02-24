package com.edu.library;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Role;
import com.edu.library.model.RoleType;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 * Login functionalities for the web app.
 *
 * @author kiska
 */
@Named("loginbean")
@ApplicationScoped
public class LoginMB implements Serializable {

	private final Logger oLogger = Logger.getLogger(LoginMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private ILoginService oLoginBean;

	@Inject
	ExceptionHandler exceptionHandler;

	@Inject
	MessageService message;

	/**
	 * User name
	 */
	private String userName;

	/**
	 * Currently logged in user roles
	 */
	private List<Role> roles;

	/**
	 * Checks whether the user is a librarian.
	 *
	 * @return - the site to which to redirect.
	 */
	public String isAdmin() {
		Role tmp = new Role();
		tmp.setRole(RoleType.LIBRARIAN.toString());
		if (this.roles != null && this.roles.contains(tmp)) {
			return "";
		} else
			return "index";
	}

	/**
	 * Checks whether the user is a reader.
	 *
	 * @return - the site to which to redirect.
	 */
	public String isReader() {
		Role tmp = new Role();
		tmp.setRole(RoleType.READER.toString());
		if (this.roles != null && this.roles.contains(tmp)) {
			return "";
		} else
			return "index";
	}

	/**
	 * Checks whether the librarian or reader roles are listed in the user's
	 * roles. If they are it redirects to role specific website. Otherwise
	 * displays a user friendly message.
	 */
	private void checkRole() {
		Role tmp = new Role();
		tmp.setRole("LIBRARIAN");
		if (this.roles.contains(tmp)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
				FacesContext.getCurrentInstance().getViewRoot()
						.setLocale(FacesContext.getCurrentInstance().getViewRoot().getLocale());
			} catch (IOException e) {
				this.oLogger.error(e);
				this.message.fatal(e.getMessage());
			}
		} else {
			tmp.setRole("READER");
			if (this.roles.contains(tmp)) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("reader.xhtml");
				} catch (IOException e) {
					this.oLogger.error(e);
					this.message.fatal(e.getMessage());
				}
			} else {
				this.message.error("login.invalid");
			}
		}
	}

	/**
	 * Search for user by username and stores them in userList.
	 *
	 * @param p_searchTxt
	 *            username.
	 * @return List of user objects found.
	 */
	public void search(final String user_name, final String password) {
		if (user_name.length() >= 3) {
			try {
				this.roles = this.oLoginBean.login(user_name, password);
				this.userName = user_name;
				checkRole();
			} catch (Exception e) {
				this.oLogger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.warn("managedbean.string");
		}
	}

	public String getUserName() {
		return this.userName;
	}

	/**
	 * Implements a simple logout process, by setting the user name to empty
	 * string. Redirects to the application's start page.
	 */
	public void logout() {
		this.userName = "";
		this.roles.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			this.oLogger.error(e);
			this.message.fatal(e.getMessage());
		}
	}

	/**
	 * Checks whether the user is logged in. Returns false if it is not.
	 *
	 * @return true if user is logged in
	 */
	public boolean isLoggedIn() {
		if (this.userName == null || this.userName.isEmpty() || this.roles.isEmpty()) {
			return false;
		}
		return true;
	}

}