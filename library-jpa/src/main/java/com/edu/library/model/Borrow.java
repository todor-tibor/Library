package com.edu.library.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the borrows database table.
 *
 */
@XmlRootElement(name = "Borrow")
@Entity
@Table(name = "borrows")
@NamedQueries({ @NamedQuery(name = "Borrow.findAll", query = "SELECT b FROM Borrow b"),
		@NamedQuery(name = "Borrow.findById", query = "SELECT b FROM Borrow b WHERE b.uuid = :uuid"),
		@NamedQuery(name = "Borrow.findByUntilDate", query = "SELECT b FROM Borrow b WHERE b.borrowUntil < :p_date") })
public class Borrow extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "borrow_from")
	private Date borrowFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "borrow_until")
	private Date borrowUntil;

	// bi-directional many-to-one association to Publication
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "publication_id")
	private Publication publication;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Borrow() {
	}

	public Date getBorrowFrom() {
		return this.borrowFrom;
	}

	public void setBorrowFrom(final Date borrowFrom) {
		this.borrowFrom = borrowFrom;
	}

	public Date getBorrowUntil() {
		return this.borrowUntil;
	}

	public void setBorrowUntil(final Date borrowUntil) {
		this.borrowUntil = borrowUntil;
	}

	public Publication getPublication() {
		return this.publication;
	}

	public void setPublication(final Publication publication) {
		this.publication = publication;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}