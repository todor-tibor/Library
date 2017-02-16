/**
 * 
 */
package gallb.wildfly.users.ejb.publicationManagement.business;

import java.util.Date;
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
import model.Publication;
import model.User;

/**
 * @author nagys, gallb
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
			if (p_value.getUser().getLoyaltyIndex() > 0) {
				if (p_value.getPublication().getOnStock() > 0) {
					oEntityManager.persist(p_value);
					Publication tempPublication = p_value.getPublication();
					tempPublication.setOnStock(tempPublication.getOnStock() -1);
					oEntityManager.merge(tempPublication);
					oEntityManager.flush();
					
				}else{
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
		oLogger.info("***********************************delete borrow called");
		try {
			Borrow borrow = getById(p_id);
			Date tmpDate = new Date();
			oLogger.info(tmpDate.toString());
			if (borrow.getBorrowUntil().before(tmpDate)) {
				oLogger.info("***********************************decrease loyalty");
				User tmpUser = borrow.getUser();
				tmpUser.setLoyaltyIndex(tmpUser.getLoyaltyIndex() - 1);
				oEntityManager.merge(tmpUser);
			}
			Publication tempPublication = borrow.getPublication();
			tempPublication.setOnStock(tempPublication.getOnStock() + 1);
			oEntityManager.merge(tempPublication);
			oEntityManager.remove(borrow);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

}
