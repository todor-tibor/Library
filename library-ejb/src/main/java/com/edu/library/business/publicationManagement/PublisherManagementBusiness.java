package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.data.publicationManagement.PublisherManager;
import com.edu.library.model.Publisher;
/**
 *  Implements basics business logic for publisher management. 
 * (same functions as in the IPublisherService interface)
 
 * @author nagys
 *
 */
@Stateless
@LocalBean
public class PublisherManagementBusiness {

	@EJB
	private PublisherManager publisherManager;

	public List<Publisher> getAll() {
		return publisherManager.getAll();
	}

	public List<Publisher> search(String p_searchText) {
		return publisherManager.search(p_searchText);
	}

	public Publisher getById(String p_id) {
		return publisherManager.getById(p_id);
	}

	public void store(Publisher p_value) {
		publisherManager.store(p_value);
	}

	public void update(Publisher p_value) {
		publisherManager.update(p_value);
	}

	public void remove(String p_id) {
		publisherManager.remove(publisherManager.getById(p_id));
	}

}
