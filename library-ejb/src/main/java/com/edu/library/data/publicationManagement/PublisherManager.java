package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Publisher;

/**
 * CRUD operations of Publisher Entity
 *
 * @author nagys
 *
 */

@Stateless
@LocalBean
public class PublisherManager {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager entityManager;
	private final Logger logger = Logger.getLogger(Publisher.class);

	/**
	 * Returns all publishers in the database.
	 *
	 * @return - List of publishers
	 */
	public List<Publisher> getAll() {
		try {
			return this.entityManager.createNamedQuery("Publisher.findAll", Publisher.class).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches for a publisher in the database by the given parameter
	 * {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the publisher to search for
	 * @return - List with all the publishers that match the search criteria
	 */
	public List<Publisher> search(final String searchText) {
		try {

			return this.entityManager.createNamedQuery("Publisher.findByName", Publisher.class)
					.setParameter("name", "%" + searchText + "%").getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);

		}
	}

	/**
	 * Return the publisher in the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a publisher
	 * @return - a publisher
	 */

	public Publisher getById(final String id) {
		try {
			return this.entityManager.createNamedQuery("Publisher.findById", Publisher.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Save the publisher in the database by the given parameter
	 * {@code publisher}
	 *
	 * @param publisher
	 *            - a publisher type object containing all the necessary
	 *            information for saving a publisher to the DB.
	 */
	public void store(final Publisher publisher) {
		try {
			this.entityManager.persist(publisher);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Update the publisher in the database by the given parameter
	 * {@code publisher}
	 *
	 * @param publisher
	 *            - the publisher object on which the update will be done
	 */
	public void update(final Publisher updatePublisher) {
		try {
			final Publisher publisher = getById(updatePublisher.getUuid());
			if (publisher != null) {
				this.entityManager.merge(updatePublisher);
				this.entityManager.flush();
			}

		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Remove the publisher from the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the publisher
	 */
	public void remove(final Publisher publisher) {
		try {
			this.entityManager.remove(publisher);
			this.entityManager.flush();

		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}

	}

}
