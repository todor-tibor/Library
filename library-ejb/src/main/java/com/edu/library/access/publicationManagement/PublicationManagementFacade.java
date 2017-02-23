package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IPublicationService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.publicationManagement.PublicationManagementBusiness;
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Publication;

/**
 * Implements the basics of publication management. Validates the given input
 * data and calls the business layer if params are valid.
 * 
 * @author sipost
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
	public List<Publication> search(String p_searchTxt) {
		ServiceValidation.checkString(p_searchTxt);
		return publicationBusiness.search(p_searchTxt);
	}

	@Override
	public Publication getById(String p_id) {
		ServiceValidation.checkUuid(p_id);
		return publicationBusiness.getById(p_id);
	}

	@Override
	public void store(Publication p_value) {
		ServiceValidation.checkNotNull(p_value);
		checkCopies(p_value);
		authorCheck(p_value);

		publicationBusiness.store(p_value);
	}

	@Override
	public void update(Publication p_user) {
		ServiceValidation.checkNotNull(p_user);
		checkCopies(p_user);
		authorCheck(p_user);

		publicationBusiness.update(p_user);
	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		publicationBusiness.remove(p_id);

	}

	@Override
	public List<Publication> filterPublication(PublicationFilter filter) {
		return publicationBusiness.filterPublication(filter);
	}

	private void authorCheck(Publication p_value) {
		if (p_value instanceof Book) {
			ServiceValidation.checkNotEmpty(((Book) p_value).getAuthors());
		}
		if (p_value instanceof Magazine) {
			ServiceValidation.checkNotEmpty(((Magazine) p_value).getAuthors());
		}
	}

	private void checkCopies(Publication p_value) {
		ServiceValidation.checkIfNumberInRange(p_value.getNrOfCopys(), 0, Integer.MAX_VALUE);
		ServiceValidation.checkIfNumberInRange(p_value.getOnStock(), 0, p_value.getNrOfCopys());
	}

}
