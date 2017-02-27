package com.edu.library.business.userManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.access.util.ServiceValidation;
import com.edu.library.data.userManagement.RoleBean;
import com.edu.library.model.Role;

/**
 * Implements basic business logic for publisher management. (same functions as
 * in the IRoleService interface)
 *
 * @author kiska
 * @author sipost
 */
@Stateless
@LocalBean
public class RoleManagementBusiness {

	@EJB
	private RoleBean dataAcces;

	/**
	 * Returns all roles.
	 *
	 * @return - List of roles
	 */
	public List<Role> getAll() {
		return this.dataAcces.getAll();
	}

	/**
	 * Searches for a role given by {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the role to search for
	 * @return - List with all the authors that match the search criteria
	 */
	public List<Role> search(final String searchText) {
		return this.dataAcces.search(searchText);
	}

	/**
	 * Return the role given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a role
	 * @return - a role
	 */
	public Role getById(final String id) {
		return this.dataAcces.getById(id);
	}

	/**
	 * Save the role given by {@code role}
	 *
	 * @param role
	 *            - a Role type object containing all the necessary information
	 *            for saving a role to the DB.
	 */
	public void store(final Role role) {
		this.dataAcces.store(role);
	}

	/**
	 * Update the role given in {@code role}
	 *
	 * @param role
	 *            - the role object on which the update will be done
	 */
	public void update(final Role role) {
		this.dataAcces.update(role);
	}

	/**
	 * Remove the role given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the role
	 */
	public void remove(final String id) {
		final Role r = this.dataAcces.getById(id);
		ServiceValidation.checkNotNull(r);
		this.dataAcces.remove(r);
	}

}
