package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.model.Publication;
import com.edu.library.util.EjbException;

/**
 * CRUD operations from Publication Entity
 * 
 * @author sipost
 *
 */

@Stateless
@LocalBean
public class PublicationBean {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Publication.class);

	public List<Publication> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.findAll", Publication.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public List<Publication> search(String p_searchTxt) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.searchByName", Publication.class)
					.setParameter("title", "%" + p_searchTxt + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public Publication getByName(String p_searchTxt) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.getByName", Publication.class)
					.setParameter("title",p_searchTxt).getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public Publication getById(String p_id) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.getById", Publication.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void store(Publication p_value) throws EjbException {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void update(Publication p_user) throws EjbException {
		try {
			oEntityManager.merge(p_user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void remove(Publication pub) throws EjbException {
		try {
			oEntityManager.remove(pub);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

}
