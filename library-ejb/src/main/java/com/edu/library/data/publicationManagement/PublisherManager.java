package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;

/**
 * crud operations from publisher Entity
 * 
 * @author nagys
 *
 */

@Stateless
@LocalBean
public class PublisherManager {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Publisher.class);

	public List<Publisher> getAll() throws TechnicalException {
		try {
			return oEntityManager.createNamedQuery("Publisher.findAll", Publisher.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Publisher> search(String p_searchTxt) throws TechnicalException {
		try {

			return oEntityManager.createNamedQuery("Publisher.findByName", Publisher.class)
					.setParameter("name", "%" + p_searchTxt + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);

		}
	}

	public Publisher getById(String p_id) throws TechnicalException {
		try {
			return oEntityManager.createNamedQuery("Publisher.findById", Publisher.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}

	public void store(Publisher p_value) throws TechnicalException {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}

	public void update(Publisher p_user) throws TechnicalException {
		try {
			Publisher publisher = getById(p_user.getUuid());
			if (publisher != null) {
				oEntityManager.merge(p_user);
				oEntityManager.flush();
			}

		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}

	public void remove(Publisher publisher) throws TechnicalException {
		try {
			oEntityManager.remove(publisher);
			oEntityManager.flush();

		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}

	}
	
	public Publisher getByName(String p_searchTxt) throws TechnicalException {
		try {
			return oEntityManager.createNamedQuery("Publisher.getByName", Publisher.class)
					.setParameter("name",p_searchTxt).getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

}
