package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;
import com.edu.library.util.ExceptionHandler;

/**
 * @author nagys, gallb
 *
 */

@Named("borrowBean")

@SessionScoped
public class BorrowMB implements Serializable {
	/**
	 * 
	 */
	private Logger oLogger = Logger.getLogger(BorrowMB.class);
	private static final long serialVersionUID = 1479586528417507035L;

	@Inject
	private IBorrowService oBorrowBean;
	@Inject
	ExceptionHandler exceptionHandler;
	
	@Inject
	MessageService message;

	private List<Borrow> borrows = new ArrayList<>();
	private User currentUser = null;
	private Publication currentPublication = null;

	private Borrow borrow = null;
	private Date date1 = new Date();
	private Date date2 = null;
	private Date date3 = null;


	
	
	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public List<Borrow> getBorrowsList() {
		return borrows;
	}

	public Borrow getCurrentBorrow() {
		return borrow;
	}

	public void setCurrentBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User curentUser) {
		this.currentUser = curentUser;
	}

	public Publication getCurrentPublication() {
		return currentPublication;
	}

	public void setCurrentPublication(Publication curentPublication) {
		this.currentPublication = curentPublication;
	}

	/**
	 * @return
	 */
	public List<Borrow> getAll() {
		oLogger.info("--getAllBorrows()--");
		borrows.clear();
		try {
			oLogger.info("--getAllBorrows()--borrows queried");
			borrows = oBorrowBean.getAll();
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
		return borrows;
	}

	public void store() {

		Borrow p_Borrow;
		p_Borrow = new Borrow();
		if ((currentPublication == null) || (currentUser == null) || (date2 == null)) {
			message.warn("All field is requered");

		} else {
			p_Borrow.setUser(currentUser);
			p_Borrow.setPublication(currentPublication);
			p_Borrow.setBorrowFrom(date1);
			p_Borrow.setBorrowUntil(date2);
			try {
				oBorrowBean.store(p_Borrow);
				borrows.add(p_Borrow);
				message.info("Succesfully added");
			} catch (LibraryException e) {
				oLogger.error("-----------??????????????????????"+ e.getMessage());
				exceptionHandler.showMessage(e);
			}catch (IllegalArgumentException e) {
				oLogger.error("-----------<<<<<<<<<<<<<<<<<<<<<<"+ e.getMessage());
				exceptionHandler.showMessage(e);
			}catch (Exception e) {
				oLogger.error("-----------??????????????????????-------------------"+ e.getMessage());
				oLogger.error("-----"+e.getClass().getSimpleName());
				exceptionHandler.showMessage(e);
			}
		}
	}

	public void remove() {
		oLogger.info("remove borrow by Id ManagedBean--p_id:" + borrow.getUuid());
		if (borrow == null) {
			message.error("Empty field");
		} else {
			try {
				oBorrowBean.remove(borrow.getUuid());
				getAll();
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		}
	}
	
	public void update() {
		borrow.setBorrowUntil(date3);
		try {
			oBorrowBean.update(borrow);
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}

	}
	
	public void search(String p_searchTxt) {
		if (p_searchTxt.length() >= 3) {
			borrows.clear();
			try {
				borrows = oBorrowBean.search(p_searchTxt);
			} catch (Exception e) {
				oLogger.error(e);
				exceptionHandler.showMessage(e);
			}
		} else {
			MessageService.error("Keyword too short. Min. 3 characters req.");
		}
	}
	
	/**
	 * @return
	 */
	public Boolean isSelected() {
		if (this.borrow == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void setUntil() {
		date3 = borrow.getBorrowUntil();
	}
	
}
