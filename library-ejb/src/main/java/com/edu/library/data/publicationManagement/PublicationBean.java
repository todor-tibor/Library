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
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(PublicationBean.class);

	public List<Publication> getAll() {
		try {
			return oEntityManager.createNamedQuery("Publication.findAll", Publication.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Publication> search(String p_searchTxt) {
		try {
			return oEntityManager.createNamedQuery("Publication.searchByName", Publication.class)
					.setParameter("title", "%" + p_searchTxt + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public Publication getByName(String p_searchTxt) {
		try {
			return oEntityManager.createNamedQuery("Publication.getByName", Publication.class)
					.setParameter("title", p_searchTxt).getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public Publication getById(String p_id) {
		try {
			return oEntityManager.createNamedQuery("Publication.getById", Publication.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void store(Publication p_value) {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void update(Publication p_user) {
		oLogger.info("-------------" + p_user);
		try {
			oEntityManager.merge(p_user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void remove(Publication pub) {
		try {
			oEntityManager.remove(pub);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Borrow> getBorrow(String title) {

		try {
			List<Borrow> u = oEntityManager.createNamedQuery("Publication.findBorrow", Borrow.class)
					.setParameter("title", "%" + title + "%").getResultList();
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Publication> filterPublication(PublicationFilter filter) {

		CriteriaBuilder qb = oEntityManager.getCriteriaBuilder();
		CriteriaQuery<Publication> cq = qb.createQuery(Publication.class);
		Root<Publication> publication = cq.from(Publication.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		// Adding predicates in case of parameter not being null
		if (filter.getTitle() != null && filter.getTitle().length() >= 3) {
			oLogger.warn("-----title: " + filter.getTitle());
			predicates.add(qb.like(publication.get("title"), "%" + filter.getTitle() + "%"));
		}
		if (filter.getPublisher() != null && filter.getPublisher().length() >= 3) {
			List<Publisher> publishers = publisherBean.search(filter.getPublisher());
			oLogger.warn("-------publisher.name: " + filter.getPublisher());
			// publication.get("authors").in(filter.getAuthor());
			predicates.add(publication.get("publisher").in(publishers));
		}
		if (filter.isOnStock() == true) {
			oLogger.warn("--------onStock: " + filter.isOnStock());
			predicates.add(qb.greaterThan(publication.get("onStock"), 0));
		}
		if (filter.getFrom() != null) {
			oLogger.warn("-----from: " + filter.getFrom());
			if (filter.getUntil() == null) {
				filter.setUntil(new Date());
				oLogger.warn("-----until: " + filter.getUntil());
			}
			predicates.add(qb.between(publication.get("publicationDate"), filter.getFrom(), filter.getUntil()));
		}
		/*if (filter.getAuthor() != null && filter.getAuthor().length() > 3) {
			List<Author> authors = authorBean.search(filter.getAuthor());
			predicates.add(publication.get("authors").in(authors));
		}*/
		// query itself
		cq.select(publication).where(qb.and(predicates.toArray(new Predicate[] {})));
		// execute query and do something with result
		try {
			return oEntityManager.createQuery(cq).getResultList();
		} catch (PersistenceException e) {
			throw new TechnicalException(e);
		}
	}

}
