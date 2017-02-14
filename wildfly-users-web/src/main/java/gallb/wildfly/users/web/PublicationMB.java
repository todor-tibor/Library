package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IPublication;
import gallb.wildfly.users.common.LibraryException;
import model.Author;
import model.Book;
import model.Magazine;
import model.Newspaper;
import model.Publication;
import model.Publisher;

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

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(PublicationMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublication oPublicationBean;

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
		oLogger.info("--getAllPublications()--");
		publicationList.clear();
		try {
			oLogger.info("--getAllPublications()--publications queried");
			publicationList = oPublicationBean.getAll();
		} catch (LibraryException e) {
			MessageService.error("Server internal error.");
		}
		return publicationList;
	}

	/**
	 * Search for publication by title and stores them in publicationList.
	 * 
	 * @param p_searchTxt
	 *            publication title.
	 * @return List of publication objects found.
	 */
	public List<Publication> search(String p_searchTxt) {
		oLogger.info("--search publication--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			publicationList.clear();
			try {
				publicationList = oPublicationBean.search(p_searchTxt);
			} catch (LibraryException e) {
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
		return publicationList;
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
	public void store(String p_title, String p_nrOfCopies) {
		if(p_title.isEmpty() || p_nrOfCopies.isEmpty()){
			MessageService.warn("All field is requered");
		}
		try{
			int nrOfCopies=Integer.parseInt(p_nrOfCopies);
		}catch (NumberFormatException e) {
			MessageService.warn("number of copies is not a valid Number");
		}
		oLogger.info("-------------nrOfCopies:  " + p_nrOfCopies + " type:  " + type);
		Publication p_value;
		try {
			switch (type) {
			case "Book":
				p_value = new Book();
				if(currentAuthors==null){
					MessageService.warn("All field is requered");
					return;
				}
				((Book) p_value).setAuthors(currentAuthors);
				break;
			case "Magazine":
				p_value = new Magazine();
				if(currentAuthors==null){
					MessageService.warn("All field is requered");
					return;
				}
				((Magazine) p_value).setAuthors(currentAuthors);
				break;
			default:
				p_value = new Newspaper();
				break;
			}
			p_value.setTitle(p_title);
			p_value.setNrOfCopys(Integer.parseInt(p_nrOfCopies));
			p_value.setOnStock(Integer.parseInt(p_nrOfCopies));
			p_value.setPublisher(currentPublisher);
			p_value.setPublicationDate(new Date());
			oPublicationBean.store(p_value);
			publicationList.add(p_value);
			MessageService.info("Succesfully added: " + p_value);
		} catch (LibraryException e) {
			MessageService.error(e.getMessage());
		}
	}

	/**
	 * Update currently selected publication. Use currentPublication,and
	 * currently selected authors
	 */
	public void update() {
		if ((currentPublication != null) && currentPublication.getTitle() != null
				&& currentPublication.getPublisher() != null) {
			if ("Book".equals(currentPublication.getClass().getSimpleName()))
				if (authors != null && !authors.isEmpty()) {
					((Book) currentPublication).setAuthors(authors);
				} else {
					MessageService.warn("All field is requered");
					return;
				}
			if ("Magazine".equals(currentPublication.getClass().getSimpleName()))
				if (authors != null && !authors.isEmpty()) {
					((Magazine) currentPublication).setAuthors(authors);
				} else {
					MessageService.warn("All field is requered");
					return;
				}
			try {
				oPublicationBean.update(currentPublication);
				publicationList = oPublicationBean.getAll();
				oLogger.info("**********************update succesfull************************************");
				MessageService.info("Update succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.warn("All field is requered");
		}
	}

	/**
	 * Deletes currently selected publication from persistency.
	 */
	public void remove() {
		oLogger.info("--remove publication by Id ManagedBean--p_id: " + currentPublication.getTitle());
		if (currentPublication == null) {
			MessageService.error("Empty field");
		} else {
			try {
				oPublicationBean.remove(currentPublication.getUuid());
				publicationList = oPublicationBean.getAll();
				MessageService.info("Delete succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		}
	}

	public String getType() {
		oLogger.info("get type--------:" + type);
		return type;
	}

	public void setType(String type) {
		oLogger.info("-----------------type changed" + type);
		this.type = type;
	}

	/**
	 * Check if have selected publication
	 * 
	 * @return
	 */
	public Boolean isSelected() {
		oLogger.info("-------------------is selected: " + currentPublication);
		if (currentPublication == null) {
			oLogger.error("-------------+++++++++++++No selected publication");
			return false;
		} else
			return true;
	}

	/**
	 * Check if the Publication selected have property authors (only the
	 * Newspaper can't have)
	 * 
	 * @return true if have
	 */
	public Boolean hasAuthor() {
		oLogger.info("-------------------has authors: " + currentPublication);
		if (currentPublication == null) {
			oLogger.error("-------------+++++++++++++No selected publication");
			return false;
		}

		oLogger.info("-------has authors-----: " + currentPublication.getClass().getSimpleName());
		if ("Newspaper".equals(currentPublication.getClass().getSimpleName())) {
			return false;
		} else
			return true;
	}

	public List<Author> getAuthors() {
		if ("Book".equals(currentPublication.getClass().getSimpleName()))
			authors = ((Book) currentPublication).getAuthors();
		if ("Magazine".equals(currentPublication.getClass().getSimpleName()))
			authors = ((Magazine) currentPublication).getAuthors();
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Author> getCurrentAuthors() {
		return currentAuthors;
	}

	public void setCurrentAuthors(List<Author> currentAuthors) {
		this.currentAuthors = currentAuthors;
	}

	public Publisher getCurrentPublisher() {
		return currentPublisher;
	}

	public void setCurrentPublisher(Publisher currentPublisher) {
		this.currentPublisher = currentPublisher;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public Publication getCurrentPublication() {
		return currentPublication;
	}

	public void setCurrentPublication(Publication currentPublication) {
		this.currentPublication = currentPublication;
	}
}
