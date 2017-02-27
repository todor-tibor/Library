/**
 *
 */
package com.edu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.edu.library.model.Borrow;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 * Handles borrowlate.xhtml
 *
 * @author nagys, gallb
 *
 */

@Named("borrowlateBean")

@SessionScoped
public class BorrowLateMB implements Serializable {

	private final Logger oLogger = Logger.getLogger(BorrowLateMB.class);
	private static final long serialVersionUID = -5489087837842352975L;

	@Inject
	private IBorrowService oBorrowBean;
	@Inject
	private ExceptionHandler exceptionHandler;

	@Inject
	private MessageService message;

	private List<Borrow> borrows = new ArrayList<>();
	private Borrow borrow = null;

	public Borrow getCurrentBorrow() {
		return this.borrow;
	}

	public void setCurrentBorrow(final Borrow borrow) {
		this.borrow = borrow;
	}

	public Borrow getBorrow() {
		return this.borrow;
	}

	public void setBorrow(final Borrow borrow) {
		this.borrow = borrow;
	}

	public List<Borrow> getBorrows() {
		return this.borrows;
	}

	public void setBorrows(final List<Borrow> borrows) {
		this.borrows = borrows;
	}

	/*
	 * Returns expired borrows
	 */
	public List<Borrow> getBorrowLate() {
		this.borrows.clear();
		try {
			this.borrows = this.oBorrowBean.getBorrowLate();
		} catch (Exception e) {
			this.oLogger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		return this.borrows;
	}

	/*
	 * Verifies if there is a borrow instance selected currently in the view.
	 */
	public Boolean isSelected() {
		if (this.borrow == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Send mail to the user of the currently selected late borrow.
	 */
	public void mailNotifySelected() {
		try {
			this.oBorrowBean.mailOneLateUser(this.borrow);
			this.message.info("Notification sent to: " + this.borrow.getUser().getUserName());
		} catch (Exception e) {
			this.oLogger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}
}
