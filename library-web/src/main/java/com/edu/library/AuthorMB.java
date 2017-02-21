package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Author;
import com.edu.library.util.ExceptionHandler;

/**
 * Author manager.
 * 
 * @author sipost
 * @author kiska
 */
@Named("authorBean")
@SessionScoped
public class AuthorMB implements Serializable {

	private Logger oLogger = Logger.getLogger(AuthorMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IAuthorService oAuthorBean;

	@Inject
	private ExceptionHandler exceptionHandler;
	/**
	 * Currently displayed authors.
	 */
	private List<Author> authorList = new ArrayList<>();
	/**
	 * Currently selected author.
	 */
	private Author currentAuthor = null;

	/**
	 * Getters and setters for private variables
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
		authorList.clear();
		try {
			authorList = oAuthorBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
		return authorList;
	}

	/**
	 * Search for author by author name and stores them in authorList.
	 * 
	 * @param p_searchTxt
	 *            author's name.
	 * @return List of author objects found.
	 */
	public List<Author> search(String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			authorList.clear();
			try {
				authorList = oAuthorBean.search(p_searchTxt);

			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);

			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
		return authorList;
	}

	/**
	 * Stores new author with author name.
	 * 
	 * @param p_value
	 *            - author's name
	 */

	public void store(String p_value) {
		if (p_value.isEmpty()) {
			MessageService.error("Empty field");
		}
		if (p_value == "") {
			MessageService.error("Empty field");
		}
		try {
			Author tmpAuthor = new Author();
			tmpAuthor.setName(p_value);
			oAuthorBean.store(tmpAuthor);
			authorList.add(tmpAuthor);
			MessageService.info("Succesfully added: " + p_value);
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Renames currently selected author.
	 * 
	 * @param p_newTxt
	 *            - new author name.
	 */
	public void update(String p_newTxt) {
		if ((currentAuthor != null) && (p_newTxt.length() >= 3)) {
			try {
				currentAuthor.setName(p_newTxt);
				oAuthorBean.update(currentAuthor);
				authorList = oAuthorBean.getAll();
				MessageService.info("Update succesfull.");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			MessageService.error("New name too short.");
		}
	}

	/**
	 * Deletes currently selected author from database.
	 */
	public void remove() {
		if (currentAuthor == null) {
			MessageService.error("Empty field");
		} else {
			try {
				oAuthorBean.remove(currentAuthor.getUuid());
				authorList = oAuthorBean.getAll();
				MessageService.info("Delete succesfull.");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Checks whether the current author is selected.
	 * 
	 * @return - Returns true if it is, false otherwise.
	 */
	public Boolean isSelected() {
		if (this.currentAuthor == null) {
			return false;
		} else {
			return true;
		}
	}
}
