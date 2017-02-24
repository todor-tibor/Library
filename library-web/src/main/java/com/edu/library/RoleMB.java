package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Role;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 * Role manager.
 * 
 * @author kiska
 * @author sipost
 * 
 */
@Named("rolebean")

@SessionScoped
public class RoleMB implements Serializable {

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(RoleMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IRoleService oRoleBean;

	@Inject
	private ExceptionHandler exceptionHandler;
	
	@Inject
	MessageService message;
		
	
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
		roleList.clear();
		try {		
			roleList = oRoleBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
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
		if (p_searchTxt.length() >= 3) {
			roleList.clear();
			try {
				roleList = oRoleBean.search(p_searchTxt);
			} catch (Exception e) {
				oLogger.error(e.getMessage());
				exceptionHandler.showMessage(e);
			}
		} else {
			message.error("managedbean.string");
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
		if (p_name.isEmpty() || "".equals(p_name)) {
			message.error("managedbean.empty");
			return;
		}
		try {
			Role tmpRole = new Role();
			tmpRole.setRole(p_name);
			oRoleBean.store(tmpRole);
			roleList.add(tmpRole);
			message.info("managedBean.storeSuccess");
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
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
				roleList = getAll();			
				message.info("managedbean.updateSuccess");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			message.error("managedbean.string");
		}
	}

	/**
	 * Deletes currently selected role from persistency.
	 */
	public void remove() {
		if (currentRole == null) {		
			message.error("managedbean.empty");
		} else {
			try {
				oRoleBean.remove(currentRole.getUuid());
				roleList.remove(currentRole);
				message.info("managedbean.deleteSuccess");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		}
	}
	
	

	public Role getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(Role currentRole) {
		this.currentRole = currentRole;
	}

	public Boolean isSelected() {
		if (this.currentRole == null) {
			return false;
		} else {
			return true;
		}
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
}
