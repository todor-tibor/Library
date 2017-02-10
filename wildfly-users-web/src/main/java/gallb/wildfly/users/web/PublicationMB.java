package gallb.wildfly.users.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IPublication;
import gallb.wildfly.users.common.LibraryException;
import model.Publication;

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

	/**
	 * 
	 */
	private List<Publication> publicationList = new ArrayList<>();// Currently
																	// displayed
																	// publications.
	private Publication currentPublication = null;// Currently selected
													// publication.

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public Publication getCurrentPublication() {
		return currentPublication;
	}

	public void setCurrentPublication(Publication currentPublication) {
		this.currentPublication = currentPublication;
	}

	/**
	 * Requests all publication objects and stores them in publicationList.
	 * 
	 * @return List of all publications from persistency.
	 */
	public List<Publication> getAll() {
		oLogger.info("--getAllPublications()--");
		publicationList = new ArrayList<>();
		try {
			oLogger.info("--getAllPublications()--publications queried");
			publicationList = oPublicationBean.getAll();
		} catch (LibraryException e) {
			MessageService.error("Server internal error.");
		}
		return publicationList;
	}

	/**
	 * Search for publication by publicationname and stores them in
	 * publicationList.
	 * 
	 * @param p_searchTxt
	 *            publicationname.
	 * @return List of publication objects found.
	 */
	public List<Publication> search(String p_searchTxt) {
		oLogger.info("--search publication--" + p_searchTxt);
		if (p_searchTxt.length() >= 3) {
			publicationList = new ArrayList<>();
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

	public void store(Publication p_value) {// store newspaper, magazine or book
		try {
			oPublicationBean.store(p_value);
			publicationList.add(p_value);
			MessageService.info("Succesfully added: " + p_value);
		} catch (LibraryException e) {
			MessageService.error(e.getMessage());
		}
	}

	/**
	 * Renames currently selected publication.
	 * 
	 * @param p_newTxt
	 *            - new publicationname.
	 */
	public void update(String p_newTxt) {
		oLogger.info("--update publication ManagedBean--id:" + currentPublication.getTitle() + "new name: " + p_newTxt);
		if ((currentPublication != null) && (p_newTxt.length() >= 3)) {
			try {
				currentPublication.setTitle(p_newTxt);
				oPublicationBean.update(currentPublication);
				publicationList = oPublicationBean.getAll();
				oLogger.info("**********************update succesfull************************************");
				MessageService.info("Update succesfull.");
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}
		} else {
			MessageService.error("New name too short.");
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
}
