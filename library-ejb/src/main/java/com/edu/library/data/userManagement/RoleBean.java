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
	private EntityManager entityManager;
	private static Logger logger = Logger.getLogger(RoleBean.class);

	/**
	 * Returns all roles in the database.
	 *
	 * @return - List of roles
	 */
	public List<Role> getAll() {
		try {
			return this.entityManager.createNamedQuery("Role.findAll", Role.class).getResultList();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the role in the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a role
	 * @return - a role
	 */
	public Role getById(final String id) {
		try {
			return this.entityManager.createNamedQuery("Role.findById", Role.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Save the role in the database by the given parameter {@code role}
	 *
	 * @param role
	 *            - a role type object containing all the necessary information
	 *            for saving a role to the DB.
	 */
	public void store(final Role role) {
		try {
			this.entityManager.persist(role);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Remove the role from the database by the given parameter {@code id}
	 *
	 * @param role
	 *            - the role object to delete
	 */
	public void remove(final Role role) {
		try {
			this.entityManager.remove(role);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Update the role in the database by the given parameter {@code role}
	 *
	 * @param role
	 *            - the role object on which the update will be done
	 */
	public void update(final Role role) {
		try {
			this.entityManager.merge(role);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches for a role in the database by the given parameter
	 * {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the role to search for
	 * @return - List with all the roles that match the search criteria
	 */
	public List<Role> search(final String searchText) {
		try {
			return this.entityManager.createNamedQuery("Role.search", Role.class)
					.setParameter("role", "%" + searchText + "%").getResultList();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the role in the database by the given parameter {@code role}
	 *
	 * @param role
	 *            - the unique name of a role
	 * @return - a role
	 */
	public Role getByName(final String role) {
		try {
			return this.entityManager.createNamedQuery("Role.getByName", Role.class).setParameter("role", role)
					.getSingleResult();
		} catch (final PersistenceException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

}
