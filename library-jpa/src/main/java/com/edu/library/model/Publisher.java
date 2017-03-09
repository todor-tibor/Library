package com.edu.library.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
		@NamedQuery(name = "Publisher.getByName", query = "SELECT p FROM Publisher p WHERE p.name = :name"),
		@NamedQuery(name = "Publisher.findById", query = "SELECT p FROM Publisher p WHERE p.uuid = :uuid") })
@XmlRootElement(name = "Publisher")
public class Publisher extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * @param name
	 *            -publisher name
	 */
	@XmlElement
	private String name;

	public Publisher() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}