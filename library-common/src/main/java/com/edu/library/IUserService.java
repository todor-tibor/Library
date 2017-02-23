package com.edu.library;

import com.edu.library.model.User;

/**
 * Defines persistence operations, for User entities.
 * 
 * @author kiska
 */
public interface IUserService extends IService<User> {

	/**
	 * Selects the user with specified name
	 * 
	 * @param userName
	 *            -String name of the selected user
	 * @return User if exists
	 */
	public User getByUserName(String userName);
}
