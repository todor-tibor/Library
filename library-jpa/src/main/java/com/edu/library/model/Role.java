package com.edu.library.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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

@XmlRootElement(name = "Role")
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

	public void setRole(final String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.role == null) ? 0 : this.role.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		Role other = (Role) obj;
		if (this.role == null) {
			if (other.role != null)
				return false;
		} else if (!this.role.equals(other.role))
			return false;
		return true;
	}

}