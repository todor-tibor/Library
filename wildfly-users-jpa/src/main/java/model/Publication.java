package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
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
@NamedQueries({ @NamedQuery(name = "Publication.findAll", query = "SELECT a FROM Publication a"),
	@NamedQuery(name = "Publication.searchByName", query = "SELECT a FROM Publication a WHERE a.title like :title"),
	@NamedQuery(name = "Publication.getById", query = "SELECT a FROM Publication a WHERE a.uuid = :uuid") })
public abstract class Publication extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "nr_of_copys")
	private int nrOfCopys;

	@Column(name = "on_stock")
	private int onStock;

	@Temporal(TemporalType.DATE)
	@Column(name = "publication_date")
	private Date publicationDate;

	private String title;

	// bi-directional many-to-one association to Borrow
	@OneToMany(mappedBy = "publication")
	private List<Borrow> borrows;

	// bi-directional many-to-one association to Publisher
	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;

	public Publication() {
	}

	public int getNrOfCopys() {
		return this.nrOfCopys;
	}

	public void setNrOfCopys(int nrOfCopys) {
		this.nrOfCopys = nrOfCopys;
	}

	public int getOnStock() {
		return this.onStock;
	}

	public void setOnStock(int onStock) {
		this.onStock = onStock;
	}

	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Borrow> getBorrows() {
		return this.borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

	public Borrow addBorrow(Borrow borrow) {
		getBorrows().add(borrow);
		borrow.setPublication(this);

		return borrow;
	}

	public Borrow removeBorrow(Borrow borrow) {
		getBorrows().remove(borrow);
		borrow.setPublication(null);

		return borrow;
	}

	public Publisher getPublisher() {
		return this.publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	

}