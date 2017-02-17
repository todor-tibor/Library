package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.IRoleService;
import com.edu.library.LibraryException;

import edu.com.library.model.Role;

/**
 * Role manager.
 * 
 * @author kiska
 * @author sipost
 * 
 */
@Named("rolebean")

@SessionScoped
//@ViewScoped
public class RoleMB implements Serializable {

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(RoleMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IRoleService oRoleBean;

	@Inject LocaleManager localeManager;
	
	/**
	 * 
	 */
	
	@PostConstruct
	private void init(){
		FacesContext.getCurrentInstance().getViewRoot().setLocale(localeManager.getUserLocale());
		System.out.println("-*-*-*-*-*-*-* " + FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	}
	
	public void change(){
		oLogger.info("-----tab changed");
	}
	
	private List<Role> roleList = new ArrayList<>();// Currently displayed
													// roles.
	private Role currentRole = null;// Currently selected role.

	

	/**
	 * Requests all role objects and stores them in roleList.
	 * 
	 * @return List of all roles from persistency.
	 */
	public List<Role> getAll() {
		oLogger.info("--getAllRoles()--");
		roleList.clear();
		try {
			oLogger.info("--getAllRoles()--roles queried");
			roleList = oRoleBean.getAll();
		} catch (LibraryException e) {
			MessageService.error("Server internal error.");
		}
		return roleList;
	}

	/**
	 * Search for role by rolename and stores them in roleList.
	 * 
	 * @param p_searchTxt
	 *            rolename.
	 * @return List of role objects found.
	 */
	public List<Role> search(String p_searchTxt) {
		oLogger.info("--search role--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			roleList.clear();
			try {
				roleList = oRoleBean.search(p_searchTxt);
			} catch (LibraryException e) {
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
		return roleList;
	}

	/**
	 * Stores new role with rolename.
	 * 
	 * @param p_name - rolename, p_pass - password, p_idx - loyalty index
	 *         
	 */

	public void store(String p_name) {
		oLogger.info("--store role--");
		oLogger.info("--store param: " + p_name);
		if (p_name.isEmpty() || "".equals(p_name)) {
			MessageService.error("Empty field");
			return;
		}
		try {
			Role tmpRole = new Role();
			tmpRole.setRole(p_name);
			oRoleBean.store(tmpRole);
			roleList.add(tmpRole);
			MessageService.info("Succesfully added: " + p_name);
		} catch (LibraryException e) {
			MessageService.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected role.
	 * 
	 * @param p_newTxt
	 *            - new rolename.
	 */
	public void update(String p_newTxt) {
		if ((currentRole != null) && (p_newTxt.length() >= 3)) {
			try {
				currentRole.setRole(p_newTxt);
				oRoleBean.update(currentRole);
				roleList = oRoleBean.getAll();
				oLogger.info("---update succesfull---");
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
	 * Deletes currently selected role from persistency.
	 */
	public void remove() {
		if (currentRole == null) {
			oLogger.warn("No selected item");
			MessageService.error("No selected item");
		} else {
			try {
				oRoleBean.remove(currentRole.getUuid());
				roleList.remove(currentRole);
				MessageService.info("Delete successful.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		}
	}
	
	public List<Role> getroleList() {
		return roleList;
	}

	public Role getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(Role currentRole) {
		this.currentRole = currentRole;
	}
}
