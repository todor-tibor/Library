package com.edu.library.business.util;

import java.util.List;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.BorrowDAO;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

/**
 * A class that checks several restriction for a Borrow type object.
 *
 * @author kiska
 *
 */
public class BorrowRestriction {

	public BorrowRestriction() {
	}

	/**
	 * Invokes both user and publication restrictions when the use ris trying to
	 * borrow a publication.
	 *
	 * @param tmpUser
	 *            - the user that wants to borrow
	 * @param pubDAO
	 *            - the class that contains the publication CRUD methods
	 * @param borrowDAO
	 *            - the class that contains the borrow CRUD methods
	 * @param id
	 *            - the borrowing the user wants to borrow
	 */
	public static void checkBorrowRestriction(final UserDao userDAO, final PublicationBean pubDAO,
			final BorrowDAO borrowDAO, final Borrow id) {
		// final User tmpUser = this.userDAO.getById(id.getUser().getUuid());
		// check if trust index is OK
		final User tmpUser = userDAO.getById(id.getUser().getUuid());
		userRestriction(tmpUser);
		final Publication tmpPub = pubDAO.getById(id.getPublication().getUuid());
		publicationResctriction(tmpUser, tmpPub);
	}

	/**
	 * Checks the restrictions of a user when trying to borrow.
	 *
	 * @param tmpUser
	 *            - the user for which to check the restrictions
	 */
	private static void userRestriction(final User tmpUser) {
		checkLoyaltyIndex(tmpUser.getLoyaltyIndex());
		// check if user reached the allowed maximum number of borrowings

		stillCanBorrow(tmpUser.getBorrows());
		// user is not currently late with borrowing
		hasLateBorrowing(tmpUser.isLate());
	}

	/**
	 * Checks the restrictions of a publication when trying to borrow.
	 *
	 * @param tmpUser
	 *            - the user that wants to borrow
	 * @param tmpPub
	 *            - the publication for which to check the restrictions
	 */
	private static void publicationResctriction(final User tmpUser, final Publication tmpPub) {
		// and the user doesn't have it currently borrowed
		hasThisPublicationBorrowed(tmpUser, tmpPub);
		// check if publication is on stock
		notOnStock(tmpPub.getOnStock());
	}

	/**
	 * Checks if a user's loyalty index is equal to or below zero. Throws
	 * exception if it is.
	 *
	 * @param loyaltyIndex
	 */
	private static void checkLoyaltyIndex(final int loyaltyIndex) {
		if (loyaltyIndex <= 0) {
			throw new BusinessException(ErrorMessages.ERROR_LOYALTY);
		}
	}

	/**
	 * Checks if user has already 3 or more borrowings. Throws exception if it
	 * has.
	 *
	 * @param borrows
	 *            - a list containing all of a user's borrowings.
	 */
	private static void stillCanBorrow(final List<Borrow> borrows) {
		if (borrows.size() >= 3) {
			throw new BusinessException(ErrorMessages.ERROR_TOOMUCH_BORROW);
		}
	}

	/**
	 * Checks if user is late with at least one borrowing. Throws exception if
	 * it is.
	 *
	 * @param isLate
	 *            - a boolean value
	 */
	private static void hasLateBorrowing(final boolean isLate) {
		if (isLate) {
			throw new BusinessException(ErrorMessages.ERROR_LATE);
		}
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
	private static boolean currentlyHasItBorrowed(final User user, final Publication publication) {
		final List<Borrow> borrows = user.getBorrows();
		for (int i = 0; i < borrows.size(); i++) {
			if (borrows.get(i).getPublication().getUuid().equals(publication.getUuid())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the user given by {@code user} has already borrowed the
	 * publication given by {@code publication}. Throws an error if the
	 * publication is borrowed for specified user.
	 *
	 * @param user
	 *            - the user for which to check if has the publication given by
	 *            {@code publication} borrowed already
	 * @param publication
	 *            - the publication to check if it is borrowed
	 */
	private static void hasThisPublicationBorrowed(final User user, final Publication publication) {
		if (currentlyHasItBorrowed(user, publication)) {
			throw new BusinessException(ErrorMessages.ERROR_CURRENTLY_BORROWED);
		}
	}

	/**
	 * Checks if number given by {@code stock} is below 0. Throws exception if
	 * the above condition is met.
	 *
	 * @param stock
	 *            - the number to check if it is less than zero
	 */
	private static void notOnStock(final int stock) {
		if (stock <= 0) {
			throw new BusinessException(ErrorMessages.ERROR_STOCK);
		}
	}

}
