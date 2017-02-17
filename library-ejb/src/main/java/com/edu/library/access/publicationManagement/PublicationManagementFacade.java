package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IPublicationService;
import com.edu.library.business.publicationManagement.PublicationManagementBusiness;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Publication;
import com.edu.library.util.ServiceValidation;

/**
 * @author kiska Implements the basics of user login. Validates the given the
 *         input data.
 */
@Stateless
public class PublicationManagementFacade implements IPublicationService {

	@EJB
	private PublicationManagementBusiness publicationBusiness;

	@Override
	public List<Publication> getAll() {
		return publicationBusiness.getAll();
	}

	@Override
	public List<Publication> search(String p_searchTxt)  {
		ServiceValidation.checkString(p_searchTxt);
		return publicationBusiness.search(p_searchTxt);
	}

	@Override
	public Publication getById(String p_id)  {
		ServiceValidation.checkUuid(p_id);
		return publicationBusiness.getById(p_id);
	}

	@Override
	public void store(Publication p_value)  {
		ServiceValidation.checkNotNull(p_value);
		authorCheck(p_value);
		publicationBusiness.store(p_value);

	}

	@Override
	public void update(Publication p_user)  {
		ServiceValidation.checkNotNull(p_user);
		authorCheck(p_user);
		publicationBusiness.update(p_user);
	}

	@Override
	public void remove(String p_id)  {
		ServiceValidation.checkUuid(p_id);
		publicationBusiness.remove(p_id);

	}

	private void authorCheck(Publication p_value) {
		if (p_value instanceof Book) {
			ServiceValidation.checkNotEmpty(((Book) p_value).getAuthors());
		}
		if (p_value instanceof Magazine) {
			ServiceValidation.checkNotEmpty(((Magazine) p_value).getAuthors());
		}
	}

}
