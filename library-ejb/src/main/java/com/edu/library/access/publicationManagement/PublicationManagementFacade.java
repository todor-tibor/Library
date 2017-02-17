package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.ILoginService;
import com.edu.library.IPublicationService;
import com.edu.library.LibraryException;
import com.edu.library.business.userManagement.LoginManagementBusiness;
import com.edu.library.model.Publication;
import com.edu.library.model.Role;
import com.edu.library.util.EjbException;
import com.edu.library.util.ServiceValidation;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class PublicationManagementFacade implements IPublicationService {

	@Override
	public List<Publication> getAll() throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publication getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Publication p_value) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Publication p_user) throws LibraryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		
	}
	
}
