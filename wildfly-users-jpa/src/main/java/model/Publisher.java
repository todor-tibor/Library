package model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
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
@NamedQueries({ @NamedQuery(name = "Publisher.findAll", query = "SELECT p FROM Publisher p"),
	@NamedQuery(name = "Publisher.findByName", query = "SELECT p FROM Publisher p WHERE p.name like :name"),
	@NamedQuery(name = "Publisher.findById", query = "SELECT p FROM Publisher p WHERE p.uuid = :uuid") })
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