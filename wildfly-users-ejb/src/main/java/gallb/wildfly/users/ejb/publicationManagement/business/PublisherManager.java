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

import gallb.wildfly.users.common.IPublisher;
import gallb.wildfly.users.ejb.exception.EjbException;
import model.Publisher;

/**
 * crud operations from publisher Entity
 * @author nagys
 *
 */

@Stateless
public class PublisherManager implements IPublisher {
	@PersistenceContext(unitName = "WildflyUsers")

	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Publisher.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Publisher> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publisher.findAll").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Publisher> search(String p_searchTxt) throws EjbException {
		try {

			return oEntityManager.createNamedQuery("Publisher.findByName").setParameter("name", "%" + p_searchTxt + "%")
					.getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);

		}
	}

	@Override
	public Publisher getById(String p_id) throws EjbException {
		try {
			return (Publisher) oEntityManager.createNamedQuery("Publisher.findById").setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	@Override
	public void store(Publisher p_value) throws EjbException {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

	@Override
	public void update(Publisher p_user) throws EjbException {
		try {
			Publisher publisher = getById(p_user.getUuid());
			if (publisher != null) {
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
			Publisher publisher = getById(p_id);
			oEntityManager.remove(publisher);
			oEntityManager.flush();

		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}

	}

}
