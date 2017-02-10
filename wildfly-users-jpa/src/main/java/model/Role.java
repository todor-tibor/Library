package model;

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
@NamedQueries({
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
@NamedQuery(name="Role.findById", query="SELECT r FROM Role r WHERE r.uuid= :uuid")})
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String role;

	public Role() {
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}