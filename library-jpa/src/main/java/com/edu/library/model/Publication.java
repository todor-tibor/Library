package com.edu.library.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent abstract class for the publications database table.
 *
 * @author sipost
 * @author kiska
 */

@XmlRootElement(name = "Publication")
@Entity
@Table(name = "publications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
		@NamedQuery(name = "Publication.countSearch", query = "SELECT count(a) FROM Publication a WHERE a.content like :title or a.title like :title"),
		@NamedQuery(name = "Publication.countAll", query = "SELECT count(a) FROM Publication a"),
		@NamedQuery(name = "Publication.findAll", query = "SELECT a FROM Publication a"),
		@NamedQuery(name = "Publication.searchByName", query = "SELECT a FROM Publication a WHERE a.title like :title"),
		@NamedQuery(name = "Publication.searchContent", query = "SELECT a FROM Publication a WHERE a.content like :title or a.title like :title"),
		@NamedQuery(name = "Publication.getByName", query = "SELECT a FROM Publication a WHERE a.title = :title"),
		@NamedQuery(name = "Publication.getById", query = "SELECT a FROM Publication a WHERE a.uuid = :uuid"),
		@NamedQuery(name = "Publication.findBorrow", query = "SELECT DISTINCT b FROM Borrow b, Publication p JOIN b.publication bPublication JOIN p.borrows pPublication WHERE bPublication.uuid = pPublication.publication.uuid AND p.title LIKE :title") })
public abstract class Publication extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Lob
	private String content;
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
	@XmlTransient
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

	@Transient
	private int startIndex = 0;
	@Transient
	private int endingIndex = 0;
	@Transient
	private int pageCharCount = 2000;
	@Transient
	String returnString;

	public Publication() {
	}

	/**
	 * Retrieve a substring of the actual string, given by the @{code
	 * beginIndex}, {@code endIndex} bounds
	 *
	 * @param beginIndex
	 *            - the first character from which to start the retrieving
	 * @param endIndex
	 *            - the last character until which to do the retrieving
	 * @return
	 */
	public String getSubcontent(final int beginIndex, final int endIndex) {
		return this.content.substring(beginIndex, endIndex);
	}

	public int getPageCharCount() {
		return this.pageCharCount;
	}

	/**
	 * Go to the next page, retrieve the next {@code pageCharCount} number
	 * characters
	 */
	public void nextPage() {
		this.startIndex = this.endingIndex;
		this.endingIndex = this.startIndex + this.pageCharCount;
		getPageContent();

	}

	/**
	 * Go to the previous page, retrieve the previous {@code pageCharCount}
	 * number characters
	 */
	public void previousPage() {
		this.endingIndex = this.startIndex;
		this.startIndex = this.endingIndex - this.pageCharCount;
		getPageContent();
	}

	public String getPageContent() {
		/**
		 * if content length doesn't exceed the page character limit, then show
		 * characters from start until content length
		 */
		if (this.pageCharCount >= this.content.length() - 1) {
			this.pageCharCount = this.content.length() - 1;
		}
		/**
		 * inside the text bounds
		 */
		if ((this.startIndex < this.content.length() - 1) && (this.endingIndex > 0) && (this.startIndex >= 0)
				&& (this.endingIndex < this.content.length() - 1)) {
			this.returnString = getSubcontent(this.startIndex, this.endingIndex);
		}
		/**
		 * starting point is less than the minimal character number of the text
		 */
		else if (this.startIndex < 0) {
			this.returnString = getSubcontent(0, this.pageCharCount);
			this.startIndex = this.endingIndex;
			this.endingIndex = this.startIndex + this.pageCharCount;
		}
		/**
		 * it was the first time the method was invoked, show the first page
		 */
		else {
			this.returnString = getSubcontent(this.startIndex, this.endingIndex + this.pageCharCount);
			this.startIndex = this.endingIndex;
			this.endingIndex = this.startIndex + this.pageCharCount;
		}
		return this.returnString;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
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

	public void setBorrows(final List<Borrow> borrows) {
		if (borrows == null) {
			this.borrows = null;
		} else {
			this.borrows = new HashSet<Borrow>(borrows);
		}
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

	public void setPageCharCount(final int pageCharCount) {
		this.pageCharCount = pageCharCount;
	}

	public String getReturnString() {
		return this.returnString;
	}

}