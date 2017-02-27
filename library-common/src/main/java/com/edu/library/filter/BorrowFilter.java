package com.edu.library.filter;

import java.util.Date;

/**
 * A representation of all the fields that can be filtered for a Borrow object.
 *
 * @author sipost
 * @author kiska
 */
public class BorrowFilter {
	private String title;
	private String userName;
	private Date borrowedFrom;
	private Date borrowedUntil;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public Date getBorrowedFrom() {
		return this.borrowedFrom;
	}

	public void setBorrowedFrom(final Date borrowedFrom) {
		this.borrowedFrom = borrowedFrom;
	}

	public Date getBorrowedUntil() {
		return this.borrowedUntil;
	}

	public void setBorrowedUntil(final Date borrowedUntil) {
		this.borrowedUntil = borrowedUntil;
	}
}
