package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.model.util.ReadXMLFile;
import com.edu.library.model.util.WriteXMLFile;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 *
 * Publication manager.
 *
 * @author sipost
 * @author kiska
 *
 */
@Named("publicationBean")
@SessionScoped
public class PublicationMB implements Serializable {

	private final Logger logger = Logger.getLogger(PublicationMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	private IPublicationService publicationBean;

	@Inject
	ExceptionHandler exceptionHandler;
	@Inject
	MessageService message;

	PublicationFilter filter = new PublicationFilter();
	private Date date = new Date();

	/**
	 * Variables to select publication type, authors or publisher for update and
	 * insert
	 */
	private List<Author> authors, currentAuthors;
	private Publisher currentPublisher;
	private String type;
	private String function = "all";
	private String title;
	private LazyDataModel<Publication> lazyModel;
	/**
	 * Currently displayed publications.
	 */
	private List<Publication> publicationList = new ArrayList<>();

	/**
	 * Currently selected publication.
	 */
	private Publication currentPublication = null;

	/**
	 * Initialize dataTable with first page
	 */
	@PostConstruct
	public void init() {
		getAllPaginate();
	}

	/**
	 * Requests all publication objects and stores them in
	 * {@code publicationList}.
	 *
	 * @return List of all publications from database.
	 */
	public List<Publication> getAll() {
		this.publicationList.clear();
		try {
			this.publicationList = this.publicationBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.publicationList;
	}

	/**
	 * Requests publication objects with pagination.
	 */
	public void getAllPaginate() {
		try {
			this.function = "all";
			this.lazyModel = new PublicationLazyModel();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Search for publication by title with pagination
	 *
	 * @param searchTxt
	 *            publication title.
	 */
	public void search(final String searchTxt) {
		if (searchTxt.length() >= 3) {
			this.title = searchTxt;
			try {
				this.function = "search";
				this.lazyModel = new PublicationLazyModel();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.warn("managedbean.string");
		}
	}

	/**
	 * Insert new Book, Magazine or Newspaper uses {@code currentAuthors} and
	 * {@code currentPublisher}
	 *
	 * @param p_title-new
	 *            title
	 * @param p_nrOfCopies-number
	 *            of copies left, and copies on stock
	 */
	public void store(final String pTitle, final String pNrOfCopies) {
		if (pTitle.isEmpty() || pNrOfCopies.isEmpty()) {
			this.message.warn("managedbean.required");
			return;
		}
		int nrOfCopies;
		try {
			nrOfCopies = Integer.parseInt(pNrOfCopies);
		} catch (final NumberFormatException e) {
			this.message.warn("managedbean.numberFormatExeption");
			return;
		}
		Publication publication;
		switch (this.type) {
		case "Book":
			publication = new Book();
			if (this.currentAuthors == null) {
				this.message.warn("managedbean.required");
				return;
			}
			((Book) publication).setAuthors(this.currentAuthors);
			break;
		case "Magazine":
			publication = new Magazine();
			if (this.currentAuthors == null) {
				this.message.warn("managedbean.required");
				return;
			}
			((Magazine) publication).setAuthors(this.currentAuthors);
			break;
		default:
			publication = new Newspaper();
			break;
		}
		publication.setTitle(pTitle);
		publication.setNrOfCopys(nrOfCopies);
		publication.setOnStock(nrOfCopies);
		publication.setPublisher(this.currentPublisher);
		final Calendar c = Calendar.getInstance();
		c.setTime(this.date);
		c.add(Calendar.DATE, 1);
		this.date = c.getTime();
		publication.setPublicationDate(this.date);

		try {
			this.publicationBean.store(publication);
			this.publicationList.add(publication);
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Update currently selected publication. Uses {@code currentPublication}
	 * and currently selected authors
	 */
	public void update() {
		if ((this.currentPublication != null) && this.currentPublication.getTitle() != null
				&& this.currentPublication.getPublisher() != null) {
			if (this.currentPublication instanceof Book) {
				if (this.authors != null && !this.authors.isEmpty()) {
					((Book) this.currentPublication).setAuthors(this.authors);
				} else {
					this.message.warn("managedbean.required");
					return;
				}
			}
			if ("Magazine".equals(this.currentPublication.getClass().getSimpleName())) {
				if (this.authors != null && !this.authors.isEmpty()) {
					((Magazine) this.currentPublication).setAuthors(this.authors);
				} else {
					this.message.warn("managedbean.required");
					return;
				}
			}
			try {
				this.publicationBean.update(this.currentPublication);
				this.publicationList = this.publicationBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.warn("managedbean.required");
		}
	}

	/**
	 * Deletes currently selected publication from database.
	 */
	public void remove() {
		if (this.currentPublication == null) {
			this.message.error("managedbean.empty");
		} else {
			try {
				this.publicationBean.remove(this.currentPublication.getUuid());
				this.publicationList = this.publicationBean.getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}

	}

	/**
	 * Filter publication with pagination
	 *
	 */
	public void filterPublication() {
		try {
			this.function = "filter";
			this.lazyModel = new PublicationLazyModel();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Check if there is selected publication
	 *
	 * @return - true if it is, false otherwise
	 */
	public Boolean isSelected() {
		if (this.currentPublication == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check if the Publication selected has authors property(the Newspaper
	 * can't have any authors)
	 *
	 * @return - true if it is, false otherwise
	 */
	public Boolean hasAuthor() {
		if (this.currentPublication == null) {
			return false;
		}
		if (this.currentPublication instanceof Newspaper) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Return the authors of the current publication
	 *
	 * @return List<Authors>
	 */
	public List<Author> getAuthors() {
		if (this.authors != null) {
			this.authors.clear();
		}
		if (this.currentPublication instanceof Book) {
			this.authors = ((Book) this.currentPublication).getAuthors();
		}
		if (this.currentPublication instanceof Magazine) {
			this.authors = ((Magazine) this.currentPublication).getAuthors();
		}
		return this.authors;
	}

	/**
	 * Getters and setters for private variables
	 */

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setAuthors(final List<Author> authors) {
		this.authors = authors;
	}

	public List<Author> getCurrentAuthors() {
		return this.currentAuthors;
	}

	public void setCurrentAuthors(final List<Author> currentAuthors) {
		this.currentAuthors = currentAuthors;
	}

	public Publisher getCurrentPublisher() {
		return this.currentPublisher;
	}

	public void setCurrentPublisher(final Publisher currentPublisher) {
		this.currentPublisher = currentPublisher;
	}

	public List<Publication> getPublicationList() {
		return this.publicationList;
	}

	public Publication getCurrentPublication() {
		return this.currentPublication;
	}

	public void setCurrentPublication(final Publication currentPublication) {
		this.currentPublication = currentPublication;
	}

	public PublicationFilter getFilter() {
		return this.filter;
	}

	public void setFilter(final PublicationFilter filter) {
		this.filter = filter;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	/**
	 * Export publications to ".xml" extension.
	 */
	public void exportPublication() {
		WriteXMLFile.exportData(getAll(), "publications");
		this.message.info("export.done");
	}

	/**
	 * Import publications from ".xml" extension file.
	 *
	 * @return - list of publications imported from file.
	 */
	public void importPublication() {
		this.publicationList = ReadXMLFile.importData("publications");
		for (final Publication p : this.publicationList) {
			try {
				this.publicationBean.getById(p.getUuid());
			} catch (final Exception e) {
				this.publicationBean.store(p);
			}
			try {
				this.publicationBean.update(p);
			} catch (final NoResultException e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Lazy model for pagination
	 *
	 * @author sipost
	 *
	 */
	private class PublicationLazyModel extends LazyDataModel<Publication> {
		private static final long serialVersionUID = -7040989400223372462L;
		private List<Publication> data = new ArrayList<>();

		@Override
		public Publication getRowData(final String rowKey) {
			for (Publication Publication : this.data) {
				if (Publication.getUuid().equals(rowKey))
					return Publication;
			}
			return null;
		}

		@Override
		public Object getRowKey(final Publication Publication) {
			return Publication.getUuid();
		}

		@Override
		public List<Publication> load(final int first, final int pageSize, final String sortField,
				final SortOrder sortOrder, final Map<String, Object> filters) {
			int dataSize = 0;
			if ("search".equals(PublicationMB.this.function)) {
				try {
					dataSize = (int) (PublicationMB.this.publicationBean.countSearch(PublicationMB.this.title));
				} catch (Exception e) {
					return this.data;
				}
				this.setRowCount(dataSize);
				this.data = PublicationMB.this.publicationBean.search(PublicationMB.this.title, first, pageSize);
			} else if ("filter".equals(PublicationMB.this.function)) {
				try {
					dataSize = (int) (PublicationMB.this.publicationBean.countFilter(PublicationMB.this.filter));
				} catch (Exception e) {
					return this.data;
				}
				this.setRowCount(dataSize);
				this.data = PublicationMB.this.publicationBean.filterPublication(PublicationMB.this.filter, first,
						pageSize);
			} else {
				try {
					dataSize = (int) (PublicationMB.this.publicationBean.countAll());
				} catch (Exception e) {
					return this.data;
				}
				this.setRowCount(dataSize);
				this.data = PublicationMB.this.publicationBean.getAll(first, pageSize);
			}
			return this.data;
		}
	}

	public LazyDataModel<Publication> getLazyModel() {
		return this.lazyModel;
	}

	public void setLazyModel(final LazyDataModel<Publication> lazyModel) {
		this.lazyModel = lazyModel;
	}

}
