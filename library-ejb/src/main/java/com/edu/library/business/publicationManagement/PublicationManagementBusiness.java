package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.edu.library.IPublicationService;
import com.edu.library.LibraryException;
import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.model.Publication;

/**
 * @author kiska
 * 
 *         Implements a simple authentication process of a user.
 */


@Stateless
@LocalBean 
public class PublicationManagementBusiness {

	@EJB
	private PublicationBean dataAcces;

	public List<Publication> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Publication> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Publication getById(String p_id) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void store(Publication p_value) throws LibraryException {
		// TODO Auto-generated method stub

	}

	public void update(Publication p_user) throws LibraryException {
		// TODO Auto-generated method stub

	}

	public void remove(String p_id) {
		Publication pub = dataAcces.getById(p_id);
		if (pub.getBorrows().isEmpty()) {
			dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

}
