package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.exception.TechnicalException;
import com.edu.library.data.publicationManagement.AuthorBean;
import com.edu.library.model.Author;

@Stateless
@LocalBean
public class AuthorManagementBusiness {

	@EJB
	private AuthorBean dataAcces;

	public List<Author> getAll() {
		return dataAcces.getAll();
	}

	public List<Author> search(String p_searchTxt) {
		return dataAcces.search(p_searchTxt);
	}

	public Author getById(String p_id) {
		return dataAcces.getById(p_id);
	}

	public void store(Author p_value) {
		dataAcces.store(p_value);
	}

	public void update(Author p_user) {
		dataAcces.update(p_user);

	}

	public void remove(String p_id) {
		dataAcces.remove(dataAcces.getById(p_id));

	}

}
