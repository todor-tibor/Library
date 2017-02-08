package gallb.wildfly.users.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IUser;
import model.User;



@Stateless
public class UserBean implements IUser {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(UserBean.class);

	@Override
	public List<User> getAll() {
		try {
			@SuppressWarnings("unchecked")
			List<User> users = (List<User>) oEntityManager.createNamedQuery("User.findAll").getResultList();
			return users;
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find users", e);
		}
	}

	@Override
	public User getById(int id) {
		try {
			User u = oEntityManager.find(User.class, id);
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find user with specifiel id: <" + id + ">", e);
		}
	}

	@Override
	public void store(User user) {
		try {
			int n = ((Number) oEntityManager.createNamedQuery("User.maxId").getSingleResult()).intValue();
			user.setUuid(n + 1);
			oEntityManager.persist(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("--------Could not insert user.", e);
		}
	}

	@Override
	public void remove(int id) {
		try {
			oEntityManager.clear();
			User u = getById(id);
			oEntityManager.remove(u);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't delete user with specifield id: <" + id + ">", e);
		}
	}

	@Override
	public void update(User user) {
		try {
			oEntityManager.merge(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't update user with specifield id: <" + user.getUuid() + ">", e);

		}
	}

	@Override
	public List<User> search(String name) {
		try {
			CriteriaBuilder cb = oEntityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> member = criteria.from(User.class);

			criteria.select(member).where(cb.like(member.get("username"), "%" + name + "%"));
			return oEntityManager.createQuery(criteria).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find any user.", e);
		}
	}

//	@Override
//	public boolean login(String username, int role) {
//		oLogger.warn("--------------------------" + role);
//		try {
//			CriteriaBuilder cb = oEntityManager.getCriteriaBuilder();
//			CriteriaQuery<User> criteria = cb.createQuery(User.class);
//			Root<User> member = criteria.from(User.class);
//
//			criteria.select(member).where(cb.equal(member.get("username"), username));
//			User u = oEntityManager.createQuery(criteria).getSingleResult();
//
//			InitialContext jndi = new InitialContext();
//			IRole oRoleBean = (IRole) jndi.lookup(IRole.jndiNAME);
//
//			Role r = oRoleBean.getRoleById(role);
//			oLogger.warn("--------------------------" + r);
//			if (u.getRoles().contains(r)) {
//				oLogger.info("----------user loged in as " + r.getRole());
//				return true;
//			} else {
//				oLogger.info(username + " can't log in as " + r.getRole());
//			}
//		} catch (PersistenceException e) {
//			oLogger.error(e);
//			EjbExeption.getCause(e);
//			throw new EjbExeption("Invalid username, can't login.", e);
//		} catch (NamingException e) {
//			oLogger.error(e);
//			throw new EjbExeption("Invalid role, can't login", e);
//		}
//		return false;
//	}
}
