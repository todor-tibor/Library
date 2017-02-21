package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.IPublisherService;
import com.edu.library.LibraryException;
import com.edu.library.model.Publisher;
import com.edu.library.util.ExceptionHandler;

/**
 * Publisher manager. Invokes the CRUD methods of the server
 * 
 * @author kiska
 *
 */
@Named("publisherbean")
@SessionScoped
public class PublisherMB implements Serializable {

	private Logger oLogger = Logger.getLogger(PublisherMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublisherService oPublisherBean;
	@Inject
	ExceptionHandler exceptionHandler;

	/**
	 * List containing all publishers
	 */
	private List<Publisher> publishersList = new ArrayList<>();
	/**
	 * Object containing the currently selected publisher
	 */
	private Publisher currentPublisher = null;

	public List<Publisher> getPublishersList() {
		return publishersList;
	}

	public Publisher getCurrentPublisher() {
		return currentPublisher;
	}

	public void setCurrentPublisher(Publisher currentPublisher) {
		this.currentPublisher = currentPublisher;
	}

	/**
	 * Returns all publishers
	 * 
	 * @return - a publisher list if database is not empty
	 */
	public List<Publisher> getAll() {
		publishersList.clear();
		;
		try {
			publishersList = oPublisherBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
		return publishersList;
	}

	/**
	 * Searches for all publishers whose name contain the {@code p_searchTxt}
	 * input data
	 * 
	 * @param p_searchTxt
	 *            - a string containing the search term
	 * @return - a list of publisher objects if any matches were found
	 */
	public List<Publisher> search(String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			publishersList.clear();
			try {
				publishersList = oPublisherBean.search(p_searchTxt);
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
		return publishersList;
	}

	/**
	 * Stores the new publisher given by {@code p_value} name into the database
	 * 
	 * @param p_value
	 *            - a string representation of the name the publisher will be
	 *            stored
	 */

	public void store(String p_value) {
		if (p_value.isEmpty()) {
			MessageService.error("Empty field");
		}
		if (p_value == "") {
			MessageService.error("Empty field");
		}
		try {
			Publisher tmpPublisher = new Publisher();
			tmpPublisher.setName(p_value);
			oPublisherBean.store(tmpPublisher);
			publishersList.add(tmpPublisher);
			MessageService.info("Succesfully added: " + p_value);
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Updates an existing publisher, setting it's name to the new name given as
	 * {@code p_newTxt}
	 * 
	 * @param p_newTxt
	 *            - a string representation of the new name
	 */
	public void update(String p_newTxt) {
		if ((currentPublisher != null) && (p_newTxt.length() >= 3)) {
			try {
				currentPublisher.setName(p_newTxt);
				oPublisherBean.update(currentPublisher);
				publishersList = oPublisherBean.getAll();
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
	 * Removes the currently selected publisher
	 */
	public void remove() {
		if (currentPublisher == null) {
			MessageService.error("Empty field");
		} else {
			try {
				oPublisherBean.remove(currentPublisher.getUuid());
				publishersList = oPublisherBean.getAll();
				MessageService.info("Delete succesfull.");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
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
}
