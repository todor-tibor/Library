package gallb.wildfly.users.ejb.publicationManagement.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import gallb.wildfly.users.common.IPublication;
import gallb.wildfly.users.ejb.exception.EjbException;
import gallb.wildfly.users.ejb.publicationManagement.business.PublicationBean;
import gallb.wildfly.users.ejb.util.ServiceValidation;
import model.Publication;

public class PublicationServiceBean implements IPublication {

	@PersistenceContext(unitName = "WildflyUsers")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(Publication.class);

	@Inject
	PublicationBean publicationBean;

	@Override
	public List<Publication> getAll() throws EjbException {
		return publicationBean.getAll();
	}

	@Override
	public List<Publication> search(String p_searchTxt) throws EjbException {
		if (ServiceValidation.checkString(p_searchTxt))
			return publicationBean.search(p_searchTxt);
		else{
			oLogger.warn("invalid parameter");
			throw new EjbException("error.invalidString");
		}
	}

	@Override
	public Publication getById(String p_id) throws EjbException {
		if (p_id.length() >= 36)
			return publicationBean.getById(p_id);
		else{
			oLogger.warn("invalid parameter");
			throw new EjbException("error.invalidString");
		}
	}

	@Override
	public void store(Publication p_value) throws EjbException {
		if (p_value != null && ServiceValidation.checkString(p_value.getTitle())) {
			publicationBean.store(p_value);
		} else{
			oLogger.warn("invalid parameter");
			throw new EjbException("error.parameter");
		}
	}

	@Override
	public void update(Publication p_user) throws EjbException {
		if (p_user != null && ServiceValidation.checkString(p_user.getTitle())) {
			publicationBean.update(p_user);
		} else{
			oLogger.warn("invalid parameter");
			throw new EjbException("error.parameter");
		}
	}

	@Override
	public void remove(String p_id) throws EjbException {
		if (p_id.length() >= 36)
			publicationBean.remove(p_id);
		else{
			oLogger.warn("invalid parameter");
			throw new EjbException("error.invalidString");
		}
	}

}
