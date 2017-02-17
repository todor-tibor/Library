package com.edu.library.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the users database table.
 * 
 * @author sipost
 * @author kiska
 */
@Entity
@Table(name = "users")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.userName = :user_name"),
		@NamedQuery(name = "User.searchByUserName", query = "SELECT u FROM User u where u.userName like :user_name"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.uuid= :uuid") })

public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "loyalty_index")
	/**
	 * @param loyaltyIndex
	 *            - The loyalty index of a user used to check eligibility for
	 *            book borrowing. It's maximum value is 10, minimum 0.
	 */
	private int loyaltyIndex;

	/**
	 * @param password
	 *            - The password of the user
	 */
	private String password;

	@Column(name = "user_name")
	/**
	 * @param userName
	 *            - The user name of the user
	 */
	private String userName;

	// bi-directional many-to-one association to Borrow
	@OneToMany(mappedBy = "user")
	/**
	 * @param borrows
	 *            - List of already borrowed publications for a given user
	 */
	private List<Borrow> borrows;

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "uuid"))
	/**
	 * @param roles
	 *            - The roles the user has
	 */
	private Set<Role> roles;

	public User() {
	}

	public int getLoyaltyIndex() {
		return this.loyaltyIndex;
	}

	public void setLoyaltyIndex(int loyaltyIndex) {
		this.loyaltyIndex = loyaltyIndex;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Borrow> getBorrows() {
		return this.borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

	public Borrow addBorrow(Borrow borrow) {
		getBorrows().add(borrow);
		borrow.setUser(this);

		return borrow;
	}

	public Borrow removeBorrow(Borrow borrow) {
		getBorrows().remove(borrow);
		borrow.setUser(null);

		return borrow;
	}

	public List<Role> getRoles() {
		return new ArrayList<Role>(this.roles);
	}

	public void setRoles(List<Role> roles) {
		this.roles = new HashSet<Role>(roles);
	}

}