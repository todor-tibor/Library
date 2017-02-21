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

	/*
	 * @return List containing all entities.
	 */
	public List<Borrow> getAll() {
		return borrowDAO.getAll();
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
		if (userDAO.getById(p_entity.getUser().getUuid()).getLoyaltyIndex() > 0) {
			Publication tmpPub = pubDAO.getById(p_entity.getPublication().getUuid());
			if (tmpPub.getOnStock() > 0) {
				tmpPub.setOnStock(tmpPub.getOnStock() - 1);
				borrowDAO.store(p_entity);
			} else {
				oLogger.info("Not on stock.");
				throw new BusinessException(ErrorMessages.ERROR_STOCK);
			}
		} else {
			oLogger.info("Trust index too low");
			throw new BusinessException(ErrorMessages.ERROR_LOYALTY);
		}
	}

	public void update(Borrow p_entity) throws LibraryException {
		// TODO Auto-generated method stub

	}

	/*
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
