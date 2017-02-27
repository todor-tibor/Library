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
		return this.userManagementBusiness.getAll();
	}

	@Override
	public List<User> search(final String searchUserName) {
		ServiceValidation.checkString(searchUserName);
		return this.userManagementBusiness.search(searchUserName);
	}

	@Override
	public User getById(final String userId) {
		ServiceValidation.checkUuid(userId);
		return this.userManagementBusiness.getById(userId);
	}

	@Override
	public void store(final User user) {
		ServiceValidation.checkNotNull(user);
		ServiceValidation.checkNotEmpty(user.getRoles());
		ServiceValidation.checkString(user.getUserName());
		ServiceValidation.checkPassword(user.getPassword());
		this.userManagementBusiness.store(user);
	}

	@Override
	public void update(final User user) {
		ServiceValidation.checkNotNull(user);
		ServiceValidation.checkNotEmpty(user.getRoles());
		ServiceValidation.checkString(user.getUserName());
		this.userManagementBusiness.update(user);
	}

	@Override
	public void remove(final String userId) {
		ServiceValidation.checkUuid(userId);
		this.userManagementBusiness.remove(userId);

	}

	@Override
	public User getByUserName(final String userName) {
		ServiceValidation.checkString(userName);
		return this.userManagementBusiness.searchForUserName(userName);
	}

}
