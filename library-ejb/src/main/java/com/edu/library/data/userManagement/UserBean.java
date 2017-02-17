package com.edu.library.data.userManagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.LibraryException;
import com.edu.library.PasswordEncrypter;
import com.edu.library.util.EjbException;

import edu.com.library.model.User;

/**
 * @author kiska
 *
 *         Invokes the CRUD methods for a user
 */
@Stateless
public class UserBean {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(UserBean.class);

	public List<User> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("User.findAll", User.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public User getById(String id) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("User.findById", User.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void store(User user) throws LibraryException {
		try {
			user.setPassword(PasswordEncrypter.encypted(user.getPassword(), " "));
			oEntityManager.persist(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void remove(String id) throws EjbException {
		try {
			oEntityManager.clear();
			User u = getById(id);
			oEntityManager.remove(u);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void update(User user) throws EjbException {
		try {
			oEntityManager.merge(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public List<User> search(String userName) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("User.searchByUserName", User.class)
					.setParameter("user_name", "%" + userName + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	/**
	 * Returns the user of {@code userName}
	 * 
	 * @param userName
	 *            - the user name of the user for which to search for
	 * @return - a User object
	 * @throws EjbException
	 */
	public User getByUserName(String userName) throws EjbException {

		try {
			User u = oEntityManager.createNamedQuery("User.findByName", User.class).setParameter("user_name", userName)
					.getSingleResult();
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}
}
