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
		return roleBusiness.getAll();
	}

	@Override
	public List<Role> search(String p_searchTxt) {
		ServiceValidation.checkString(p_searchTxt);
		return roleBusiness.search(p_searchTxt);
	}

	@Override
	public Role getById(String p_id) {
		ServiceValidation.checkUuid(p_id);
		return roleBusiness.getById(p_id);
	}

	@Override
	public void store(Role p_value) {
		ServiceValidation.checkNotNull(p_value);
		ServiceValidation.checkString(p_value.getRole());
		roleBusiness.store(p_value);
	}

	@Override
	public void update(Role p_user) {
		ServiceValidation.checkNotNull(p_user);
		ServiceValidation.checkString(p_user.getRole());
		roleBusiness.update(p_user);
	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		roleBusiness.remove(p_id);

	}

}
