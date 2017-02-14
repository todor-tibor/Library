/**
 * 
 */
package gallb.wildfly.users.ejb.publicationManagement.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IBorrow;
import gallb.wildfly.users.common.IEntity;
import gallb.wildfly.users.ejb.exception.EjbException;
import model.Borrow;

/**
 * @author nagys
 *
 */
@Stateless
public class BorrowManager implements IBorrow {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Borrow.class);

	@Override
	public List<Borrow> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Borrow.findAll", Borrow.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	@Override
	public List<Borrow> search(String p_searchTxt) throws EjbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrow getById(String p_id) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Borrow.findById", Borrow.class).setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public void store(Borrow p_value) throws EjbException {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();

		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	@Override
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

	@Override
	public void remove(String p_id) throws EjbException {
		try {
			Borrow borrow = getById(p_id);
			oEntityManager.remove(borrow);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

}
