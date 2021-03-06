package com.edu.library;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

import com.edu.library.model.BaseEntity;
import com.edu.library.model.Role;
import com.edu.library.model.util.JAXB;
import com.edu.library.model.util.JpaException;
import com.edu.library.porting.IPdfExporter;
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
	private IPdfExporter pdfExporter;

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

	private String pdfName;
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
	 * Export entities from database (specified by type) to ".xml" extension
	 * file using JAXB.
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> void exportList() {
		try {
			final List<T> entities = getEntities();
			// JAXB.marshall(entities, this.clazz, this.activeTab);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + this.activeTab + ".xml");
			httpServletResponse.setContentType("text/xml");
			JAXB.marshall(entities, this.clazz, httpServletResponse.getOutputStream());
			this.message.info("export.done");
			this.logger.info("exported " + entities.size() + " element");
		} catch (final JpaException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		} catch (IOException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		} finally {
			FacesContext.getCurrentInstance().responseComplete();
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
			} catch (final IllegalArgumentException e) {
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
		} catch (final ClassNotFoundException e) {
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

	/**
	 * Invoke PDF exporter method for corresponding class
	 */
	public void savePDF() {
		switch (this.activeTab) {
		case "Publication":
			this.pdfExporter.writePublications();
			break;
		case "User":
			this.pdfExporter.writeUsers();
			break;
		case "Role":
			this.pdfExporter.writeRole();
			break;
		case "Publisher":
			this.pdfExporter.writePublishers();
			break;
		case "Author":
			this.pdfExporter.writeAuthors();
			break;
		case "Borrow":
			this.pdfExporter.writeBorrows();
			break;
		case "BorrowLate":
			// list = (List<T>) this.borrowBean.getBorrowLate();
			break;
		default:
			break;
		}
		this.pdfName = this.pdfExporter.getPdfName();
	}

	/**
	 * Using for select file from file system
	 */
	private UploadedFile file;

	public UploadedFile getFile() {
		return this.file;
	}

	public void setFile(final UploadedFile file) {
		this.file = file;
	}

	/**
	 * Import list of entities (specified by type) from ".xml" extension file
	 * using JAXB and store them in database
	 *
	 * @return - list of entities imported from file.
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> void importList() {
		System.out.println(this.file.getFileName() + " is uploaded.");
		if (this.file != null) {
			try {
				final List<T> list = JAXB.unmarshallList(this.clazz, this.file.getInputstream());
				saveEntities(list);
				this.message.info("import.done");
				this.logger.info("imported " + list.size() + " element");
				// FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
			} catch (JpaException | IOException e) {
				this.logger.error(e.getMessage());
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Checks whether the PDF file was written.
	 *
	 */
	public boolean isWritten() {
		return this.pdfName != null;
	}

	/**
	 * Returns the name of the PDF file that was written.
	 *
	 * @return -String name
	 */
	public String getData() {
		return "http://localhost:8080/" + this.pdfName + ".pdf";
	}
}
