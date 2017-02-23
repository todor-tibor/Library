package com.edu.library.filter;

import java.util.Date;

public class BorrowFilter {
	private String title;
	private String userName;
	private Date borrowedFrom;
	private Date borrowedUntil;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBorrowedFrom() {
		return borrowedFrom;
	}

	public void setBorrowedFrom(Date borrowedFrom) {
		this.borrowedFrom = borrowedFrom;
	}

	public Date getBorrowedUntil() {
		return borrowedUntil;
	}

	public void setBorrowedUntil(Date borrowedUntil) {
		this.borrowedUntil = borrowedUntil;
	}
}
