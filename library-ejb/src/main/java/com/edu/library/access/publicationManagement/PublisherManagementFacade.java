package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IPublisherService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.publicationManagement.PublisherManagementBusiness;
import com.edu.library.model.Publisher;

/**
 * Implements the basics of publisher management. Validates the given input data,
 * and call the business layer if params are valid.
 * 
 * @author nagys
 */
@Stateless
public class PublisherManagementFacade implements IPublisherService {

	@EJB
	private PublisherManagementBusiness publisherBusiness;

	@Override
	public List<Publisher> getAll() {
		return publisherBusiness.getAll();
	}

	@Override
	public List<Publisher> search(String p_searchTxt) {
		ServiceValidation.checkString(p_searchTxt);
		return publisherBusiness.search(p_searchTxt);
	}

	@Override
	public Publisher getById(String p_id) {
		ServiceValidation.checkUuid(p_id);
		return publisherBusiness.getById(p_id);
	}

	@Override
	public void store(Publisher p_value) {
		ServiceValidation.checkNotNull(p_value);
		ServiceValidation.checkString(p_value.getName());
		publisherBusiness.store(p_value);
	}

	@Override
	public void update(Publisher p_user) {
		ServiceValidation.checkNotNull(p_user);
		ServiceValidation.checkString(p_user.getName());
		publisherBusiness.update(p_user);
	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		publisherBusiness.remove(p_id);
	}

}
