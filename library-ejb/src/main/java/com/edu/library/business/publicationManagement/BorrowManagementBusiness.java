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
import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.publicationManagement.BorrowDAO;
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

/**
 * @author gallb
 *		Implements publication related business logic.
 */

@Stateless
@LocalBean 
public class BorrowManagementBusiness {
	
	private Logger oLogger = Logger.getLogger(Borrow.class);
	
	@EJB
	private BorrowDAO borrowDAO;

	public List<Borrow> getAll() {
		borrowDAO.getAll();
		return null;
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
		// TODO Auto-generated method stub

	}

	public void update(Borrow p_entity) throws LibraryException {
		// TODO Auto-generated method stub

	}
	/*
	 * Removes borrowing entity from persistence by ID.
	 * Handles stock and trust index related changes for
	 * publication and user entities.
	 * 
	 * @param p_id - id of borrowing entity.
	 */
	public void remove(String p_id) {
		Borrow tmpBorrow = borrowDAO.getById(p_id);
		Date tmpDate = new Date();
		//verify if user is late
		if (tmpBorrow.getBorrowUntil().before(tmpDate)) {
			oLogger.info("Return late decrease loyalty index.");
			//TO DO DECREASE LOYALTY INDEX, WHEN USER IS READY
			User tmpUser = tmpBorrow.getUser();
			tmpUser.setLoyaltyIndex(tmpUser.getLoyaltyIndex() - 1);
			// user DAO call update
		}
		
		//TO DO DECREASE LOYALTY INDEX, WHEN USER IS READY
		Publication tmpPub = tmpBorrow.getPublication();
		tmpPub.setOnStock(tmpPub.getOnStock() + 1);
		// publication DAO call update
	}
}
