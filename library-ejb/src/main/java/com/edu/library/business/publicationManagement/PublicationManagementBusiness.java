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
	 * Lists Publications that are in the database.
	 *
	 * @param start
	 *            - start row number
	 * @param fin
	 *            - number of data per page
	 * @return List containing all entities.
	 */
	public List<Publication> getAll(final int start, final int fin) {
		return this.dataAcces.getAll(start, fin);
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

	public List<Publication> searchContent(final String searchText, final int start, final int fin) {
		return this.dataAcces.searchContent(searchText, start, fin);
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
	 * Filters the data specified by the {@code filter} object. This can have
	 * one or more filters set. For example: publication title, publisher etc.
	 *
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @param start
	 *            -start row number
	 * @param fin
	 *            -number of data per page
	 * @return List of Publications
	 */
	public List<Publication> filterPublication(final PublicationFilter filter, final int start, final int fin) {
		return this.dataAcces.filterPublication(filter, start, fin);
	}

	/**
	 * Count publications that is in the database.
	 *
	 * @return Number of Publications found
	 */
	public long countAll() {
		return this.dataAcces.countAll();
	}

	/**
	 * Lists of publications that is in the database and contains the
	 * {@code searchTxt} input string
	 *
	 * @param searchTxt
	 *            - String to search for
	 * @return List of entities found, empty list if nothing found.
	 */
	public List<Publication> search(final String text, final int start, final int fin) {
		return this.dataAcces.search(text, start, fin);
	}

	/**
	 * Count publications that is in the database and contains the
	 * {@code searchTxt} input string
	 *
	 * @param title
	 *            - String to search for
	 * @return Number of Publications found
	 */
	public long countSearch(final String text) {
		return this.dataAcces.countSearch(text);
	}

	/**
	 * Count the data specified by the {@code filter} object. This can have one
	 * or more filters set. For example: publication title, publisher etc.
	 *
	 * @param filter
	 *            - a custom class that represents all the fields that can be
	 *            filtered of a publication object.
	 * @return Number of Publications filtered
	 */
	public long countFilter(final PublicationFilter filter) {
		return this.dataAcces.countFilter(filter);
	}
}
