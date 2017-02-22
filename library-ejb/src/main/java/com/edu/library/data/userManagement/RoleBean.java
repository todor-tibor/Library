package com.edu.library.data.userManagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Role;

/**
 * CRUD operations of Role Entity
 * 
 * @author sipost
 * @author gallb
 *
 */
@Stateless
public class RoleBean {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private static Logger oLogger = Logger.getLogger(RoleBean.class);

	public List<Role> getAll() {
		try {
			return oEntityManager.createNamedQuery("Role.findAll", Role.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public Role getById(String id) {
		try {
			return oEntityManager.createNamedQuery("Role.findById", Role.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void store(Role role) {
		try {
			oEntityManager.persist(role);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void remove(Role role) {
		try {
			oEntityManager.remove(role);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}

	public void update(Role role) {
		try {
			oEntityManager.merge(role);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Role> search(String p_searchTxt) {
		try {
			return oEntityManager.createNamedQuery("Role.search", Role.class)
					.setParameter("role", "%" + p_searchTxt + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public Role getByName(String p_searchTxt) {
		try {
			return oEntityManager.createNamedQuery("Role.getByName", Role.class).setParameter("role", p_searchTxt)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

}
