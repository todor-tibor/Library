package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the borrows database table.
 * 
 */
@Entity
@Table(name="borrows")
@NamedQuery(name="Borrow.findAll", query="SELECT b FROM Borrow b")
public class Borrow extends BaseEntity {
	private static final long serialVersionUID = 1L;


	@Temporal(TemporalType.DATE)
	@Column(name="borrow_from")
	private Date borrowFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="borrow_until")
	private Date borrowUntil;

	//bi-directional many-to-one association to Publication
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="publication_id")
	private Publication publication;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	public Borrow() {
	}

	public Date getBorrowFrom() {
		return this.borrowFrom;
	}

	public void setBorrowFrom(Date borrowFrom) {
		this.borrowFrom = borrowFrom;
	}

	public Date getBorrowUntil() {
		return this.borrowUntil;
	}

	public void setBorrowUntil(Date borrowUntil) {
		this.borrowUntil = borrowUntil;
	}

	public Publication getPublication() {
		return this.publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}