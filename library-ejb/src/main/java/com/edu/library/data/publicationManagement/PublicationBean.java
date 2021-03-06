package com.edu.library.data.publicationManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;

/**
 * CRUD operations of Publication Entity
 *
 * @author sipost
 *
 */

@Stateless
@LocalBean
public class PublicationBean {

	@EJB
	PublisherManager publisherBean;

	@EJB
	AuthorBean authorBean;

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager entityManager;
	private final Logger logger = Logger.getLogger(PublicationBean.class);

	/**
	 * Returns all publications in the database.
	 *
	 * @return - List of publications
	 */

	public List<Publication> getAll() {
		try {
			return this.entityManager.createNamedQuery("Publication.findAll", Publication.class).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Lists of publications that is in the database and contains the
	 * {@code searchTxt} input string
	 *
	 * @param searchTxt
	 *            - String to search for
	 * @return List of entities found, empty list if nothing found.
	 */
	public List<Publication> search(final String title, final int start, final int fin) {
		try {
			return this.entityManager.createNamedQuery("Publication.searchByName", Publication.class)
					.setParameter("title", "%" + title + "%").setFirstResult(start).setMaxResults(fin).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches for a publication in the database by the given parameter
	 * {@code searchText}
	 *
	 * @param searchText
	 *            - the title or part of the title of the publication to search
	 *            for
	 * @return - List with all the publications that match the search criteria
	 */
	public List<Publication> search(final String title) {
		try {
			return this.entityManager.createNamedQuery("Publication.searchByName", Publication.class)
					.setParameter("title", "%" + title + "%").getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches the publication's content in the database by the given parameter
	 * {@code searchText}
	 *
	 * @param searchText
	 *            - the text to search for in the content
	 * @param start
	 *            - start row number
	 * @param fin
	 *            - number of data per page
	 * @return List of publications that match the criteria, empty list if there
	 *         was no match
	 */
	public List<Publication> searchContent(final String content, final int start, final int fin) {
		try {
			return this.entityManager.createNamedQuery("Publication.searchContent", Publication.class)
					.setParameter("title", "%" + content + "%").setFirstResult(start).setMaxResults(fin)
					.getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the publication in the database by the given parameter
	 * {@code publication}
	 *
	 * @param publication
	 *            - the title of a publication
	 * @return - a publication
	 */
	public Publication getByName(final String publication) {
		try {
			return this.entityManager.createNamedQuery("Publication.getByName", Publication.class)
					.setParameter("title", publication).getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the publication in the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a publication
	 * @return - a publication
	 */
	public Publication getById(final String id) {
		try {
			return this.entityManager.createNamedQuery("Publication.getById", Publication.class)
					.setParameter("uuid", id).getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Save the publication in the database by the given parameter
	 * {@code publication}
	 *
	 * @param publication
	 *            - a publication type object containing all the necessary
	 *            information for saving a publication to the DB.
	 */
	public void store(final Publication publication) {
		try {
			this.entityManager.persist(publication);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Update the publication in the database by the given parameter
	 * {@code publication}
	 *
	 * @param publication
	 *            - the publication object on which the update will be done
	 */
	public void update(final Publication publication) {
		try {
			this.entityManager.merge(publication);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Remove the publication from the database by the given parameter
	 * {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the publication
	 */
	public void remove(final Publication pub) {
		try {
			this.entityManager.remove(pub);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Lists all borrowings from the database that a user has, given by the
	 * {@code title}
	 *
	 * @param title
	 *            - the title of the publication
	 * @return - list of borrowings
	 */
	public List<Borrow> getBorrow(final String title) {

		try {
			final List<Borrow> u = this.entityManager.createNamedQuery("Publication.findBorrow", Borrow.class)
					.setParameter("title", "%" + title + "%").getResultList();
			return u;
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Filters the data specified by the {@code filter} object. This can have
	 * one or more filters set. For example: publication title, publisher etc.
	 *
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @param start
	 *            -start row number
	 * @param fin
	 *            -number of data per page
	 * @return List of Publications
	 */
	public List<Publication> filterPublication(final PublicationFilter filter, final int start, final int fin) {
		final CriteriaQuery<Publication> cq = makeFilter(filter);
		try {
			return this.entityManager.createQuery(cq).setFirstResult(start).setMaxResults(fin).getResultList();
		} catch (final PersistenceException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Lists Publications that are in the database.
	 *
	 * @param start
	 *            - start row number
	 * @param fin
	 *            - number of data per page
	 * @return List containing all entities.
	 */
	public List<Publication> getAll(final int start, final int fin) {
		try {
			return this.entityManager.createNamedQuery("Publication.findAll", Publication.class).setFirstResult(start)
					.setMaxResults(fin).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Count publications that is in the database.
	 *
	 * @return Number of Publications found
	 */
	public long countAll() {
		try {
			return this.entityManager.createNamedQuery("Publication.countAll", Long.class).getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Count publications that is in the database and contains the
	 * {@code searchTxt} input string
	 *
	 * @param title
	 *            - String to search for
	 * @return Number of Publications found
	 */
	public long countSearch(final String title) {
		try {
			return this.entityManager.createNamedQuery("Publication.countSearch", Long.class)
					.setParameter("title", "%" + title + "%").getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Count the data specified by the {@code filter} object. This can have one
	 * or more filters set. For example: publication title, publisher etc.
	 *
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @return Number of Publications filtered
	 */
	public long countFilter(final PublicationFilter filter) {
		final CriteriaQuery<Publication> cq = makeFilter(filter);
		try {
			return this.entityManager.createQuery(cq).getResultList().size();
		} catch (final PersistenceException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Create query by filter
	 *
	 * @param filter
	 * @return
	 */
	private CriteriaQuery<Publication> makeFilter(final PublicationFilter filter) {
		final CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<Publication> cq = qb.createQuery(Publication.class);
		final Root<Publication> publication = cq.from(Publication.class);

		// Constructing list of parameters
		final List<Predicate> predicates = new ArrayList<Predicate>();

		// Adding predicates in case of parameter not being null
		if (filter.getTitle() != null && filter.getTitle().length() >= 3) {
			predicates.add(qb.like(publication.get("title"), "%" + filter.getTitle() + "%"));
		}
		if (filter.getPublisher() != null && filter.getPublisher().length() >= 3) {
			final List<Publisher> publishers = this.publisherBean.search(filter.getPublisher());
			// publication.get("authors").in(filter.getAuthor());
			predicates.add(publication.get("publisher").in(publishers));
		}
		if (filter.isOnStock() == true) {
			predicates.add(qb.greaterThan(publication.get("onStock"), 0));
		}
		if (filter.getFrom() != null) {
			if (filter.getUntil() == null) {
				filter.setUntil(new Date());
			}
			predicates.add(qb.between(publication.get("publicationDate"), filter.getFrom(), filter.getUntil()));
		}
		// query itself
		cq.select(publication).where(qb.and(predicates.toArray(new Predicate[] {})));
		// execute query and do something with result
		return cq;
	}

}
