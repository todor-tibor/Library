package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "loyalty_index")
	private int loyaltyIndex;

	private String password;

	@Column(name = "user_name")
	private String userName;

	// bi-directional many-to-one association to Borrow
	@OneToMany(mappedBy = "user")
	private List<Borrow> borrows;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "uuid"))
	private List<Role> roles;

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
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}