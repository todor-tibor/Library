package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.access.util.ServiceValidation;
import com.edu.library.data.userManagement.RoleBean;
import com.edu.library.model.Role;

/**
 * Implements basic business logic for publisher management. (same functions as
 * in the IRoleService interface)
 * 
 * @author kiska
 * @author sipost
 */
@Stateless
@LocalBean
public class RoleManagementBusiness {

	@EJB
	private RoleBean dataAcces;

	public List<Role> getAll() {
		return dataAcces.getAll();
	}

	public List<Role> search(String p_searchTxt) {
		return dataAcces.search(p_searchTxt);
	}

	public Role getById(String p_id) {
		return dataAcces.getById(p_id);
	}

	public void store(Role p_value) {
		dataAcces.store(p_value);
	}

	public void update(Role p_user) {
		dataAcces.update(p_user);
	}

	public void remove(String p_id) {
		Role r = dataAcces.getById(p_id);
		ServiceValidation.checkNotNull(r);
		dataAcces.remove(r);
	}

}
