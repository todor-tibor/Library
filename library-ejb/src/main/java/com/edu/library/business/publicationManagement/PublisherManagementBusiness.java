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

	/**
	 * Returns all publishers.
	 *
	 * @return - List of publishers
	 */
	public List<Publisher> getAll() {
		return this.publisherManager.getAll();
	}

	/**
	 * Searches for a publisher given by {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the publisher to search for
	 * @return - List with all the publishers that match the search criteria
	 */
	public List<Publisher> search(final String searchText) {
		return this.publisherManager.search(searchText);
	}

	/**
	 * Return the publisher given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a publisher
	 * @return - a publication
	 */
	public Publisher getById(final String id) {
		return this.publisherManager.getById(id);
	}

	/**
	 * Save the publisher given by {@code publisher}
	 *
	 * @param publisher
	 *            - a Publisher type object containing all the necessary
	 *            information for saving a publisher to the DB.
	 */
	public void store(final Publisher publisher) {
		this.publisherManager.store(publisher);
	}

	/**
	 * Update the publisher given in {@code publisher}
	 *
	 * @param publisher
	 *            - the publisher object on which the update will be done
	 */
	public void update(final Publisher publisher) {
		this.publisherManager.update(publisher);
	}

	/**
	 * Remove the publisher given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the publisher
	 */
	public void remove(final String id) {
		this.publisherManager.remove(this.publisherManager.getById(id));
	}

}
