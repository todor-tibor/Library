/**
 * 
 */
package gallb.wildfly.users.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.BeanException;
import gallb.wildfly.users.common.IPublisher;
import model.Publisher;

/**
 * @author nagys
 *
 */
public class PublisherManager implements IPublisher {
	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(UserBean.class);

	/**
	 * get all Publisher
	 * 
	 * @return Publisher list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Publisher> getAll() {
		try {
			return (List<Publisher>) oEntityManager.createNamedQuery("").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find Publisher", e);
		}
	}

	@Override
	public List<Publisher> search(String p_searchTxt) {
		try {
			CriteriaBuilder builder = oEntityManager.getCriteriaBuilder();
			CriteriaQuery<Publisher> criteriaQuery = builder.createQuery(Publisher.class);
			Root<Publisher> root = criteriaQuery.from(Publisher.class);
			criteriaQuery.select(root).where(builder.like(root.get("publisher"), "%" + p_searchTxt + "%"));
			return oEntityManager.createQuery(criteriaQuery).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find any publisher.", e);

		}
	}

	@Override
	public Publisher getById(int p_id) {
		try {
			return (Publisher) oEntityManager.createNamedQuery("Publisher.findById").setParameter("uuid", p_id)
					.getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find publisher with specifiel id: <" + p_id + ">", e);
		}

	}

	@Override
	public void store(Publisher p_value) {
		try {
			

		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("--------Could not insert publisher.", e);
		}

	}

	@Override
	public void update(Publisher p_user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(int p_id) {
		// TODO Auto-generated method stub

	}

}
