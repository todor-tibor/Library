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
	public List<Author> search(String p_searchTxt) {
		ServiceValidation.checkString(p_searchTxt);
		return authorBussines.search(p_searchTxt);
	}

	@Override
	public Author getById(String p_id) {
		ServiceValidation.checkUuid(p_id);
		return authorBussines.getById(p_id);
	}

	@Override
	public void store(Author p_value) {
		ServiceValidation.checkNotNull(p_value);
		ServiceValidation.checkString(p_value.getName());
		authorBussines.store(p_value);
	}

	@Override
	public void update(Author p_user) {
		ServiceValidation.checkNotNull(p_user);
		ServiceValidation.checkString(p_user.getName());
		authorBussines.update(p_user);
	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		authorBussines.remove(p_id);
	}

}
