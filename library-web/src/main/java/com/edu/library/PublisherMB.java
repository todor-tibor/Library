package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Publisher;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 * Publisher manager. Invokes the CRUD methods of the server
 *
 * @author kiska
 *
 */
@Named("publisherbean")
@SessionScoped
public class PublisherMB implements Serializable {

	private final Logger logger = Logger.getLogger(PublisherMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublisherService oPublisherBean;
	@Inject
	ExceptionHandler exceptionHandler;

	@Inject
	MessageService message;

	/**
	 * List containing all publishers
	 */
	private List<Publisher> publishersList = new ArrayList<>();

	/**
	 * Object containing the currently selected publisher
	 */
	private Publisher currentPublisher = null;

	/**
	 * Returns all publishers
	 *
	 * @return - a publisher list if database is not empty
	 */
	public List<Publisher> getAll() {
		this.publishersList.clear();
		try {
			this.publishersList = this.oPublisherBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.publishersList;
	}

	/**
	 * Searches for all publishers whose name contain the {@code searchTxt}
	 * input data
	 *
	 * @param searchTxt
	 *            - a string containing the search term
	 * @return - a list of publisher objects if any matches were found
	 */
	public List<Publisher> search(final String searchTxt) {
		if (searchTxt.length() >= 3) {
			this.publishersList.clear();
			try {
				this.publishersList = this.oPublisherBean.search(searchTxt);
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.error("managedbean.string");
		}
		return this.publishersList;
	}

	/**
	 * Stores the new publisher given by {@code value} name into the database
	 *
	 * @param value
	 *            - a string representation of the name the publisher will be
	 *            stored
	 */

	public void store(final String value) {
		if (value.isEmpty()) {
			this.message.error("managedbean.empty");
		}
		if (value == "") {
			this.message.error("managedbean.empty");
		}
		try {
			final Publisher tmpPublisher = new Publisher();
			tmpPublisher.setName(value);
			this.oPublisherBean.store(tmpPublisher);
			this.publishersList.add(tmpPublisher);
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Updates an existing publisher, setting it's name to the new name given as
	 * {@code newTxt}
	 *
	 * @param newTxt
	 *            - a string representation of the new name
	 */
	public void update(final String newTxt) {
		if ((this.currentPublisher != null) && (newTxt.length() >= 3)) {
			try {
				this.currentPublisher.setName(newTxt);
				this.oPublisherBean.update(this.currentPublisher);
				this.publishersList = this.oPublisherBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);

			}
		} else {
			this.message.error("managedbean.string");
		}
	}

	/**
	 * Removes the currently selected publisher
	 */
	public void remove() {
		if (this.currentPublisher == null) {
			this.message.error("managedbean.empty");
		} else {
			try {
				this.oPublisherBean.remove(this.currentPublisher.getUuid());
				this.publishersList = this.oPublisherBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Checks whether a publisher is selected.
	 *
	 * @return - true if it is, false otherwise
	 */
	public Boolean isSelected() {
		if (this.currentPublisher == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Getters and setters for private attributes
	 */

	public List<Publisher> getPublishersList() {
		return this.publishersList;
	}

	public Publisher getCurrentPublisher() {
		return this.currentPublisher;
	}

	public void setCurrentPublisher(final Publisher currentPublisher) {
		this.currentPublisher = currentPublisher;
	}
}
