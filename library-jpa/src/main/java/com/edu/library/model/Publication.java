package com.edu.library.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent abstract class for the publications database table.
 *
 * @author sipost
 * @author kiska
 */
@Entity
@Table(name = "publications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
		@NamedQuery(name = "Publication.countSearch", query = "SELECT count(a) FROM Publication a WHERE a.title like :title"),
		@NamedQuery(name = "Publication.countAll", query = "SELECT count(a) FROM Publication a"),
		@NamedQuery(name = "Publication.findAll", query = "SELECT a FROM Publication a"),
		@NamedQuery(name = "Publication.searchByName", query = "SELECT a FROM Publication a WHERE a.title like :title"),
		@NamedQuery(name = "Publication.getByName", query = "SELECT a FROM Publication a WHERE a.title = :title"),
		@NamedQuery(name = "Publication.getById", query = "SELECT a FROM Publication a WHERE a.uuid = :uuid"),
		@NamedQuery(name = "Publication.findBorrow", query = "SELECT DISTINCT b FROM Borrow b, Publication p JOIN b.publication bPublication JOIN p.borrows pPublication WHERE bPublication.uuid = pPublication.publication.uuid AND p.title LIKE :title") })
public abstract class Publication extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * @param nrOfCopys
	 *            -Represents the number of a publication copies that can be in
	 *            the library
	 */
	@Column(name = "nr_of_copys")
	private int nrOfCopys;

	/**
	 * @param onStock
	 *            -Represents the available number of a publication copies in
	 *            the library
	 */
	@Column(name = "on_stock")
	private int onStock;

	/**
	 * @param publicationDate
	 *            -Represents the date when the publication was published
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "publication_date")
	private Date publicationDate;

	/**
	 * @param title
	 *            -Title of the publication
	 */
	private String title;

	/**
	 * @param borrows
	 *            - List of borrows for a given publications -bi-directional
	 *            many-to-one association to Borrow
	 */
	@OneToMany(mappedBy = "publication", fetch = FetchType.EAGER)
	private Set<Borrow> borrows;

	/**
	 * @param publisher
	 *            - The publisher of publications -bi-directional many-to-one
	 *            association to Publisher
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;

	public Publication() {
	}

	public int getNrOfCopys() {
		return this.nrOfCopys;
	}

	public void setNrOfCopys(final int nrOfCopys) {
		this.nrOfCopys = nrOfCopys;
	}

	public int getOnStock() {
		return this.onStock;
	}

	public void setOnStock(final int onStock) {
		this.onStock = onStock;
	}

	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public List<Borrow> getBorrows() {
		return new ArrayList<>(this.borrows);
	}

	public void setBorrows(final Set<Borrow> borrows) {
		this.borrows = borrows;
	}

	public Borrow addBorrow(final Borrow borrow) {
		getBorrows().add(borrow);
		borrow.setPublication(this);

		return borrow;
	}

	public Borrow removeBorrow(final Borrow borrow) {
		getBorrows().remove(borrow);
		borrow.setPublication(null);

		return borrow;
	}

	public Publisher getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final Publisher publisher) {
		this.publisher = publisher;
	}

}