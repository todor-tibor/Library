package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IUser;
import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.util.PasswordEncrypter;
import model.User;

/**
 * @author kiska
 *
 */
@Stateless
public class UserBean implements IUser {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(UserBean.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("User.findAll").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public User getById(String id) throws EjbException {
		try {
			return oEntityManager.find(User.class, id);
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public void store(User user) throws EjbException {
		try {
			user.setPassword(PasswordEncrypter.encypted(user.getPassword(), " "));
			oEntityManager.persist(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
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

	@Override
	public void update(User user) throws EjbException {
		try {
			oEntityManager.merge(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public List<User> search(String name) throws EjbException {
		try {
			CriteriaBuilder cb = oEntityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> member = criteria.from(User.class);

			criteria.select(member).where(cb.like(member.get("username"), "%" + name + "%"));
			return oEntityManager.createQuery(criteria).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	/**
	 * Returns the user of {@param userName}
	 * 
	 * @param userName
	 *            - the user name of the user for which to search
	 * @return - a User object
	 */
	public User getByName(String userName) {
		return (User) oEntityManager.createNamedQuery("User.findByName").setParameter("user_name", userName)
				.getSingleResult();
	}
}
