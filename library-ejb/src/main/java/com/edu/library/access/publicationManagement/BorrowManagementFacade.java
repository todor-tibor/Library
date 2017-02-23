package com.edu.library.access.publicationManagement;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.edu.library.IBorrowService;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.publicationManagement.BorrowManagementBusiness;
import com.edu.library.filter.BorrowFilter;
import com.edu.library.model.Borrow;

/**
 * Implements the basics of borrowing. Validates the given input data and calls
 * the business layer if params are valid
 * 
 * @author gallb
 * @author kiska
 */
@Stateless
public class BorrowManagementFacade implements IBorrowService {

	@EJB
	private BorrowManagementBusiness borrowBusiness;

	@Override
	public List<Borrow> getAll() {
		return borrowBusiness.getAll();
	}

	@Override
	public List<Borrow> search(String p_searchTxt) {
		ServiceValidation.checkString(p_searchTxt);
		return borrowBusiness.search(p_searchTxt);
	}

	@Override
	public Borrow getById(String p_id) {
		ServiceValidation.checkUuid(p_id);
		return borrowBusiness.getById(p_id);
	}

	@Override
	public void store(Borrow p_value) {
		// Data validation.
		ServiceValidation.checkNotNull(p_value);
		ServiceValidation.checkNotNull(p_value.getPublication());
		ServiceValidation.checkNotNull(p_value.getUser());
		ServiceValidation.checDateOrder(p_value.getBorrowFrom(), p_value.getBorrowUntil());

		borrowBusiness.store(p_value);
	}

	@Override
	public void update(Borrow p_value) {
		// Data validation.
		ServiceValidation.checkNotNull(p_value);
		ServiceValidation.checkNotNull(p_value.getPublication());
		ServiceValidation.checkNotNull(p_value.getUser());
		ServiceValidation.checDateOrder(p_value.getBorrowFrom(), p_value.getBorrowUntil());

		borrowBusiness.update(p_value);

	}

	@Override
	public void remove(String p_id) {
		ServiceValidation.checkUuid(p_id);
		borrowBusiness.remove(p_id);
	}

	@Override
	public List<Borrow> filterBorrow(BorrowFilter filter) {
		if (filter.getBorrowedFrom() != null && filter.getBorrowedUntil() != null) {
			ServiceValidation.checDateOrder(filter.getBorrowedFrom(), filter.getBorrowedUntil());
		}
		return borrowBusiness.filterBorrow(filter);
	}
}
