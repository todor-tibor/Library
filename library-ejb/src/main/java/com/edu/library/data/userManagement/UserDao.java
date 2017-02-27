package com.edu.library.data.userManagement;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Borrow;
import com.edu.library.model.User;
import com.edu.library.util.PasswordEncrypter;

/**
 * Invokes the CRUD methods for a user
 *
 * @author kiska
 */
@Stateless
@LocalBean
public class UserDao {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager entityManager;
	private final Logger logger = Logger.getLogger(UserDao.class);

	/**
	 * Returns all users in the database.
	 *
	 * @return - List of users
	 */
	public List<User> getAll() {
		try {
			return this.entityManager.createNamedQuery("User.findAll", User.class).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the user in the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a user
	 * @return - a user
	 */
	public User getById(final String id) {
		try {
			return this.entityManager.createNamedQuery("User.findById", User.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Save the user in the database by the given parameter {@code user}
	 *
	 * @param user
	 *            - a user type object containing all the necessary information
	 *            for saving a user to the DB.
	 */
	public void store(final User user) {
		try {
			user.setPassword(PasswordEncrypter.encypted(user.getPassword(), " "));
			this.entityManager.persist(user);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Remove the user from the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the user
	 */
	public void remove(final User user) {
		try {
			this.entityManager.remove(user);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Update the user in the database by the given parameter {@code user}
	 *
	 * @param user
	 *            - the user object on which the update will be done
	 */
	public void update(final User user) {
		try {
			this.entityManager.merge(user);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches for a user in the database by the given parameter
	 * {@code userName}
	 *
	 * @param userName
	 *            - the name or part of the name of the user to search for
	 * @return - List with all the users that match the search criteria
	 */
	public List<User> search(final String userName) {
		try {
			return this.entityManager.createNamedQuery("User.searchByUserName", User.class)
					.setParameter("user_name", "%" + userName + "%").getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Returns the user of {@code userName}
	 *
	 * @param userName
	 *            - the user name of the user for which to search for
	 * @return - a User object
	 * @throws TechnicalException
	 */
	public User getByUserName(final String userName) {

		try {
			final User u = this.entityManager.createNamedQuery("User.findByName", User.class)
					.setParameter("user_name", userName).getSingleResult();
			return u;
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Lists all borrowings from the database that a user has, given by the
	 * {@code userName}
	 *
	 * @param userName
	 *            - the user name of the publication
	 * @return - list of borrowings
	 */
	public List<Borrow> getBorrow(final String userName) {

		try {
			final List<Borrow> u = this.entityManager.createNamedQuery("User.findBorrow", Borrow.class)
					.setParameter("userName", "%" + userName + "%").getResultList();
			return u;
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}
}
