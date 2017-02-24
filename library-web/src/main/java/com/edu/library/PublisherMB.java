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

	private Logger oLogger = Logger.getLogger(PublisherMB.class);
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
			message.error("managedbean.string");
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
			message.error("managedbean.empty");
		}
		if (p_value == "") {
			message.error("managedbean.empty");
		}
		try {
			Publisher tmpPublisher = new Publisher();
			tmpPublisher.setName(p_value);
			oPublisherBean.store(tmpPublisher);
			publishersList.add(tmpPublisher);
			message.info("managedBean.storeSuccess");
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
				message.info("managedbean.updateSuccess");
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);

			}
		} else {
			message.error("managedbean.string");
		}
	}

	/**
	 * Removes the currently selected publisher
	 */
	public void remove() {
		if (currentPublisher == null) {
			message.error("managedbean.empty");
		} else {
			try {
				oPublisherBean.remove(currentPublisher.getUuid());
				publishersList = oPublisherBean.getAll();
				message.info("managedbean.deleteSuccess");
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
