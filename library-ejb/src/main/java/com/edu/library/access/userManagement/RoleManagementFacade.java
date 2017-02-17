package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IRoleService;
import com.edu.library.business.userManagement.RoleManagementBusiness;
import com.edu.library.model.Role;
import com.edu.library.util.ServiceValidation;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
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
		roleBusiness.store(p_value);
	}

	@Override
	public void update(Role p_user) {
		ServiceValidation.checkNotNull(p_user);
		roleBusiness.update(p_user);
	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		roleBusiness.remove(p_id);

	}

}
