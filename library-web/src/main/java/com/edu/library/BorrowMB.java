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
import com.edu.library.util.MessageService;

/**
 * Borrow manager.
 *
 * @author nagys, gallb, kiska
 *
 */

@Named("borrowBean")

@SessionScoped
public class BorrowMB implements Serializable {

	private final Logger logger = Logger.getLogger(BorrowMB.class);
	private static final long serialVersionUID = 1479586528417507035L;

	@Inject
	private IBorrowService oBorrowBean;
	@Inject
	ExceptionHandler exceptionHandler;

	@Inject
	MessageService message;

	/**
	 * Filter for Borrow List
	 */
	BorrowFilter filter = new BorrowFilter();

	/**
	 * List of displayed borrows
	 */
	private List<Borrow> borrows = new ArrayList<>();

	/**
	 * Selected user
	 */
	private User currentUser = null;

	/**
	 * Selected publication
	 */
	private Publication currentPublication = null;

	/**
	 * Selected borrow
	 */
	private Borrow borrow = null;
	private Date date1 = new Date();
	private Date date2 = null;
	private Date date3 = null;

	/**
	 * Get all borrows.
	 *
	 * @return List of borrows
	 */
	public List<Borrow> getAll() {
		this.borrows.clear();
		try {
			this.borrows = this.oBorrowBean.getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.borrows;
	}

	/**
	 * Store the new borrow
	 */
	public void store() {

		Borrow p_Borrow;
		p_Borrow = new Borrow();
		if ((this.currentPublication == null) || (this.currentUser == null) || (this.date2 == null)) {
			this.message.warn("managedbean.required");

		} else {
			p_Borrow.setUser(this.currentUser);
			p_Borrow.setPublication(this.currentPublication);
			p_Borrow.setBorrowFrom(this.date1);
			p_Borrow.setBorrowUntil(this.date2);
			try {
				this.oBorrowBean.store(p_Borrow);
				this.borrows.add(p_Borrow);
			} catch (final Exception e) {
				this.logger.error(e.getMessage());
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Delete borrow (return publication)
	 */
	public void remove() {
		if (this.borrow == null) {
			this.message.error("managedbean.empty");
		} else {
			try {
				this.oBorrowBean.remove(this.borrow.getUuid());
				getAll();
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		}
	}

	/**
	 * Update the borrow until.
	 */
	public void update() {
		this.borrow.setBorrowUntil(this.date3);
		try {
			this.oBorrowBean.update(this.borrow);
			getAll();
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}

	}

	/**
	 * Search borrow by User_name and/or Publication_title given by
	 * {@code searchTxt}
	 *
	 * @param searchTxt
	 *            -user name or publication title
	 */
	public void search(final String searchTxt) {
		if (searchTxt.length() >= 3) {
			this.borrows.clear();
			try {
				this.borrows = this.oBorrowBean.search(searchTxt);
			} catch (final Exception e) {
				this.logger.error(e);
				this.exceptionHandler.showMessage(e);
			}
		} else {
			this.message.error("managedbean.string");
		}
	}

	/**
	 * Check if there is selected Borrow from data table.
	 *
	 * @return true if currently selected borrow not null
	 */
	public Boolean isSelected() {
		if (this.borrow == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Filter Borrow
	 *
	 * @return list of filtered borrows
	 */
	public List<Borrow> filterBorrow() {
		this.borrows.clear();
		try {
			this.borrows = this.oBorrowBean.filterBorrow(this.filter);
			if (this.borrows.isEmpty()) {
				this.message.warn("ejb.message.noEntity");
			}
		} catch (final Exception e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.borrows;
	}

	/*
	 * getters and setters for private attributes
	 */

	public BorrowFilter getFilter() {
		return this.filter;
	}

	public void setFilter(final BorrowFilter filter) {
		this.filter = filter;
	}

	public Date getDate3() {
		return this.date3;
	}

	public void setDate3(final Date date3) {
		this.date3 = date3;
	}

	public Date getDate1() {
		return this.date1;
	}

	public void setDate1(final Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return this.date2;
	}

	public void setDate2(final Date date2) {
		this.date2 = date2;
	}

	public void setUntil() {
		this.date3 = this.borrow.getBorrowUntil();
	}

	public List<Borrow> getBorrowsList() {
		return this.borrows;
	}

	public Borrow getCurrentBorrow() {
		return this.borrow;
	}

	public void setCurrentBorrow(final Borrow borrow) {
		this.borrow = borrow;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(final User curentUser) {
		this.currentUser = curentUser;
	}

	public Publication getCurrentPublication() {
		return this.currentPublication;
	}

	public void setCurrentPublication(final Publication curentPublication) {
		this.currentPublication = curentPublication;
	}

}
