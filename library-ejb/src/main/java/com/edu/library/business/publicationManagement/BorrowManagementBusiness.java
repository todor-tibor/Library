/**
 *
 */
package com.edu.library.business.publicationManagement;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.BorrowDAO;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.filter.BorrowFilter;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

/**
 * Implements basic business logic for borrow management.
 *
 * @author gallb
 * @author kiska
 */

@Stateless
@LocalBean
public class BorrowManagementBusiness {

	private final Logger logger = Logger.getLogger(BorrowManagementBusiness.class);

	@EJB
	private BorrowDAO borrowDAO;
	@EJB
	private UserDao userDAO;
	@EJB
	private PublicationBean pubDAO;

	/**
	 * @return List containing all entities.
	 */
	public List<Borrow> getAll() {
		return this.borrowDAO.getAll();
	}

	/**
	 * Verifies if the given user is currently having a publication borrowed.
	 *
	 * @param user
	 *            user to verify
	 * @param publication
	 *            publication to verify
	 * @return true if the publication is already borrowed, false if not
	 */
	private boolean currentlyHasItBorrowed(final User user, final Publication publication) {
		final List<Borrow> borrows = user.getBorrows();
		for (int i = 0; i < borrows.size(); i++) {
			if (borrows.get(i).getPublication().getUuid().equals(publication.getUuid())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches for a borrowing given by {@code searchText}
	 *
	 * @param searchText
	 *            - the name or part of the name of the borrowing to search for
	 * @return - List with all the borrows that match the search criteria
	 */
	public List<Borrow> search(final String searchText) {
		final List<Borrow> tmpList = this.userDAO.getBorrow(searchText);
		final Set<Borrow> tempSet = new HashSet<Borrow>(tmpList);
		tempSet.addAll(this.pubDAO.getBorrow(searchText));
		tmpList.clear();
		tmpList.addAll(tempSet);
		return tmpList;
	}

	/**
	 * Return the borrowing given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a borrow
	 * @return - a borrow
	 */
	public Borrow getById(final String id) {
		return this.borrowDAO.getById(id);
	}

	/**
	 * Save the borrowing given by {@code author}
	 *
	 * @param id
	 *            - the unique identifier of a borrow
	 */
	public void store(final Borrow id) {
		// get current data of user
		final User tmpUser = this.userDAO.getById(id.getUser().getUuid());
		// check if trust index is OK
		if (tmpUser.getLoyaltyIndex() > 0) {
			// check if user reached the allowed maximum number of borrowings
			if (tmpUser.getBorrows().size() < 3) {
				// user is not currently late with borrowing
				if (!tmpUser.isLate()) {
					final Publication tmpPub = this.pubDAO.getById(id.getPublication().getUuid());
					// and the user doesn't have it currently borrowed
					if (!this.currentlyHasItBorrowed(tmpUser, tmpPub)) {
						// check if publication is on stock
						if (tmpPub.getOnStock() > 0) {
							tmpPub.setOnStock(tmpPub.getOnStock() - 1);
							this.borrowDAO.store(id);
						} else {
							throw new BusinessException(ErrorMessages.ERROR_STOCK);
						}
					} else {
						throw new BusinessException(ErrorMessages.ERROR_CURRENTLY_BORROWED);
					}
				} else {
					throw new BusinessException(ErrorMessages.ERROR_LATE);
				}
			} else {
				throw new BusinessException(ErrorMessages.ERROR_TOOMUCH_BORROW);
			}
		} else {
			throw new BusinessException(ErrorMessages.ERROR_LOYALTY);
		}
	}

	/**
	 * Update the borrowing given by {@code id}
	 *
	 * @param id
	 *            - the unique identifier of a borrow
	 */
	public void update(final Borrow id) {
		this.borrowDAO.getById(id.getUuid());
		this.borrowDAO.update(id);
	}

	/**
	 * Removes borrowing entity from persistence by ID. Handles stock and trust
	 * index related changes for publication and user entities.
	 *
	 * @param id
	 *            - id of borrowing entity.
	 */
	public void remove(final String id) {
		final Borrow tmpBorrow = this.borrowDAO.getById(id);
		ServiceValidation.checkNotNull(tmpBorrow);

		final Date tmpDate = new Date();
		// verify if user is late
		if (tmpBorrow.getBorrowUntil().before(tmpDate)) {
			// decrease loyalty index
			final User tmpUser = tmpBorrow.getUser();
			tmpUser.setLoyaltyIndex(tmpUser.getLoyaltyIndex() - 1);
			this.userDAO.update(tmpUser);
		}
		// increase stock
		final Publication tmpPub = this.pubDAO.getById(tmpBorrow.getPublication().getUuid());
		tmpPub.setOnStock(tmpPub.getOnStock() + 1);
		this.pubDAO.update(tmpPub);
		this.borrowDAO.remove(tmpBorrow);
	}

	/**
	 * Searches for all borrowings that match certain criteria given by
	 * {@code filter}
	 *
	 * @param filter
	 *            - a custom filter for borrowings, which represents the the
	 *            fields that can be filtered
	 * @return - list of borrows that match the search criteria
	 */
	public List<Borrow> filterBorrow(final BorrowFilter filter) {
		return this.borrowDAO.filterBorrow(filter);
	}
}
