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
import com.edu.library.util.MessageService;

/**
 * Author manager.
 *
 * @author sipost
 * @author kiska
 */
@Named("authorBean")
@SessionScoped
public class AuthorMB implements Serializable {

	private final Logger logger = Logger.getLogger(AuthorMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IAuthorService oAuthorBean;

	@Inject
	private ExceptionHandler exceptionHandler;

	@Inject
	MessageService message;

	/**
	 * Currently displayed authors.
	 */
	private List<Author> authorList = new ArrayList<>();

	/**
	 * Currently selected author.
	 */
	private Author currentAuthor = null;

	/**
	 * Requests all author objects and stores them in authorList.
	 *
	 * @return List of all authors from database.
	 */
	public List<Author> getAll() {
		this.authorList.clear();
		try {
			this.authorList = this.oAuthorBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.authorList;
	}

	/**
	 * Search for author by author name and stores them in authorList.
	 *
	 * @param searchTxt
	 *            author's name.
	 * @return List of author objects found.
	 */
	public List<Author> search(final String searchTxt) {
		if (searchTxt.length() >= 3) {
			this.authorList.clear();
			try {
				this.authorList = this.oAuthorBean.search(searchTxt);
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);

			}
		} else {
			this.message.error("managedbean.string");
		}
		return this.authorList;
	}

	/**
	 * Stores new author with author name.
	 *
	 * @param value
	 *            - author's name
	 */

	public void store(final String value) {
		if (value.isEmpty() || value == "") {
			this.message.warn("managedbean.empty");
		}
		try {
			final Author tmpAuthor = new Author();
			tmpAuthor.setName(value);
			this.oAuthorBean.store(tmpAuthor);
			this.authorList.add(tmpAuthor);
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Renames currently selected author.
	 *
	 * @param newValue
	 *            - new author name.
	 */
	public void update(final String newValue) {
		if ((this.currentAuthor != null) && (newValue.length() >= 3)) {
			try {
				this.currentAuthor.setName(newValue);
				this.oAuthorBean.update(this.currentAuthor);
				this.authorList = this.oAuthorBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.error("managedbean.string");
		}
	}

	/**
	 * Deletes currently selected author from database.
	 */
	public void remove() {
		if (this.currentAuthor == null) {
			this.message.error("managedbean.empty");
		} else {
			try {
				this.oAuthorBean.remove(this.currentAuthor.getUuid());
				this.authorList = this.oAuthorBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
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

	/**
	 * Getters and setters for private variables
	 *
	 */
	public List<Author> getAuthorList() {
		return this.authorList;
	}

	public Author getCurrentAuthor() {
		return this.currentAuthor;
	}

	public void setCurrentAuthor(final Author currentAuthor) {
		this.currentAuthor = currentAuthor;
	}
}
