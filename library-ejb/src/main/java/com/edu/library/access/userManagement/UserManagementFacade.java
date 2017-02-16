package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.Stateless;

import gallb.wildfly.users.common.IUserService;
import gallb.wildfly.users.common.LibraryException;
import model.User;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class UserManagementFacade implements IUserService {

	@Override
	public List<User> getAll() throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(User p_value) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User p_user) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getByUserName(String userName) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
