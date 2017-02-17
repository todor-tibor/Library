package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.Stateless;

import com.edu.library.IAuthorService;
import com.edu.library.LibraryException;
import com.edu.library.model.Author;
import com.edu.library.model.Publication;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class AuthorManagementFacade implements IAuthorService {

	@Override
	public List<Author> getAll() throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Author p_value) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Author p_user) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	
	
}
