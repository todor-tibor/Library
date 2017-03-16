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
		return this.publicationBusiness.getAll();
	}

	@Override
	public List<Publication> search(final String searchText) {
		ServiceValidation.checkString(searchText);
		return this.publicationBusiness.search(searchText);
	}

	@Override
	public List<Publication> searchContent(final String searchText, final int start, final int fin) {
		ServiceValidation.checkString(searchText);
		return this.publicationBusiness.searchContent(searchText, start, fin);
	}

	@Override
	public Publication getById(final String id) {
		ServiceValidation.checkUuid(id);
		return this.publicationBusiness.getById(id);
	}

	@Override
	public void store(final Publication publication) {
		ServiceValidation.checkNotNull(publication);
		checkCopies(publication);
		authorCheck(publication);

		this.publicationBusiness.store(publication);
	}

	@Override
	public void update(final Publication publication) {
		ServiceValidation.checkNotNull(publication);
		checkCopies(publication);
		authorCheck(publication);

		this.publicationBusiness.update(publication);
	}

	@Override
	public void remove(final String id) {
		ServiceValidation.checkUuid(id);
		this.publicationBusiness.remove(id);

	}

	@Override
	public List<Publication> filterPublication(final PublicationFilter filter, final int start, final int fin) {
		return this.publicationBusiness.filterPublication(filter, start, fin);
	}

	private void authorCheck(final Publication publication) {
		if (publication instanceof Book) {
			ServiceValidation.checkNotEmpty(((Book) publication).getAuthors());
		}
		if (publication instanceof Magazine) {
			ServiceValidation.checkNotEmpty(((Magazine) publication).getAuthors());
		}
	}

	private void checkCopies(final Publication publication) {
		ServiceValidation.checkIfNumberInRange(publication.getNrOfCopys(), 0, Integer.MAX_VALUE);
		ServiceValidation.checkIfNumberInRange(publication.getOnStock(), 0, publication.getNrOfCopys());
	}

	@Override
	public long countAll() {
		return this.publicationBusiness.countAll();
	}

	@Override
	public List<Publication> search(final String text, final int start, final int fin) {
		ServiceValidation.checkIfNumberInRange(start, -1, Integer.MAX_VALUE);
		ServiceValidation.checkIfNumberInRange(fin, 0, Integer.MAX_VALUE);
		ServiceValidation.checkString(text);
		return this.publicationBusiness.search(text, start, fin);
	}

	@Override
	public long countSearch(final String title) {
		return this.publicationBusiness.countSearch(title);
	}

	@Override
	public long countFilter(final PublicationFilter filter) {
		return this.publicationBusiness.countFilter(filter);
	}

	@Override
	public List<Publication> getAll(final int start, final int fin) {
		ServiceValidation.checkIfNumberInRange(start, -1, Integer.MAX_VALUE);
		ServiceValidation.checkIfNumberInRange(fin, 0, Integer.MAX_VALUE);
		return this.publicationBusiness.getAll(start, fin);
	}

}
