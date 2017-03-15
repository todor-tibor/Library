package com.edu.library.access.publicationManagement;

import java.util.HashMap;
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
		return this.borrowBusiness.getAll();
	}

	@Override
	public List<Borrow> search(final String searchText) {
		ServiceValidation.checkString(searchText);
		return this.borrowBusiness.search(searchText);
	}

	@Override
	public Borrow getById(final String id) {
		ServiceValidation.checkUuid(id);
		return this.borrowBusiness.getById(id);
	}

	@Override
	public void store(final Borrow borrow) {
		// Data validation.
		ServiceValidation.checkNotNull(borrow);
		ServiceValidation.checkNotNull(borrow.getPublication());
		ServiceValidation.checkNotNull(borrow.getUser());
		ServiceValidation.checkDateOrder(borrow.getBorrowFrom(), borrow.getBorrowUntil());

		this.borrowBusiness.store(borrow);
	}

	@Override
	public void update(final Borrow borrow) {
		// Data validation.
		ServiceValidation.checkNotNull(borrow);
		ServiceValidation.checkNotNull(borrow.getPublication());
		ServiceValidation.checkNotNull(borrow.getUser());
		ServiceValidation.checkDateOrder(borrow.getBorrowFrom(), borrow.getBorrowUntil());

		this.borrowBusiness.update(borrow);

	}

	@Override
	public void remove(final String id) {
		ServiceValidation.checkUuid(id);
		this.borrowBusiness.remove(id);
	}

	@Override
	public List<Borrow> filterBorrow(final BorrowFilter filter) {
		if (filter.getBorrowedFrom() != null && filter.getBorrowedUntil() != null) {
			ServiceValidation.checkDateOrder(filter.getBorrowedFrom(), filter.getBorrowedUntil());
		}
		return this.borrowBusiness.filterBorrow(filter);
	}

	@Override
	public List<Borrow> getBorrowLate() {
		return this.borrowBusiness.getBorrwLate();
	}

	@Override
	public void mailOneLateUser(final Borrow borrow) {
		// Data validation.
		ServiceValidation.checkNotNull(borrow);
		ServiceValidation.checkNotNull(borrow.getUser());
		ServiceValidation.checEmail(borrow.getUser().getEmail());

		this.borrowBusiness.mailOneLateUser(borrow);
	}

	@Override
	public HashMap<String, Integer> borrowLateStatistic() {
		return this.borrowBusiness.borrowLateStatistic();
	}
}
