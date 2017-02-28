package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.model.util.ReadXMLFile;
import com.edu.library.model.util.WriteXMLFile;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 *
 * Publication manager.
 *
 * @author sipost
 * @author kiska
 *
 */
@Named("publicationBean")
@SessionScoped
public class PublicationMB implements Serializable {

	private final Logger logger = Logger.getLogger(PublicationMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublicationService oPublicationBean;

	@Inject
	ExceptionHandler exceptionHandler;
	@Inject
	MessageService message;

	PublicationFilter filter = new PublicationFilter();
	private Date date = new Date();

	/**
	 * Variables to select publication type, authors or publisher for update and
	 * insert
	 */
	private List<Author> authors, currentAuthors;
	private Publisher currentPublisher;
	private String type;

	/**
	 * Currently displayed publications.
	 */
	private List<Publication> publicationList = new ArrayList<>();

	/**
	 * Currently selected publication.
	 */
	private Publication currentPublication = null;

	/**
	 * Requests all publication objects and stores them in
	 * {@code publicationList}.
	 *
	 * @return List of all publications from database.
	 */
	public List<Publication> getAll() {
		this.publicationList.clear();
		try {
			this.publicationList = this.oPublicationBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.publicationList;
	}

	/**
	 * Search for publication by title and stores them in
	 * {@code publicationList}.
	 *
	 * @param searchTxt
	 *            publication title.
	 * @return List of publication objects found.
	 */
	public List<Publication> search(final String searchTxt) {
		if (searchTxt.length() >= 3) {
			this.publicationList.clear();
			try {
				this.publicationList = this.oPublicationBean.search(searchTxt);
				if (this.publicationList.isEmpty()) {
					this.message.warn("ejb.message.noEntity");
				}
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.warn("managedbean.string");
		}
		return this.publicationList;
	}

	/**
	 * Insert new Book, Magazine or Newspaper uses {@code currentAuthors} and
	 * {@code currentPublisher}
	 *
	 * @param p_title-new
	 *            title
	 * @param p_nrOfCopies-number
	 *            of copies left, and copies on stock
	 */
	public void store(final String pTitle, final String pNrOfCopies) {
		if (pTitle.isEmpty() || pNrOfCopies.isEmpty()) {
			this.message.warn("managedbean.required");
			return;
		}
		int nrOfCopies;
		try {
			nrOfCopies = Integer.parseInt(pNrOfCopies);
		} catch (final NumberFormatException e) {
			this.message.warn("managedbean.numberFormatExeption");
			return;
		}
		Publication publication;
		switch (this.type) {
		case "Book":
			publication = new Book();
			if (this.currentAuthors == null) {
				this.message.warn("managedbean.required");
				return;
			}
			((Book) publication).setAuthors(this.currentAuthors);
			break;
		case "Magazine":
			publication = new Magazine();
			if (this.currentAuthors == null) {
				this.message.warn("managedbean.required");
				return;
			}
			((Magazine) publication).setAuthors(this.currentAuthors);
			break;
		default:
			publication = new Newspaper();
			break;
		}
		publication.setTitle(pTitle);
		publication.setNrOfCopys(nrOfCopies);
		publication.setOnStock(nrOfCopies);
		publication.setPublisher(this.currentPublisher);
		final Calendar c = Calendar.getInstance();
		c.setTime(this.date);
		c.add(Calendar.DATE, 1);
		this.date = c.getTime();
		publication.setPublicationDate(this.date);

		try {
			this.oPublicationBean.store(publication);
			this.publicationList.add(publication);
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Update currently selected publication. Uses {@code currentPublication}
	 * and currently selected authors
	 */
	public void update() {
		if ((this.currentPublication != null) && this.currentPublication.getTitle() != null
				&& this.currentPublication.getPublisher() != null) {
			if (this.currentPublication instanceof Book) {
				if (this.authors != null && !this.authors.isEmpty()) {
					((Book) this.currentPublication).setAuthors(this.authors);
				} else {
					this.message.warn("managedbean.required");
					return;
				}
			}
			if ("Magazine".equals(this.currentPublication.getClass().getSimpleName())) {
				if (this.authors != null && !this.authors.isEmpty()) {
					((Magazine) this.currentPublication).setAuthors(this.authors);
				} else {
					this.message.warn("managedbean.required");
					return;
				}
			}
			try {
				this.oPublicationBean.update(this.currentPublication);
				this.publicationList = this.oPublicationBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.warn("managedbean.required");
		}
	}

	/**
	 * Deletes currently selected publication from database.
	 */
	public void remove() {
		if (this.currentPublication == null) {
			this.message.error("managedbean.empty");
		} else {
			try {
				this.oPublicationBean.remove(this.currentPublication.getUuid());
				this.publicationList = this.oPublicationBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}

	}

	/**
	 * Filter publication
	 *
	 * @return
	 */
	public List<Publication> filterPublication() {
		this.publicationList.clear();
		try {
			this.publicationList = this.oPublicationBean.filterPublication(this.filter);
			if (this.publicationList.isEmpty()) {
				this.message.warn("ejb.message.noEntity");
			}
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.publicationList;
	}

	/**
	 * Check if there is selected publication
	 *
	 * @return - true if it is, false otherwise
	 */
	public Boolean isSelected() {
		if (this.currentPublication == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check if the Publication selected has authors property(the Newspaper
	 * can't have any authors)
	 *
	 * @return - true if it is, false otherwise
	 */
	public Boolean hasAuthor() {
		if (this.currentPublication == null) {
			return false;
		}
		if (this.currentPublication instanceof Newspaper) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Return the authors of the current publication
	 *
	 * @return List<Authors>
	 */
	public List<Author> getAuthors() {
		if (this.authors != null) {
			this.authors.clear();
		}
		if (this.currentPublication instanceof Book) {
			this.authors = ((Book) this.currentPublication).getAuthors();
		}
		if (this.currentPublication instanceof Magazine) {
			this.authors = ((Magazine) this.currentPublication).getAuthors();
		}
		return this.authors;
	}

	/**
	 * Getters and setters for private variables
	 */

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setAuthors(final List<Author> authors) {
		this.authors = authors;
	}

	public List<Author> getCurrentAuthors() {
		return this.currentAuthors;
	}

	public void setCurrentAuthors(final List<Author> currentAuthors) {
		this.currentAuthors = currentAuthors;
	}

	public Publisher getCurrentPublisher() {
		return this.currentPublisher;
	}

	public void setCurrentPublisher(final Publisher currentPublisher) {
		this.currentPublisher = currentPublisher;
	}

	public List<Publication> getPublicationList() {
		return this.publicationList;
	}

	public Publication getCurrentPublication() {
		return this.currentPublication;
	}

	public void setCurrentPublication(final Publication currentPublication) {
		this.currentPublication = currentPublication;
	}

	public PublicationFilter getFilter() {
		return this.filter;
	}

	public void setFilter(final PublicationFilter filter) {
		this.filter = filter;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	/**
	 * Export publications to ".xml" extension.
	 */
	public void exportPublication() {
		WriteXMLFile.exportData(getAll(), "publications");
	}

<<<<<<< HEAD
	/**
	 * Import publications from ".xml" extension file.
	 * 
	 * @return - list of publications imported from file.
	 */
	public List<Publication> importPublication() {
		this.publicationList = ReadXMLFile.importData("publications");
		for (final Publication p : this.publicationList) {
			try {
				this.oPublicationBean.getById(p.getUuid());
			} catch (final Exception e) {
				this.oPublicationBean.store(p);
			}
			try {
				this.oPublicationBean.update(p);
			} catch (final NoResultException e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}
=======
	public List<Publication> importPublication() {
		this.publicationList = ReadXMLFile.importData("publications");
>>>>>>> branch 'xml' of https://github.com/todor-tibor/Library.git
		return this.publicationList;
	}
}
