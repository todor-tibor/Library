package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IRoleService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.userManagement.RoleManagementBusiness;
import com.edu.library.model.Role;

/**
 * Implements the basics of role management. Validates the given input data and
 * calls the business layer if params are valid
 *
 * @author sipost
 */
@Stateless
public class RoleManagementFacade implements IRoleService {

	@EJB
	RoleManagementBusiness roleBusiness;

	@Override
	public List<Role> getAll() {
		return this.roleBusiness.getAll();
	}

	@Override
	public List<Role> search(final String searchText) {
		ServiceValidation.checkString(searchText);
		return this.roleBusiness.search(searchText);
	}

	@Override
	public Role getById(final String id) {
		ServiceValidation.checkUuid(id);
		return this.roleBusiness.getById(id);
	}

	@Override
	public void store(final Role role) {
		ServiceValidation.checkNotNull(role);
		ServiceValidation.checkString(role.getRole());
		this.roleBusiness.store(role);
	}

	@Override
	public void update(final Role role) {
		ServiceValidation.checkNotNull(role);
		ServiceValidation.checkString(role.getRole());
		this.roleBusiness.update(role);
	}

	@Override
	public void remove(final String id) {
		ServiceValidation.checkUuid(id);
		this.roleBusiness.remove(id);
	}

}
