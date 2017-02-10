package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IAuthor;
import gallb.wildfly.users.common.LibraryException;
import model.Author;

/**
 * Author manager.
 * 
 * @author sipost
 */
@Named("authorBean")

@SessionScoped
public class AuthorMB implements Serializable {

	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(AuthorMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IAuthor oAuthorBean;

	/**
	 * 
	 */
	private List<Author> authorList = new ArrayList<>();// Currently displayed
														// authors.
	private Author currentAuthor = null;// Currently selected author.

	/**
	 * Sets faces context error message.
	 * 
	 * @param message
	 */
	public void error(String message) {
		oLogger.info("**********************Error CALLED***************************");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}

	/**
	 * Sets faces context info message.
	 * 
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
	}

	/**
	 * Sets faces context warning message.
	 * 
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
	}

	/**
	 * Sets faces context fatal error message.
	 * 
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
	}

	
	/*
	 * getters and setters for private variables
	 * 
	 */
	public List<Author> getAuthorList() {
		return authorList;
	}

	public Author getCurrentAuthor() {
		return currentAuthor;
	}

	public void setCurrentAuthor(Author currentAuthor) {
		this.currentAuthor = currentAuthor;
	}

	/**
	 * Requests all author objects and stores them in authorList.
	 * 
	 * @return List of all authors from database.
	 */
	public List<Author> getAll() {
		oLogger.info("--getAllAuthors()--");
		authorList = new ArrayList<>();
		try {
			oLogger.info("--getAllAuthors()--authors queried");
			authorList = oAuthorBean.getAll();
		} catch (LibraryException e) {
			this.error(e.getMessage());
		}
		return authorList;
	}

	/**
	 * Search for author by authorname and stores them in authorList.
	 * 
	 * @param p_searchTxt
	 *            authorname.
	 * @return List of author objects found.
	 */
	public List<Author> search(String p_searchTxt) {
		oLogger.info("--search author--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			authorList = new ArrayList<>();
			try {
				authorList = oAuthorBean.search(p_searchTxt);
			} catch (LibraryException e) {
				this.error(e.getMessage());
			}
		} else {
			this.error("Keyword too short. Min. 3 characters req.");
		}
		return authorList;
	}

	/**
	 * Stores new author with authorname.
	 * 
	 * @param p_value
	 *            - authorname
	 */

	public void store(String p_value) {
		oLogger.info("--store author--");
		oLogger.info("--store param: " + p_value);
		if (p_value.isEmpty()) {
			this.error("Empty field");
		}
		if (p_value == "") {
			this.error("Empty field");
		}
		try {
			Author tmpAuthor = new Author();
			tmpAuthor.setName(p_value);
			oAuthorBean.store(tmpAuthor);
			authorList.add(tmpAuthor);
			this.info("Succesfully added: " + p_value);
		} catch (LibraryException e) {
			this.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected author.
	 * 
	 * @param p_newTxt
	 *            - new authorname.
	 */
	public void update(String p_newTxt) {
		oLogger.info("--update author ManagedBean--id:" + currentAuthor.getName() + "new name: " + p_newTxt);
		if ((currentAuthor != null) && (p_newTxt.length() >= 3)) {
			try {
				currentAuthor.setName(p_newTxt);
				oAuthorBean.update(currentAuthor);
				authorList = oAuthorBean.getAll();
				oLogger.info("**********************update succesfull************************************");
				this.info("Update succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				this.error(e.getMessage());
			}
		} else {
			this.error("New name too short.");
		}
	}

	/**
	 * Deletes currently selected author from persistency.
	 */
	public void remove() {
		oLogger.info("--remove author by Id ManagedBean--p_id: " + currentAuthor.getName());
		if (currentAuthor == null) {
			this.error("Empty field");
		} else {
			try {
				oAuthorBean.remove(currentAuthor.getUuid());
				authorList = oAuthorBean.getAll();
				this.info("Delete succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				this.error(e.getMessage());
			}
		}
	}
}
