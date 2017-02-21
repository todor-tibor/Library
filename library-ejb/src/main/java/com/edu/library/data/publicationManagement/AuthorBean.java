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
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(AuthorBean.class);

	public List<Author> getAll() {
		try {
			return oEntityManager.createNamedQuery("Author.findAll", Author.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public List<Author> search(String p_searchTxt) {
		try {
			return oEntityManager.createNamedQuery("Author.searchByName", Author.class)
					.setParameter("name", "%" + p_searchTxt + "%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public Author getById(String p_id) {
		try {
			return (Author) oEntityManager.createNamedQuery("Author.getById", Author.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public Author getByname(String p_value) {
		try {
			return (Author) oEntityManager.createNamedQuery("Author.getByName", Author.class)
					.setParameter("name", p_value).getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void store(Author p_value) {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void update(Author p_user) {
		try {
			oEntityManager.merge(p_user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

	public void remove(Author author) {
		try {
			oEntityManager.remove(author);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new TechnicalException(e);
		}
	}

}
