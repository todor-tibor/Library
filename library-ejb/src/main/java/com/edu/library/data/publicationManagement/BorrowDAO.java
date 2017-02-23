package com.edu.library.data.publicationManagement;

/**
 * 
 */

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.model.User;

/**
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
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Borrow.class);

	public List<Borrow> getAll() throws TechnicalException {
		try {
			return oEntityManager.createNamedQuery("Borrow.findAll", Borrow.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}

	public Borrow getById(String p_id) {
		try {
			return oEntityManager.createNamedQuery("Borrow.findById", Borrow.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void store(Borrow p_value) {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void update(Borrow p_entity) {
		try {
			Borrow borrow = getById(p_entity.getUuid());
			if (borrow != null) {
				oEntityManager.merge(p_entity);
				oEntityManager.flush();
			}
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}

	public void remove(Borrow p_borrow) {
		oLogger.info("delete borrow called on entity: " + p_borrow.getUuid());
		try {
			oEntityManager.remove(p_borrow);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Borrow> filterBorrow(BorrowFilter filter) {

		CriteriaBuilder qb = oEntityManager.getCriteriaBuilder();
		CriteriaQuery<Borrow> cq = qb.createQuery(Borrow.class);
		Root<Borrow> borrow = cq.from(Borrow.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		// Adding predicates in case of parameter not being null
		if (filter.getTitle() != null && filter.getTitle().length() >= 3) {
			List<Publication> publications = publicationBean.search(filter.getTitle());
			oLogger.warn("-----title: " + filter.getTitle());
			//search pub
			predicates.add(borrow.get("publication").in(publications));
		}
		if (filter.getUserName() != null && filter.getUserName().length() >= 3) {
			List<User> users = userBean.search(filter.getUserName());
			oLogger.warn("-------publisher.name: " + filter.getUserName());
			// publication.get("authors").in(filter.getAuthor());
			predicates.add(borrow.get("user").in(users));
		}
		if (filter.getBorrowedFrom() != null) {
			oLogger.warn("-----from: " + filter.getBorrowedFrom() + " until: " + filter.getBorrowedUntil());
			predicates.add(qb.greaterThan(borrow.get("borrowFrom"), filter.getBorrowedFrom()));
		}
		if (filter.getBorrowedUntil() != null) {
			oLogger.warn("-----from: " + filter.getBorrowedUntil() + " until: " + filter.getBorrowedUntil());
			predicates.add(qb.lessThan(borrow.get("borrowUntil"), filter.getBorrowedUntil()));
		}
		/*
		 * if (filter.getAuthor() != null && filter.getAuthor().length() > 3) {
		 * List<Author> authors = authorBean.search(filter.getAuthor());
		 * predicates.add(borrow.get("authors").in(authors)); }
		 */
		// query itself
		cq.select(borrow).where(qb.and(predicates.toArray(new Predicate[] {})));
		// execute query and do something with result
		try {
			return oEntityManager.createQuery(cq).getResultList();
		} catch (PersistenceException e) {
			throw new TechnicalException(e);
		}
	}
}
