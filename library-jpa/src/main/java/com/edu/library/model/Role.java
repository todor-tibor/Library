package com.edu.library.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the roles database table.
 * 
 * @author sipost
 * @author kiska
 */
@Entity
@Table(name = "roles")
@NamedQueries({ @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
		@NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.uuid= :uuid"),
		@NamedQuery(name = "Role.getByName", query = "SELECT r FROM Role r WHERE r.role = :role"),
		@NamedQuery(name = "Role.search", query = "SELECT  r FROM Role r WHERE r.role like :role") })
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * @param role
	 *            - The name of the role
	 */
	private String role;

	public Role() {
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Role other = (Role) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

}