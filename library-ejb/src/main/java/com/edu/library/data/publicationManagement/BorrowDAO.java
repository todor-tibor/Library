/**
 * 
 */
package com.edu.library.data.publicationManagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.model.Borrow;

/**
 * @author nagys, gallb
 *
 */
@Stateless
public class BorrowDAO {

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
}
