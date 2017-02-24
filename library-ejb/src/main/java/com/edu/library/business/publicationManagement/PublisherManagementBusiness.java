package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.data.publicationManagement.PublisherManager;
import com.edu.library.model.Publisher;

/**
 * Implements basic business logic for publisher management. (same functions as
 * in the IPublisherService interface)
 * 
 * @author nagys
 *
 */
@Stateless
@LocalBean
public class PublisherManagementBusiness {

	@EJB
	private PublisherManager publisherManager;

	public List<Publisher> getAll() {
		return this.publisherManager.getAll();
	}

	public List<Publisher> search(final String searchText) {
		return this.publisherManager.search(searchText);
	}

	public Publisher getById(final String id) {
		return this.publisherManager.getById(id);
	}

	public void store(final Publisher publication) {
		this.publisherManager.store(publication);
	}

	public void update(final Publisher publication) {
		this.publisherManager.update(publication);
	}

	public void remove(final String id) {
		this.publisherManager.remove(this.publisherManager.getById(id));
	}

}
