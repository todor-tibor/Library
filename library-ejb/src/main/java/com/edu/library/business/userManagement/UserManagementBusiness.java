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

	/**
	 * Returns all users.
	 *
	 * @return - List of users
	 */
	public List<User> getAll() {
		return this.dataAcces.getAll();
	}

	/**
	 * Searches for a user given by {@code userName}
	 *
	 * @param userName
	 *            - the name or part of the name of the user to search for
	 * @return - List with all the users that match the search criteria
	 */
	public List<User> search(final String userName) {
		return this.dataAcces.search(userName);
	}

	/**
	 * Return the user given by {@code userId}
	 *
	 * @param userId
	 *            - the unique identifier of a user
	 * @return - an author
	 */
	public User getById(final String userId) {
		return this.dataAcces.getById(userId);
	}

	/**
	 * Save the user given by {@code user}
	 *
	 * @param user
	 *            - a User type object containing all the necessary information
	 *            for saving a user to the DB.
	 */
	public void store(final User user) {
		this.dataAcces.store(user);
	}

	/**
	 * Update the user given in {@code user}
	 *
	 * @param user
	 *            - the user object on which the update will be done
	 */
	public void update(final User user) {
		this.dataAcces.update(user);
	}

	/**
	 * Remove the user given by {@code userId}. Throws an exception if the user
	 * has no borrowings.
	 *
	 * @param userId
	 *            - the unique identifier of the user
	 */
	public void remove(final String userId) {
		final User pub = this.dataAcces.getById(userId);
		if (pub.getBorrows().isEmpty()) {
			this.dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

	/**
	 * Search for user by an exact name.
	 * 
	 * @param userName
	 *            - the exact user name to search for
	 * @return - the found user
	 */
	public User searchForUserName(final String userName) {
		return this.dataAcces.getByUserName(userName);
	}
}
