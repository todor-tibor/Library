package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.userManagement.RoleBean;
import com.edu.library.model.Role;
import com.edu.library.util.ServiceValidation;

@Stateless
@LocalBean
public class RoleManagementBusiness {

	@EJB
	private RoleBean dataAcces;

	public List<Role> getAll() {
		return dataAcces.getAll();
	}

	public List<Role> search(String p_searchTxt) {
		ServiceValidation.checkString(p_searchTxt);
		return dataAcces.search(p_searchTxt);
	}

	public Role getById(String p_id) {
		ServiceValidation.checkUuid(p_id);
		return dataAcces.getById(p_id);
	}

	public void store(Role p_value) {
		ServiceValidation.checkNotNull(p_value);
		Role r = dataAcces.getByName(p_value.getRole());
		if (r == null) {
			dataAcces.store(p_value);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_CONSTRAINT_VIOLATION);
		}
	}

	public void update(Role p_user) {
		ServiceValidation.checkNotNull(p_user);
		dataAcces.update(p_user);
	}

	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		Role r = dataAcces.getById(p_id);
		ServiceValidation.checkNotNull(r);
		dataAcces.remove(r);
	}

}
