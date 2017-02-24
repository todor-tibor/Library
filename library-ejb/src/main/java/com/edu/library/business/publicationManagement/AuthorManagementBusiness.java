package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.data.publicationManagement.AuthorBean;
import com.edu.library.model.Author;

/**
 * Implements basic business logic for author management. (same functions as in
 * the IAuthorService interface)
 *
 * @author sipost
 *
 */
@Stateless
@LocalBean
public class AuthorManagementBusiness {

	@EJB
	private AuthorBean dataAcces;

	/**
	 * Returns all authors.
	 *
	 * @return - List of authors
	 */
	public List<Author> getAll() {
		return this.dataAcces.getAll();
	}

	/**
	 * Searches for an author given by {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the author to search for
	 * @return - List with all the authors that match the search criteria
	 */
	public List<Author> search(final String searchText) {
		return this.dataAcces.search(searchText);
	}

	/**
	 * Return the author given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of an author
	 * @return - an author
	 */
	public Author getById(final String id) {
		return this.dataAcces.getById(id);
	}

	/**
	 * Save the author given by {@code author}
	 * 
	 * @param author
	 *            - an Author type object containing all the necessary
	 *            information for saving an author to the DB.
	 */
	public void store(final Author author) {
		this.dataAcces.store(author);
	}

	/**
	 * Update the author given in {@code author}
	 * 
	 * @param author
	 *            - the author object on which the update will be done
	 */
	public void update(final Author author) {
		this.dataAcces.update(author);
	}

	/**
	 * Remove the author given by {@code id}
	 * 
	 * @param id
	 *            - the unique identifier of the author
	 */
	public void remove(final String id) {
		this.dataAcces.remove(this.dataAcces.getById(id));
	}

}
