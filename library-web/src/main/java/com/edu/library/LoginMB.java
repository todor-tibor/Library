package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Cache;

import org.jboss.logging.Logger;

import com.edu.library.ILoginService;
import com.edu.library.IUserService;
import com.edu.library.LibraryException;
import com.edu.library.model.Role;
import com.edu.library.model.RoleType;
import com.edu.library.model.User;

/**
 * @author kiska
 * 
 *         Login
 * 
 */
@Named("loginbean")
@SessionScoped
public class LoginMB implements Serializable {

	private Logger oLogger = Logger.getLogger(LoginMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IUserService oUserBean;
	@Inject
	private ILoginService oLoginBean;
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

	private String checkRole() {
		Role tmp = new Role();
		tmp.setRole("LIBRARIAN");

		if (roles.contains(tmp)) {
			setCurrentRole("LIBRARIAN");
			return "index";
		} else {
			tmp.setRole("READER");

			if (roles.contains(tmp)) {
				setCurrentRole("READER");
				return "publication_user";
			} else {
				setCurrentRole("INVALID");
				return "login";
			}
		}
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
	public String search() {
		oLogger.info("--search user--" + this.getUser_name());
		if (this.getUser_name().length() >= 3) {
			try {
				System.out.println("/*/*-/*-/ " + this.getUser_name() + "    " + this.getPassword());
				roles = oLoginBean.login(this.getUser_name(), this.getPassword());
				System.out.println("///**********-----------    success    -*-*-*-*-*-");
				return checkRole();
			} catch (LibraryException e) {
				oLogger.error(e.getMessage());
				MessageService.error(e.getMessage());

			}
		}

		return "";
		/*
		 * for (Role r : roles) { switch (r.getRole()) { case "LIBRARIAN":
		 * return "index"; case "READER": return "publication_user"; default:
		 * return "login"; } }
		 */

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