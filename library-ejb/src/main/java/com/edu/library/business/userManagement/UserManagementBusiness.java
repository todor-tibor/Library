package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.LibraryException;
import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.User;

/**
 * @author kiska
 * 
 *         Business implementation of user management.
 */

@Stateless
@LocalBean
public class UserManagementBusiness {
	@EJB
	private UserDao dataAcces;

	public List<User> getAll() {
		return dataAcces.getAll();
	}

	public List<User> search(String userName) {
		return dataAcces.search(userName);
	}

	public User getById(String userId) {
		return dataAcces.getById(userId);
	}

	public void store(User user) {
		if (dataAcces.getByUserName(user.getUserName()).getUserName().isEmpty()) {
			dataAcces.store(user);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_CONSTRAINT_VIOLATION);
		}
	}

	public void update(User user) {
		dataAcces.update(user);
	}

	public void remove(String userId) {
		User pub = dataAcces.getById(userId);
		if (pub.getBorrows().isEmpty()) {
			dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

	public User searchForUserName(String userName) {
		return dataAcces.getByUserName(userName);
	}

}
