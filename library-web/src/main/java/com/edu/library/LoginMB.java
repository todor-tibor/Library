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
	private ILoginService oLoginBean;
	
	@Inject
	ExceptionHandler exceptionHandler;
	/**
	 * 
	 */
	private String userName;
	private List<Role> roles;
	private String currentRole;


	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public String isAdmin() {
		if (RoleType.LIBRARIAN.name().equals(currentRole)) {
			return "";
		} else
			return "index";
	}
	
	public String isReader() {
		if (RoleType.READER.name().equals(currentRole)) {
			return "";
		} else
			return "index";
	}

	private void checkRole() {
		Role tmp = new Role();
		tmp.setRole("LIBRARIAN");
		if (roles.contains(tmp)) {
			setCurrentRole("LIBRARIAN");
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
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
					FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
				} catch (IOException e) {
					oLogger.error(e);
					MessageService.fatal(e.getMessage());
				}

			} else {
				setCurrentRole("INVALID");
				MessageService.error("login.invalid");
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
	public void search(String user_name, String password) {
		if (user_name.length() >= 3) {
			try {
				roles = oLoginBean.login(user_name, password);
				userName=user_name;
				checkRole();
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		}else{
			MessageService.warn("Username is to short");
		}
	}

	public String getUserName() {
		return userName;
	}

}