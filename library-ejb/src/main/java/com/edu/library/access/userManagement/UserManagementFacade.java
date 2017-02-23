package com.edu.library.access.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IUserService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.userManagement.UserManagementBusiness;
import com.edu.library.model.User;

/**
 * Implements the basics of user login. Validates the given the input data and
 * calls the business layer if params are valid
 * 
 * @author kiska
 */
@Stateless
public class UserManagementFacade implements IUserService {
	@EJB
	private UserManagementBusiness userManagementBusiness;

	@Override
	public List<User> getAll() {
		return userManagementBusiness.getAll();
	}

	@Override
	public List<User> search(String searchUserName) {
		ServiceValidation.checkString(searchUserName);
		return userManagementBusiness.search(searchUserName);
	}

	@Override
	public User getById(String user_id) {
		ServiceValidation.checkUuid(user_id);
		return userManagementBusiness.getById(user_id);
	}

	@Override
	public void store(User user) {
		ServiceValidation.checkNotNull(user);
		ServiceValidation.checkNotEmpty(user.getRoles());
		ServiceValidation.checkString(user.getUserName());
		ServiceValidation.checkPassword(user.getPassword());
		userManagementBusiness.store(user);
	}

	@Override
	public void update(User user) {
		ServiceValidation.checkNotNull(user);
		ServiceValidation.checkNotEmpty(user.getRoles());
		ServiceValidation.checkString(user.getUserName());
		userManagementBusiness.update(user);
	}

	@Override
	public void remove(String user_id) {
		ServiceValidation.checkUuid(user_id);
		userManagementBusiness.remove(user_id);

	}

	@Override
	public User getByUserName(String userName) {
		ServiceValidation.checkString(userName);
		return userManagementBusiness.searchForUserName(userName);
	}

}
