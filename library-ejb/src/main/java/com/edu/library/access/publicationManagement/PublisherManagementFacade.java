package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IPublisherService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.publicationManagement.PublisherManagementBusiness;
import com.edu.library.model.Publisher;

/**
 * Implements the basics of publisher management. Validates the given input data
 * and calls the business layer if params are valid.
 *
 * @author nagys
 */
@Stateless
public class PublisherManagementFacade implements IPublisherService {

	@EJB
	private PublisherManagementBusiness publisherBusiness;

	@Override
	public List<Publisher> getAll() {
		return this.publisherBusiness.getAll();
	}

	@Override
	public List<Publisher> search(final String searchText) {
		ServiceValidation.checkString(searchText);
		return this.publisherBusiness.search(searchText);
	}

	@Override
	public Publisher getById(final String id) {
		ServiceValidation.checkUuid(id);
		return this.publisherBusiness.getById(id);
	}

	@Override
	public void store(final Publisher publisher) {
		ServiceValidation.checkNotNull(publisher);
		ServiceValidation.checkString(publisher.getName());
		this.publisherBusiness.store(publisher);
	}

	@Override
	public void update(final Publisher publisher) {
		ServiceValidation.checkNotNull(publisher);
		ServiceValidation.checkString(publisher.getName());
		this.publisherBusiness.update(publisher);
	}

	@Override
	public void remove(final String id) {
		ServiceValidation.checkUuid(id);
		this.publisherBusiness.remove(id);
	}

}
