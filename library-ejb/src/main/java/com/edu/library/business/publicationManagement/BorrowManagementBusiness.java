/**
 * 
 */
package com.edu.library.business.publicationManagement;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.edu.library.LibraryException;
import com.edu.library.access.util.ServiceValidation;
import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.BorrowDAO;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

/**
 * @author gallb Implements publication related business logic.
 */

@Stateless
@LocalBean
public class BorrowManagementBusiness {

	private Logger oLogger = Logger.getLogger(BorrowManagementBusiness.class);

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
		return borrowDAO.getAll();
	}
	
	/**
	 * Verifies if a the given user is currently having a publication borrowed.
	 * @param p_user user to verify
	 * @param p_pub publication to verify
	 * @return true if the publication is already borrowed, false if not
	 */
	private boolean currentlyHasItBorrowed (User p_user, Publication p_pub) {
		List<Borrow> borrows = p_user.getBorrows();
		for (int i = 0; i < borrows.size(); i++) {
			if (borrows.get(i).getPublication().getUuid().equals(p_pub.getUuid())) {
				return true;
			}
		}
		return false;	
	}
	
	public List<Borrow> search(String p_searchTxt) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Borrow getById(String p_entity) throws LibraryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void store(Borrow p_entity) throws LibraryException {
		// get current data of user
		User tmpUser = userDAO.getById(p_entity.getUser().getUuid());
		// check if trust index is OK
		if (tmpUser.getLoyaltyIndex() > 0) {
			// check if user reached the allowed maximum number of borrowings
			if (tmpUser.getBorrows().size() < 3) {
				// user is not currently late with borrowing
				if (!tmpUser.isLate()) {
					Publication tmpPub = pubDAO.getById(p_entity.getPublication().getUuid());
					//and the user doesn't have it currently borrowed
					if (!this.currentlyHasItBorrowed(tmpUser, tmpPub)) {
						// check if publication is on stock
						if (tmpPub.getOnStock() > 0) {
							tmpPub.setOnStock(tmpPub.getOnStock() - 1);
							borrowDAO.store(p_entity);
						} else {
							oLogger.info("Not on stock.");
							throw new BusinessException(ErrorMessages.ERROR_STOCK);
						}
					}else  {
						oLogger.info("Not allowed. One copy already borrowed.");
						throw new BusinessException(ErrorMessages.ERROR_CURRENTLY_BORROWED);
					}
				} else {
					oLogger.info("User is currently late with one or more publications.");
					throw new BusinessException(ErrorMessages.ERROR_LATE);
				}
			} else {
				oLogger.info("User reached the maximum number of borrows.");
				throw new BusinessException(ErrorMessages.ERROR_TOOMUCH_BORROW);
			}
		} else {
			oLogger.info("Trust index too low");
			throw new BusinessException(ErrorMessages.ERROR_LOYALTY);
		}
	}

	public void update(Borrow p_entity) throws LibraryException {
		borrowDAO.getById(p_entity.getUuid());
		borrowDAO.update(p_entity);

	}

	/**
	 * Removes borrowing entity from persistence by ID. Handles stock and trust
	 * index related changes for publication and user entities.
	 * 
	 * @param p_id - id of borrowing entity.
	 */
	public void remove(String p_id) {
		Borrow tmpBorrow = borrowDAO.getById(p_id);
		ServiceValidation.checkNotNull(tmpBorrow);

		Date tmpDate = new Date();
		// verify if user is late
		if (tmpBorrow.getBorrowUntil().before(tmpDate)) {
			oLogger.info("Return late decrease loyalty index.");
			// decrease loyalty index
			User tmpUser = tmpBorrow.getUser();
			tmpUser.setLoyaltyIndex(tmpUser.getLoyaltyIndex() - 1);
			userDAO.update(tmpUser);
		}
		// increase stock
		Publication tmpPub = pubDAO.getById(tmpBorrow.getPublication().getUuid());
		tmpPub.setOnStock(tmpPub.getOnStock() + 1);
		pubDAO.update(tmpPub);
		oLogger.info("-------------" + tmpPub.getTitle());
		
		borrowDAO.remove(tmpBorrow);
	}
}
