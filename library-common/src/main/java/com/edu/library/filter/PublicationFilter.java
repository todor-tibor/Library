package com.edu.library.filter;

import java.util.Date;

/**
 * A representation of all the fields that can be filtered for a Publication
 * object.
 * 
 * @author sipost
 * @author kiska
 */
public class PublicationFilter {
	private String title;
	private String publisher;
	private boolean onStock;
	private String author;
	private Date from;
	private Date until;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isOnStock() {
		return onStock;
	}

	public void setOnStock(boolean onStock) {
		this.onStock = onStock;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}
}
