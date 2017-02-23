package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.filter.BorrowFilter;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;
import com.edu.library.util.ExceptionHandler;

/**
 * @author nagys, gallb, kiska
 *
 */

@Named("borrowBean")

@SessionScoped
public class BorrowMB implements Serializable {

	private Logger oLogger = Logger.getLogger(BorrowMB.class);
	private static final long serialVersionUID = 1479586528417507035L;

	@Inject
	private IBorrowService oBorrowBean;
	@Inject
	ExceptionHandler exceptionHandler;

	@Inject
	MessageService message;

	BorrowFilter filter = new BorrowFilter();

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
		borrows.clear();
		try {
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
			message.warn("managedbean.required");

		} else {
			p_Borrow.setUser(currentUser);
			p_Borrow.setPublication(currentPublication);
			p_Borrow.setBorrowFrom(date1);
			p_Borrow.setBorrowUntil(date2);
			try {
				oBorrowBean.store(p_Borrow);
				borrows.add(p_Borrow);
				message.info("managedBean.storeSuccess");
			} catch (Exception e) {
				oLogger.error(e.getMessage());
				exceptionHandler.showMessage(e);
			}
		}
	}

	public void remove() {
		if (borrow == null) {
			message.error("managedbean.empty");
		} else {
			try {
				oBorrowBean.remove(borrow.getUuid());
				getAll();
				message.info("managedbean.deleteSuccess");
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
			getAll();
			message.info("managedbean.updateSuccess");
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
			message.error("managedbean.string");
		}
	}

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

	/**
	 * filter publication
	 * 
	 * @return
	 */
	public List<Borrow> filterBorrow() {
		oLogger.info("filter publication " + filter.getTitle());
		this.borrows.clear();
		try {
			this.borrows = oBorrowBean.filterBorrow(filter);
			if (this.borrows.isEmpty()) {
				message.warn("ejb.message.noEntity");
			}
		} catch (Exception e) {
			oLogger.error(e);
			exceptionHandler.showMessage(e);
		}
		return this.borrows;
	}

	public BorrowFilter getFilter() {
		return filter;
	}

	public void setFilter(BorrowFilter filter) {
		this.filter = filter;
	}

}
