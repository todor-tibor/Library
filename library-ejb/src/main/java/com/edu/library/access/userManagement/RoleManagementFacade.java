package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.Stateless;

import gallb.wildfly.users.common.IRoleService;
import gallb.wildfly.users.common.LibraryException;
import model.Role;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class RoleManagementFacade implements IRoleService {

	@Override
	public List<Role> getAll() throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Role p_value) throws LibraryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Role p_user) throws LibraryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub

	}

}
