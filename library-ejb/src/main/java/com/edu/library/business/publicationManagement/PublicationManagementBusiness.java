package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.exception.TechnicalException;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.model.Publication;
import com.edu.library.util.ServiceValidation;

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
		return dataAcces.getAll();
	}

	public List<Publication> search(String p_searchTxt)  {
		return dataAcces.search(p_searchTxt);
	}

	public Publication getById(String p_id)  {
		return dataAcces.getById(p_id);
	}

	public void store(Publication p_value)  {
		try {
			dataAcces.getByName(p_value.getTitle());
			throw new BusinessException(ErrorMessages.ERROR_CONSTRAINT_VIOLATION);
		} catch (Exception e) {			
			System.out.println("minden rendben, be kellene szurni");
		}
		System.out.println("minden rendben, be kellene szurni1111111111");
		dataAcces.store(p_value);
	}

	public void update(Publication p_user)  {
		dataAcces.getById(p_user.getUuid());
		dataAcces.update(p_user);
	}

	public void remove(String p_id) {
		Publication pub = dataAcces.getById(p_id);
		ServiceValidation.checkNotNull(pub);
		if (pub.getBorrows().isEmpty()) {
			dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

}
