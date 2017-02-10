package gallb.wildfly.users.ejb.publicationManagement.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IPublication;
import gallb.wildfly.users.ejb.exception.EjbException;
import model.Publication;

/**
 * CRUD operations from Publication Entity
 * 
 * @author sipost
 *
 */

@Stateless
public class PublicationBean implements IPublication {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Publication.class);

	@Override
	public List<Publication> getAll() throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.findAll",Publication.class).getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public List<Publication> search(String p_searchTxt) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.searchByName",Publication.class).setParameter("title","%"+ p_searchTxt+"%").getResultList();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public Publication getById(String p_id) throws EjbException {
		try {
			return oEntityManager.createNamedQuery("Publication.getById",Publication.class).setParameter("uuid",p_id).getSingleResult();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public void store(Publication p_value) throws EjbException {
		try {
			oEntityManager.persist(p_value);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

	@Override
	public void update(Publication p_user) throws EjbException {
		try {
			Publication r = oEntityManager.find(Publication.class, p_user.getUuid());
			if (r != null) {
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
			Publication r = oEntityManager.find(Publication.class, p_id);
			oEntityManager.remove(r);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			throw new EjbException(e);
		}
	}

}
