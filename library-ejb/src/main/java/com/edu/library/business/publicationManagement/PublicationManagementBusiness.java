package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Publication;

/**
 * Implements basic business logic for publication management. (same functions
 * as in the IPublicationService interface)
 *
 * @author sipost
 */

@Stateless
@LocalBean
public class PublicationManagementBusiness {

	@EJB
	private PublicationBean dataAcces;

	/**
	 * Returns all publications.
	 *
	 * @return - List of publications
	 */
	public List<Publication> getAll() {
		return this.dataAcces.getAll();
	}

	/**
	 * Searches for a publication given by {@code searchText}
	 *
	 * @param searchText
	 *            - the title or part of the title of the publication to search
	 *            for
	 * @return - List with all the publications that match the search criteria
	 */
	public List<Publication> search(final String searchText) {
		return this.dataAcces.search(searchText);
	}

	/**
	 * Return the publication given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a publication
	 * @return - a publication
	 */
	public Publication getById(final String id) {
		return this.dataAcces.getById(id);
	}

	/**
	 * Save the publication given by {@code publication}
	 *
	 * @param publication
	 *            - a Publication type object containing all the necessary
	 *            information for saving a publication to the DB.
	 */
	public void store(final Publication publication) {
		this.dataAcces.store(publication);
	}

	/**
	 * Update the publication given in {@code publication}
	 *
	 * @param publication
	 *            - the publication object on which the update will be done
	 */
	public void update(final Publication publication) {
		this.dataAcces.getById(publication.getUuid());
		this.dataAcces.update(publication);
	}

	/**
	 * Remove the publication given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of the publication
	 */
	public void remove(final String id) {
		final Publication pub = this.dataAcces.getById(id);
		if (pub.getBorrows().isEmpty()) {
			this.dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

	/**
	 * Searches for all publications that match certain criteria given by
	 * {@code filter}
	 *
	 * @param filter
	 *            - a custom filter for publications, which represents the
	 *            fields that can be filtered
	 * @return - list of publications that match the search criteria
	 */
	public List<Publication> filterPublication(final PublicationFilter filter) {
		return this.dataAcces.filterPublication(filter);
	}
}
