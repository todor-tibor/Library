/**
 * 
 */
package com.edu.library.data.publicationManagement;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;
import com.edu.library.util.EjbException;

/**
 * @author nagys, gallb
 *
 */
@Stateless
public class BorrowDAO {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Borrow.class);

	public List<Borrow> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Borrow.findAll", Borrow.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	public List<Borrow> search(String p_searchTxt) throws EjbException {
		// TODO Auto-generated method stub
		return null;
	}

	public Borrow getById(String p_id) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Borrow.findById", Borrow.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	public void store(Borrow p_value) throws EjbException {
		try {
			if (p_value.getUser().getLoyaltyIndex() > 0) {
				if (p_value.getPublication().getOnStock() > 0) {
					oEntityManager.persist(p_value);
					Publication tempPublication = p_value.getPublication();
					tempPublication.setOnStock(tempPublication.getOnStock() - 1);
					oEntityManager.merge(tempPublication);
					oEntityManager.flush();

				} else {
					oLogger.info("Publication not on stock");
					throw new EjbException("Publication not on stock");
				}
			} else {
				oLogger.info("Trust index to low");
				throw new EjbException("Trust index to low");
			}

		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	public void update(Borrow p_user) throws EjbException {
		try {
			Borrow borrow = getById(p_user.getUuid());
			if (borrow != null) {
				oEntityManager.merge(p_user);
				oEntityManager.flush();
			}
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	public void remove(Borrow p_borrow) throws EjbException {
		oLogger.info("delete borrow called on entity: " + p_borrow.toString());
		try {
			oEntityManager.refresh(p_borrow);
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}
}