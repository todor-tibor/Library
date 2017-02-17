package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IPublisherService;
import com.edu.library.LibraryException;
import com.edu.library.business.publicationManagement.PublisherManagementBusiness;
import com.edu.library.model.Publisher;
import com.edu.library.util.ServiceValidation;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class PublisherManagementFacade implements IPublisherService {

	@EJB
	private PublisherManagementBusiness publisherBusiness;
	
	@Override
	public List<Publisher> getAll() throws LibraryException {
		
		return publisherBusiness.getAll();
	}

	@Override
	public List<Publisher> search(String p_searchTxt) throws LibraryException {
		ServiceValidation.checkString(p_searchTxt);
		return publisherBusiness.search(p_searchTxt);
	}

	@Override
	public Publisher getById(String p_id) throws LibraryException {
		ServiceValidation.checkUuid(p_id);
		return publisherBusiness.getById(p_id);
	}

	@Override
	public void store(Publisher p_value) throws LibraryException {
		ServiceValidation.checkNotNull(p_value);
		publisherBusiness.store(p_value);

	}

	@Override
	public void update(Publisher p_user) throws LibraryException {
		ServiceValidation.checkNotNull(p_user);
		
		publisherBusiness.update(p_user);

	}

	@Override
	public void remove(String p_id) throws LibraryException {
		ServiceValidation.checkUuid(p_id);
		publisherBusiness.remove(p_id);

	}

}
