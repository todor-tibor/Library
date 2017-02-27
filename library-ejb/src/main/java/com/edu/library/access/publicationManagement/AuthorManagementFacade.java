package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IAuthorService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.publicationManagement.AuthorManagementBusiness;
import com.edu.library.model.Author;

/**
 * Implements the basics of author management. Validates the given input data
 * and calls the business layer if params are valid.
 * 
 * @author sipost
 */
@Stateless
public class AuthorManagementFacade implements IAuthorService {

	@EJB
	AuthorManagementBusiness authorBussines;

	@Override
	public List<Author> getAll() {
		return authorBussines.getAll();
	}

	@Override
	public List<Author> search(String searchText) {
		ServiceValidation.checkString(searchText);
		return authorBussines.search(searchText);
	}

	@Override
	public Author getById(String id) {
		ServiceValidation.checkUuid(id);
		return authorBussines.getById(id);
	}

	@Override
	public void store(Author author) {
		ServiceValidation.checkNotNull(author);
		ServiceValidation.checkString(author.getName());
		authorBussines.store(author);
	}

	@Override
	public void update(Author author) {
		ServiceValidation.checkNotNull(author);
		ServiceValidation.checkString(author.getName());
		authorBussines.update(author);
	}

	@Override
	public void remove(String id) {
		ServiceValidation.checkUuid(id);
		authorBussines.remove(id);
	}

}
