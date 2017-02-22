package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.util.ExceptionHandler;

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

	private Logger oLogger = Logger.getLogger(PublicationMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublicationService oPublicationBean;

	@Inject
	ExceptionHandler exceptionHandler;
	@Inject
	MessageService message;

	/*
	 * variables to select publication type, authors or publisher for update and
	 * insert
	 */
	private List<Author> authors, currentAuthors;
	private Publisher currentPublisher;
	private String type;

	/*
	 * Currently displayed publications.
	 */
	private List<Publication> publicationList = new ArrayList<>();

	/*
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
			this.publicationList = oPublicationBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
		return this.publicationList;
	}

	/**
	 * Search for publication by title and stores them in
	 * {@code publicationList}.
	 * 
	 * @param p_searchTxt
	 *            publication title.
	 * @return List of publication objects found.
	 */
	public List<Publication> search(String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			this.publicationList.clear();
			try {
				this.publicationList = oPublicationBean.search(p_searchTxt);
				if (this.publicationList.isEmpty()) {
					message.warn("ejb.message.noEntity");
				}
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			message.warn("managedbean.string");
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
	public void store(String pTitle, String pNrOfCopies) {
		if (pTitle.isEmpty() || pNrOfCopies.isEmpty()) {
			message.warn("managedbean.required");
			return;
		}
		int nrOfCopies;
		try {
			nrOfCopies = Integer.parseInt(pNrOfCopies);
		} catch (NumberFormatException e) {
			message.warn("managedbean.numberFormatExeption");
			return;
		}
		Publication publication;
		switch (this.type) {
		case "Book":
			publication = new Book();
			if (this.currentAuthors == null) {
				message.warn("managedbean.required");
				return;
			}
			((Book) publication).setAuthors(this.currentAuthors);
			break;
		case "Magazine":
			publication = new Magazine();
			if (this.currentAuthors == null) {
				message.warn("managedbean.required");
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
		publication.setPublicationDate(new Date());

		try {
			oPublicationBean.store(publication);
			publicationList.add(publication);
			message.info("managedBean.storeSuccess");
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Update currently selected publication. Uses {@code currentPublication}
	 * and currently selected authors
	 */
	public void update() {
		if ((this.currentPublication != null) && this.currentPublication.getTitle() != null
				&& this.currentPublication.getPublisher() != null) {
			if (currentPublication instanceof Book)
				if (authors != null && !authors.isEmpty()) {
					((Book) this.currentPublication).setAuthors(this.authors);
				} else {
					message.warn("managedbean.required");
					return;
				}
			if ("Magazine".equals(currentPublication.getClass().getSimpleName()))
				if (this.authors != null && !this.authors.isEmpty()) {
					((Magazine) this.currentPublication).setAuthors(this.authors);
				} else {
					message.warn("managedbean.required");
					return;
				}
			try {
				oPublicationBean.update(this.currentPublication);
				this.publicationList = oPublicationBean.getAll();
				message.info("managedbean.updateSuccess");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			message.warn("managedbean.required");
		}
	}

	/**
	 * Deletes currently selected publication from database.
	 */
	public void remove() {
		if (this.currentPublication == null) {
			message.error("managedbean.empty");
		} else {
			try {
				oPublicationBean.remove(this.currentPublication.getUuid());
				publicationList = oPublicationBean.getAll();
				message.info("managedbean.deleteSuccess");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		}

	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Check if has selected publication
	 * 
	 * @return - true if it has, false otherwise
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
	 * @return - true if it has, false otherwise
	 */
	public Boolean hasAuthor() {
		if (this.currentPublication == null) {
			return false;
		}
		if (currentPublication instanceof Newspaper) {
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
		if (authors != null) {
			authors.clear();
		}
		if (currentPublication instanceof Book) {
			this.authors = ((Book) this.currentPublication).getAuthors();
		}
		if (currentPublication instanceof Magazine) {
			this.authors = ((Magazine) this.currentPublication).getAuthors();
		}
		return this.authors;
	}

	/**
	 * Getters and setters for private variables
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Author> getCurrentAuthors() {
		return this.currentAuthors;
	}

	public void setCurrentAuthors(List<Author> currentAuthors) {
		this.currentAuthors = currentAuthors;
	}

	public Publisher getCurrentPublisher() {
		return this.currentPublisher;
	}

	public void setCurrentPublisher(Publisher currentPublisher) {
		this.currentPublisher = currentPublisher;
	}

	public List<Publication> getPublicationList() {
		return this.publicationList;
	}

	public Publication getCurrentPublication() {
		return this.currentPublication;
	}

	public void setCurrentPublication(Publication currentPublication) {
		this.currentPublication = currentPublication;
	}
}
