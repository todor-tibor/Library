package com.edu.library.business.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Publication;

/**
 * Implements basic business logic for publication management. (same functions
 * as in the IPublicationService interface)
 *
 * @author sipost
 */

@Stateless
@LocalBean
public class PublicationManagementBusiness {

	@EJB
	private PublicationBean dataAcces;

	public List<Publication> getAll() {
		return this.dataAcces.getAll();
	}

	public List<Publication> search(final String searchText) {
		return this.dataAcces.search(searchText);
	}

	public Publication getById(final String id) {
		return this.dataAcces.getById(id);
	}

	public void store(final Publication publication) {
		this.dataAcces.store(publication);
	}

	public void update(final Publication publication) {
		this.dataAcces.getById(publication.getUuid());
		this.dataAcces.update(publication);
	}

	public void remove(final String id) {
		final Publication pub = this.dataAcces.getById(id);
		if (pub.getBorrows().isEmpty()) {
			this.dataAcces.remove(pub);
		} else {
			throw new BusinessException(ErrorMessages.ERROR_BOUND);
		}
	}

	public List<Publication> filterPublication(final PublicationFilter filter) {
		return this.dataAcces.filterPublication(filter);
	}

}
