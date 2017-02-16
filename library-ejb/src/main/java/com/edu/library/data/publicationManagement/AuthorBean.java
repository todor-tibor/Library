package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.util.EjbException;

import model.Author;

/**
 * crud operations from author Entity
 * 
 * @author sipost
 *
 */
@Stateless
public class AuthorBean {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Author.class);

	
	public List<Author> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Author.findAll",Author.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public List<Author> search(String p_searchTxt) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Author.searchByName",Author.class).setParameter("name", "%" + p_searchTxt + "%")
					.getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public Author getById(String p_id) throws EjbException {
		try {
			return (Author) oEntityManager.createNamedQuery("Author.getById",Author.class).setParameter("uuid", p_id)
					.getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public void store(Author p_value) throws EjbException {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public void update(Author p_user) throws EjbException {
		try {
			Author r = oEntityManager.find(Author.class, p_user.getUuid());
			if (r != null) {
				oEntityManager.merge(p_user);
				oEntityManager.flush();
			}
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	
	public void remove(String p_id) throws EjbException {
		try {
			Author r = oEntityManager.find(Author.class, p_id);
			oEntityManager.remove(r);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

}
