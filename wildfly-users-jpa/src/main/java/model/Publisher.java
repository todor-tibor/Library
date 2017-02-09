package model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the publishers database table.
 * 
 * @author sipost
 * @author kiska
 */
@Entity
@Table(name = "publishers")
@NamedQuery(name = "Publisher.findAll", query = "SELECT p FROM Publisher p")
public class Publisher extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * publisher name
	 */
	private String name;

	public Publisher() {
	}

	/**
	 * @return publisher name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * set publisher name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}