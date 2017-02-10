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

import gallb.wildfly.users.common.IPublisher;
import gallb.wildfly.users.common.LibraryException;
import model.Publisher;

/**
 * Publisher manager. Invokes the CRUD methods of the server
 * 
 * @author kiska
 *
 */
@Named("publisherbean")
@SessionScoped
public class PublisherManagedBean implements Serializable {

	private Logger oLogger = Logger.getLogger(ManagedBean.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublisher oPublisherBean;

	/**
	 * List containing all publishers
	 */
	private List<Publisher> publishersList = new ArrayList<>();
	/**
	 * Object containing the currently selected publisher
	 */
	private Publisher currentPublisher = null;

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
		publishersList = new ArrayList<>();
		try {
			publishersList = oPublisherBean.getAll();
		} catch (LibraryException e) {
			this.error("Server internal error.");
		}
		return publishersList;
	}

	/**
	 * Searches for all publishers whom name contain the {@code p_searchTxt}
	 * input data
	 * 
	 * @param p_searchTxt
	 *            - a string containing the search term
	 * @return - a list of publisher objects if any matches were found
	 */
	public List<Publisher> search(String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			publishersList = new ArrayList<>();
			try {
				publishersList = oPublisherBean.search(p_searchTxt);
			} catch (LibraryException e) {
				this.error(e.getMessage());
			}
		} else {
			this.error("Keyword too short. Min. 3 characters req.");
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
			this.error("Empty field");
		}
		if (p_value == "") {
			this.error("Empty field");
		}
		try {
			Publisher tmpPublisher = new Publisher();
			tmpPublisher.setName(p_value);
			oPublisherBean.store(tmpPublisher);
			publishersList.add(tmpPublisher);
			this.info("Succesfully added: " + p_value);
		} catch (LibraryException e) {
			this.error(e.getMessage());
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
	 * Removes the currently selected publisher
	 */
	public void remove() {
		if (currentPublisher == null) {
			this.error("Empty field");
		} else {
			try {
				oPublisherBean.remove(currentPublisher.getUuid());
				publishersList = oPublisherBean.getAll();
				this.info("Delete succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				this.error(e.getMessage());
			}
		}
	}
}
