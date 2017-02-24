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
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public boolean isOnStock() {
		return this.onStock;
	}

	public void setOnStock(final boolean onStock) {
		this.onStock = onStock;
	}

	public Date getFrom() {
		return this.from;
	}

	public void setFrom(final Date from) {
		this.from = from;
	}

	public Date getUntil() {
		return this.until;
	}

	public void setUntil(final Date until) {
		this.until = until;
	}
}
