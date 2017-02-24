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
		return this.dataAcces.getAll();
	}

	public List<Role> search(final String searchText) {
		return this.dataAcces.search(searchText);
	}

	public Role getById(final String id) {
		return this.dataAcces.getById(id);
	}

	public void store(final Role role) {
		this.dataAcces.store(role);
	}

	public void update(final Role role) {
		this.dataAcces.update(role);
	}

	public void remove(final String id) {
		final Role r = this.dataAcces.getById(id);
		ServiceValidation.checkNotNull(r);
		this.dataAcces.remove(r);
	}

}
