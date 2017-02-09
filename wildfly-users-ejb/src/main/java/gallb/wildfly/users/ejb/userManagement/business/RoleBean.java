package gallb.wildfly.users.ejb.userManagement.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.LibraryException;
import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.common.IRole;
import model.Role;



@Stateless
public class RoleBean implements IRole {
	
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(RoleBean.class);

	@Override
	public List<Role> getAll() throws EjbException {
		try {
			@SuppressWarnings("unchecked")
			List<Role> roles = (List<Role>) oEntityManager.createNamedQuery("Role.findAll").getResultList();
			return roles;
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbException.getCause(e);
			throw new EjbException(e.getMessage());
		}
	}

	@Override
	public Role getById(int id) throws EjbException {
		try {
			return oEntityManager.find(Role.class, id);
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbException.getCause(e);
			throw new EjbException(e.getMessage());
		}
	}

	@Override
	public void store(Role role) throws EjbException {
		try {
			int n = ((Number) oEntityManager.createNamedQuery("Role.maxId").getSingleResult()).intValue();
			role.setId(n + 1);
			oEntityManager.persist(role);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbException.getCause(e);
			throw new EjbException("Can't insert role <"+role.getRole()+">", e);
		}
	}

	@Override
	public void remove(int id) throws EjbException {
		try {
			Role r = oEntityManager.find(Role.class, id);
			oEntityManager.remove(r);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbException.getCause(e);
			throw new EjbException("Can't delete role", e);
		}

	}

	@Override
	public void update(Role role) throws EjbException {
		try {
			Role r = oEntityManager.find(Role.class, role.getId());
			if (r != null) {
				oEntityManager.merge(role);
				oEntityManager.flush();
			}
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbException.getCause(e);
			throw new EjbException("Can't update role", e);
		}
	}

	@Override
	public List<Role> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

}
