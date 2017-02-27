package com.edu.library.data.publicationManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
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
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.filter.BorrowFilter;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

/**
 * CRUD operations of Borrow Entity
 *
 * @author nagys, gallb, kiska
 *
 */
@Stateless
public class BorrowDAO {
	@EJB
	UserDao userBean;

	@EJB
	PublicationBean publicationBean;

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager entityManager;
	private final Logger logger = Logger.getLogger(Borrow.class);

	/**
	 * Returns all borrowings in the database.
	 *
	 * @return - List of borrows
	 */
	public List<Borrow> getAll() throws TechnicalException {
		try {
			return this.entityManager.createNamedQuery("Borrow.findAll", Borrow.class).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Return the borrow in the database by the given parameter {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a borrow
	 * @return - a borrow
	 */
	public Borrow getById(final String id) {
		try {
			return this.entityManager.createNamedQuery("Borrow.findById", Borrow.class).setParameter("uuid", id)
					.getSingleResult();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Save the borrowing in the database by the given parameter {@code borrow}
	 *
	 * @param borrow
	 *            - a borrow type object containing all the necessary
	 *            information for saving a borrow to the DB.
	 */
	public void store(final Borrow borrow) {
		try {
			this.entityManager.persist(borrow);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Update the borrowing in the database by the given parameter
	 * {@code borrowToUpdate}
	 *
	 * @param borrowToUpdate
	 *            - the borrow object on which the update will be done
	 */
	public void update(final Borrow borrowToUpdate) {
		try {
			final Borrow borrow = getById(borrowToUpdate.getUuid());
			if (borrow != null) {
				this.entityManager.merge(borrowToUpdate);
				this.entityManager.flush();
			}
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}

	}

	/**
	 * Remove the borrow from the database by the given parameter {@code borrow}
	 *
	 * @param borrow
	 *            - the unique identifier of the borrow
	 */
	public void remove(final Borrow borrow) {
		try {
			this.entityManager.remove(borrow);
			this.entityManager.flush();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	/**
	 * Searches for all borrowings in the database that match certain criteria
	 * given by {@code filter}
	 *
	 * @param filter
	 *            - a custom filter for borrowings, which represents the fields
	 *            that can be filtered
	 * @return - list of borrowings that match the search criteria
	 */
	public List<Borrow> filterBorrow(final BorrowFilter filter) {

		final CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<Borrow> cq = qb.createQuery(Borrow.class);
		final Root<Borrow> borrow = cq.from(Borrow.class);

		// Constructing list of parameters
		final List<Predicate> predicates = new ArrayList<Predicate>();

		// Adding predicates in case of parameter not being null
		if (filter.getTitle() != null && filter.getTitle().length() >= 3) {
			final List<Publication> publications = this.publicationBean.search(filter.getTitle());
			// search pub
			predicates.add(borrow.get("publication").in(publications));
		}
		if (filter.getUserName() != null && filter.getUserName().length() >= 3) {
			final List<User> users = this.userBean.search(filter.getUserName());
			predicates.add(borrow.get("user").in(users));
		}
		if (filter.getBorrowedFrom() != null) {
			predicates.add(qb.greaterThan(borrow.get("borrowFrom"), filter.getBorrowedFrom()));
		}
		if (filter.getBorrowedUntil() != null) {
			predicates.add(qb.lessThan(borrow.get("borrowUntil"), filter.getBorrowedUntil()));
		}
		// query itself
		cq.select(borrow).where(qb.and(predicates.toArray(new Predicate[] {})));
		// execute query and do something with result
		try {
			return this.entityManager.createQuery(cq).getResultList();
		} catch (final PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Borrow> getBorrwUntilDate(final Date p_date) {
		try {
			return this.entityManager.createNamedQuery("Borrow.findByUntilDate", Borrow.class)
					.setParameter("p_date", p_date).getResultList();
		} catch (PersistenceException e) {
			this.logger.error(e);
			throw new TechnicalException(e);
		}
	}
}
