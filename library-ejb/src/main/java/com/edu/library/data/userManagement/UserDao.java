package com.edu.library.data.userManagement;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.PasswordEncrypter;
import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Borrow;
import com.edu.library.model.User;

/**
 * Invokes the CRUD methods for a user
 * 
 * @author kiska 
 */
@Stateless
@LocalBean
public class UserDao {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(UserDao.class);

	public List<User> getAll() {
		try {
			return oEntityManager.createNamedQuery("User.findAll", User.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public User getById(String id) {
		try {
			return oEntityManager.createNamedQuery("User.findById", User.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void store(User user) {
		try {
			user.setPassword(PasswordEncrypter.encypted(user.getPassword(), " "));
			oEntityManager.persist(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void remove(User user) {
		try {
			oEntityManager.remove(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void update(User user) {
		try {
			oEntityManager.merge(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<User> search(String userName) {
		try {
			return oEntityManager.createNamedQuery("User.searchByUserName", User.class)
					.setParameter("user_name", "%" + userName + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
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
	public User getByUserName(String userName) {

		try {
			User u = oEntityManager.createNamedQuery("User.findByName", User.class).setParameter("user_name", userName)
					.getSingleResult();
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}
	
	public List<Borrow> getBorrow(String userName){

		try {
			List<Borrow> u = oEntityManager.createNamedQuery("User.findBorrow", Borrow.class).setParameter("userName", userName)
					.getResultList();
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}
}
