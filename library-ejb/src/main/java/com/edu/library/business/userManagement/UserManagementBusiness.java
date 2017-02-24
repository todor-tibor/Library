package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.User;

/**
 * Business implementation of user management. * (same functions as in the
 * IPublisherService interface)
 *
 * @author kiska
 */

@Stateless
@LocalBean
public class UserManagementBusiness {
	@EJB
	private UserDao dataAcces;

	public List<User> getAll() {
		return this.dataAcces.getAll();
	}

	public List<User> search(final String userName) {
		return this.dataAcces.search(userName);
	}

	public User getById(final String userId) {
		return this.dataAcces.getById(userId);
	}

	public void store(final User user) {
		this.dataAcces.store(user);
	}

	public void update(final User user) {
		this.dataAcces.update(user);
	}

	public void remove(final String userId) {
		final User pub = this.dataAcces.getById(userId);
		if (pub.getBorrows().isEmpty()) {
			this.dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

	public User searchForUserName(final String userName) {
		return this.dataAcces.getByUserName(userName);
	}
}
