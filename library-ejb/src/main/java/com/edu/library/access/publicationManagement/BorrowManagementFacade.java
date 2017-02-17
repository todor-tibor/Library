package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.Stateless;

import com.edu.library.IBorrowService;
import com.edu.library.LibraryException;
import com.edu.library.model.Borrow;

/**
 * @author gallb
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class BorrowManagementFacade implements IBorrowService {

	@Override
	public List<Borrow> getAll() throws LibraryException {
		return null;
	}

	@Override
	public List<Borrow> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrow getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Borrow p_value) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Borrow p_user) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		
	}
	
}
