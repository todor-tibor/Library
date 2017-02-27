package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Author;

/**
 * CRUD operations of Author Entity
 *
 * @author sipost
 *
 */
@Stateless
public class AuthorBean {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager entityManager;
	private final Logger logger = Logger.getLogger(AuthorBean.class);

	/**
	 * Returns all authors in the database.
	 *
	 * @return - List of authors
	 */
	public List<Author> getAll() {
		try {
			return this.entityManager.createNamedQuery("Author.findAll", Author.class).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches for an author in the database by the given parameter
	 * {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the author to search for
	 * @return - List with all the authors that match the search criteria
	 */
	public List<Author> search(final String searchText) {
		try {
			return this.entityManager.createNamedQuery("Author.searchByName", Author.class)
					.setParameter("name", "%" + searchText + "%").getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the author in the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of an author
	 * @return - an author
	 */
	public Author getById(final String id) {
		try {
			return this.entityManager.createNamedQuery("Author.getById", Author.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Return the author in the database by the given parameter {@code author}
	 *
	 * @param author
	 *            - the unique name of an author
	 * @return - an author
	 */
	public Author getByname(final String author) {
		try {
			return this.entityManager.createNamedQuery("Author.getByName", Author.class).setParameter("name", author)
					.getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Save the author in the database by the given parameter {@code author}
	 *
	 * @param author
	 *            - an Author type object containing all the necessary
	 *            information for saving an author to the DB.
	 */
	public void store(final Author author) {
		try {
			this.entityManager.persist(author);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Update the author in the database by the given parameter {@code author}
	 *
	 * @param author
	 *            - the author object on which the update will be done
	 */
	public void update(final Author author) {
		try {
			this.entityManager.merge(author);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Remove the author from the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the author
	 */
	public void remove(final Author author) {
		try {
			this.entityManager.remove(author);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

}
