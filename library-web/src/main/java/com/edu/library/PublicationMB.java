package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.IPublicationService;
import com.edu.library.LibraryException;
import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.PropertyProvider;

/**
 * 
 * Publication manager.
 * 
 * @author sipost
 * 
 */
@Named("publicationBean")

@SessionScoped
public class PublicationMB implements Serializable {

	private Logger oLogger = Logger.getLogger(PublicationMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublicationService oPublicationBean;

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
	 * Requests all publication objects and stores them in publicationList.
	 * 
	 * @return List of all publications from persistency.
	 */
	public List<Publication> getAll() {
		remove();

		/*
		 * 
		 * this.publicationList.clear(); try { this.publicationList =
		 * oPublicationBean.getAll(); } catch (LibraryException e) {
		 * oLogger.error(e.getMessage()); MessageService.error(e.getMessage());
		 * }
		 */
		return this.publicationList;
	}

	/**
	 * Search for publication by title and stores them in publicationList.
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
					MessageService.warn("No entity found");
				}
			} catch (LibraryException e) {
				oLogger.error(e.getMessage());
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
		return this.publicationList;
	}

	/**
	 * Insert new Book, Magazine or Newspaper Use currentAuthors and
	 * currentPublisher
	 * 
	 * @param p_title-new
	 *            title
	 * @param p_nrOfCopies-number
	 *            of copies left, and copies on stock
	 */
	public void store(String pTitle, String pNrOfCopies) {
		if (pTitle.isEmpty() || pNrOfCopies.isEmpty()) {
			MessageService.warn("All field is required");
			return;
		}
		int nrOfCopies;
		try {
			nrOfCopies = Integer.parseInt(pNrOfCopies);
		} catch (NumberFormatException e) {
			MessageService.warn("Number of copies is not a valid Number");
			return;
		}
		Publication publication;
		switch (this.type) {
		case "Book":
			publication = new Book();
			if (this.currentAuthors == null) {
				MessageService.warn("All field is required");
				return;
			}
			((Book) publication).setAuthors(this.currentAuthors);
			break;
		case "Magazine":
			publication = new Magazine();
			if (this.currentAuthors == null) {
				MessageService.warn("All field is required");
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
			MessageService.info("Succesfully added: " + publication);
		} catch (LibraryException e) {
			oLogger.error(e.getMessage());
			MessageService.error(e.getMessage());
		}
	}

	/**
	 * Update currently selected publication. Use currentPublication,and
	 * currently selected authors
	 */
	public void update() {
		if ((this.currentPublication != null) && this.currentPublication.getTitle() != null
				&& this.currentPublication.getPublisher() != null) {
			if (currentPublication instanceof Book)
				if (authors != null && !authors.isEmpty()) {
					((Book) this.currentPublication).setAuthors(this.authors);
				} else {
					MessageService.warn("All field is requered");
					return;
				}
			if ("Magazine".equals(currentPublication.getClass().getSimpleName()))
				if (this.authors != null && !this.authors.isEmpty()) {
					((Magazine) this.currentPublication).setAuthors(this.authors);
				} else {
					MessageService.warn("All field is requered");
					return;
				}
			try {
				oPublicationBean.update(this.currentPublication);
				this.publicationList = oPublicationBean.getAll();
				oLogger.info("---update succesfull---");
				MessageService.info("Update succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.warn("All field is required");
		}
	}

	/**
	 * Deletes currently selected publication from persistency.
	 */
	public void remove() {
		try {
			oPublicationBean.remove("b03e86a5-538e-4697-bcd5-3827f1ad1760");
			oLogger.info("---------torolte");
		} catch (Exception e) {
			oLogger.info("nem sikerult torolni" + PropertyProvider.getProperty(e.getMessage()));
			new ExceptionHandler(e);
		}
		/*
		 * if (this.currentPublication == null) {
		 * MessageService.error("Empty field"); } else { try {
		 * oPublicationBean.remove(this.currentPublication.getUuid());
		 * publicationList = oPublicationBean.getAll();
		 * MessageService.info("Delete succesfull."); } catch (LibraryException
		 * e) { oLogger.error(e); MessageService.error(e.getMessage()); } }
		 */
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Check if have selected publication
	 * 
	 * @return
	 */
	public Boolean isSelected() {
		if (this.currentPublication == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check if the Publication selected have property authors (only the
	 * Newspaper can't have)
	 * 
	 * @return true if have
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
		authors.clear();
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
