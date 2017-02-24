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

	private final Logger oLogger = Logger.getLogger(RoleMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IRoleService oRoleBean;

	@Inject
	private ExceptionHandler exceptionHandler;

	@Inject
	MessageService message;

	/**
	 * Currently displayed roles.
	 */
	private List<Role> roleList = new ArrayList<>();

	/**
	 * Currently selected role.
	 */
	private Role currentRole = null;

	/**
	 * Requests all role objects and stores them in roleList.
	 *
	 * @return List of all roles from database.
	 */
	public List<Role> getAll() {
		this.roleList.clear();
		try {
			this.roleList = this.oRoleBean.getAll();
		} catch (Exception e) {
			this.oLogger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.roleList;
	}

	/**
	 * Search for role by role name and stores them in roleList.
	 *
	 * @param searchTxt
	 *            rolename.
	 * @return List of role objects found.
	 */
	public List<Role> search(final String searchTxt) {
		if (searchTxt.length() >= 3) {
			this.roleList.clear();
			try {
				this.roleList = this.oRoleBean.search(searchTxt);
			} catch (Exception e) {
				this.oLogger.error(e.getMessage());
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.error("managedbean.string");
		}
		return this.roleList;
	}

	/**
	 * Stores new role with rolename.
	 *
	 * @param name
	 *            - rolename
	 */

	public void store(final String name) {
		if (name.isEmpty() || "".equals(name)) {
			this.message.error("managedbean.empty");
			return;
		}
		Role tmpRole = new Role();
		tmpRole.setRole(name);
		try {
			this.oRoleBean.store(tmpRole);
			this.roleList.add(tmpRole);
			this.message.info("managedBean.storeSuccess");
		} catch (Exception e) {
			this.oLogger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Renames currently selected role.
	 *
	 * @param newRole
	 *            - new rolename.
	 */
	public void update(final String newRole) {
		if ((this.currentRole != null) && (newRole.length() >= 3)) {
			this.currentRole.setRole(newRole);
			try {
				this.oRoleBean.update(this.currentRole);
				this.roleList = getAll();
				this.message.info("managedbean.updateSuccess");
			} catch (Exception e) {
				this.oLogger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.error("managedbean.string");
		}
	}

	/**
	 * Deletes currently selected role from persistency.
	 */
	public void remove() {
		if (this.currentRole == null) {
			this.message.error("managedbean.empty");
		} else {
			try {
				this.oRoleBean.remove(this.currentRole.getUuid());
				this.roleList.remove(this.currentRole);
				this.message.info("managedbean.deleteSuccess");
			} catch (Exception e) {
				this.oLogger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Check if is role selected
	 *
	 * @return true if current role not null
	 */
	public Boolean isSelected() {
		if (this.currentRole == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Getters and setters for private attributes
	 */

	public Role getCurrentRole() {
		return this.currentRole;
	}

	public void setCurrentRole(final Role currentRole) {
		this.currentRole = currentRole;
	}

	public List<Role> getRoleList() {
		return this.roleList;
	}

	public void setRoleList(final List<Role> roleList) {
		this.roleList = roleList;
	}

}
