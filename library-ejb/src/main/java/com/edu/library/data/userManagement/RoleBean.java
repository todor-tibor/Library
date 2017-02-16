package com.edu.library.data.userManagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.util.EjbException;

import gallb.wildfly.users.common.IRoleService;
import model.Role;

@Stateless
public class RoleBean {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private static Logger oLogger = Logger.getLogger(RoleBean.class);

	public List<Role> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Role.findAll",Role.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public Role getById(String id) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Role.findById", Role.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public void store(Role role) throws EjbException {
		try {
			oEntityManager.persist(role);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public void remove(String id) throws EjbException {
		try {
			Role r = oEntityManager.find(Role.class, id);
			oEntityManager.remove(r);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	
	public void update(Role role) throws EjbException {
		try {
			Role r = oEntityManager.find(Role.class, role.getUuid());
			if (r != null) {
				oEntityManager.merge(role);
				oEntityManager.flush();
			}
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public List<Role> search(String p_searchTxt) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Role.search", Role.class).setParameter("role", p_searchTxt)
					.getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

}
