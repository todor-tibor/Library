package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;

/**
 * CRUD operations of Publication Entity
 * 
 * @author sipost
 *
 */

@Stateless
@LocalBean
public class PublicationBean {

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
	
	public List<Borrow> getBorrow(String title){

		try {
			List<Borrow> u = oEntityManager.createNamedQuery("Publication.findBorrow", Borrow.class).setParameter("title", title)
					.getResultList();
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

}
