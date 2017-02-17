package com.edu.library;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.IBorrowService;
import com.edu.library.LibraryException;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

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

	private List<Borrow> borrows = new ArrayList<>();
	private User currentUser = null;
	private Publication currentPublication = null;

	private Borrow borrow = null;
	private Date date1 = new Date();
	private Date date2 = null;

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
		} catch (LibraryException e) {

			MessageService.error("Server internal error.");
		}
		return borrows;
	}

	public void store() {

		Borrow p_Borrow;
		p_Borrow = new Borrow();
		if ((currentPublication == null) || (currentUser == null) || (date2 == null)) {
			MessageService.warn("All field is requered");

		} else {
			p_Borrow.setUser(currentUser);
			p_Borrow.setPublication(currentPublication);
			p_Borrow.setBorrowFrom(date1);
			p_Borrow.setBorrowUntil(date2);
			try {
				oBorrowBean.store(p_Borrow);
				borrows.add(p_Borrow);
				MessageService.info("Succesfully added: " + p_Borrow);
			} catch (LibraryException e) {
				MessageService.error(e.getMessage());
			}
		}
	}

	public void remove() {
		oLogger.info("--remove borrow by Id ManagedBean--p_id:" + borrow.getUuid());
		if (borrow == null) {
			MessageService.error("Empty field");
		} else {
			try {
				oBorrowBean.remove(borrow.getUuid());
				getAll();
			} catch (LibraryException e) {
				oLogger.error(e);
				MessageService.error(e.getMessage());
			}

		}
	}

}
