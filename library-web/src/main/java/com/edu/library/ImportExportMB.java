package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.event.TabChangeEvent;

import com.edu.library.model.BaseEntity;
import com.edu.library.model.Role;
import com.edu.library.model.util.JAXB;
import com.edu.library.model.util.JaxbException;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 * ImportExport manager.
 *
 * @author sipost
 */
@Named("importExportBean")
@SessionScoped
public class ImportExportMB implements Serializable {

	private final Logger logger = Logger.getLogger(ImportExportMB.class);
	private static final long serialVersionUID = 91196305537954756L;

	@Inject
	private ExceptionHandler exceptionHandler;
	@Inject
	private MessageService message;

	@Inject
	private IPublicationService publicationBean;
	@Inject
	private IUserService userBean;
	@Inject
	private IRoleService roleBean;
	@Inject
	private IPublisherService publisherBean;
	@Inject
	private IAuthorService authorBean;
	@Inject
	private IBorrowService borrowBean;

	/**
	 * Class of entities for marshall or unmarshall
	 */
	@SuppressWarnings("rawtypes")
	private Class clazz = Role.class;

	/**
	 * Active tab define the list for marshall or unmarshall
	 */
	private String activeTab = "Role";

	/**
	 * Import list of entities (specified by type) from ".xml" extension file
	 * using JAXB and store them in database
	 *
	 * @return - list of entities imported from file.
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> importList() {
		List<T> list = new ArrayList<>();
		try {
			list = JAXB.unmarshallList(this.clazz, this.activeTab);
			saveEntities(list);
			this.message.info("import.done");
			this.logger.info("imported " + list.size() + " element");
		} catch (JaxbException e) {
			this.logger.error(e.getMessage());
			this.exceptionHandler.showMessage(e);
		}
		return list;
	}

	/**
	 * Export entities from database (specified by type) to ".xml" extension
	 * file using JAXB.
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> void exportList() {
		try {
			List<T> entities = getEntities();
			JAXB.marshall(entities, this.clazz, this.activeTab);
			this.message.info("export.done");
			this.logger.info("exported " + entities.size() + " element");
		} catch (JaxbException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Get entities specified by type from database
	 *
	 * @return entityList
	 */
	@SuppressWarnings("unchecked")
	private <T extends BaseEntity> List<T> getEntities() {
		List<T> list = new ArrayList<>();
		switch (this.activeTab) {
		case "Publication":
			list = (List<T>) this.publicationBean.getAll();
			break;
		case "User":
			list = (List<T>) this.userBean.getAll();
			break;
		case "Role":
			list = (List<T>) this.roleBean.getAll();
			break;
		case "Publisher":
			list = (List<T>) this.publisherBean.getAll();
			break;
		case "Author":
			list = (List<T>) this.authorBean.getAll();
			break;
		case "Borrow":
			list = (List<T>) this.borrowBean.getAll();
			break;
		case "BorrowLate":
			list = (List<T>) this.borrowBean.getBorrowLate();
			break;
		default:
			break;
		}
		return list;
	}

	/**
	 * Define the Service interface to store the entity list
	 *
	 * @param list
	 *            of entities
	 */
	@SuppressWarnings("unchecked")
	private <T extends BaseEntity> void saveEntities(final List<T> list) {
		switch (this.clazz.getSimpleName()) {
		case "Publication":
			saveEntitiList((IService<T>) this.publicationBean, list);
			break;
		case "User":
			saveEntitiList((IService<T>) this.userBean, list);
			break;
		case "Role":
			saveEntitiList((IService<T>) this.roleBean, list);
			break;
		case "Publisher":
			saveEntitiList((IService<T>) this.publisherBean, list);
			break;
		case "Author":
			saveEntitiList((IService<T>) this.authorBean, list);
			break;
		case "Borrow":
			saveEntitiList((IService<T>) this.borrowBean, list);
			break;
		default:
			break;
		}
	}

	/**
	 * Save entities in database
	 *
	 * @param service
	 *            interface for corresponding type
	 * @param list
	 *            of entities
	 */
	private <T extends BaseEntity> void saveEntitiList(final IService<T> service, final List<T> list) {
		for (final T entity : list) {
			try {
				service.getById(entity.getUuid());
				service.update(entity);
			} catch (IllegalArgumentException e) {
				this.logger.error(e.getMessage());
				this.exceptionHandler.showMessage(e);
				return;
			} catch (final Exception e) {
				try {
					service.store(entity);
				} catch (final Exception e2) {
					this.logger.error(e2.getLocalizedMessage());
					this.exceptionHandler.showMessage(e2);
				}
			}
		}
	}

	private <T extends BaseEntity> void setType(final String type) {
		if (type == null) {
			this.clazz = null;
			return;
		}
		try {
			this.clazz = Class.forName(type);
		} catch (ClassNotFoundException e) {
			this.logger.warn(e.getLocalizedMessage());
		}
	}

	public String getType() {
		if (this.clazz == null) {
			return null;
		} else {
			return this.clazz.getSimpleName();
		}

	}

	/**
	 * TabChange event listener, define the active tab and set type for
	 * import/export
	 *
	 * @param event
	 */
	public void changeTab(final TabChangeEvent event) {
		this.activeTab = event.getTab().getId();
		if ("Delay".equals(this.activeTab)) {
			setType("com.edu.library.model.Borrow");
		} else {
			final String typeName = "com.edu.library.model." + this.activeTab;
			setType(typeName);
		}
	}

}
