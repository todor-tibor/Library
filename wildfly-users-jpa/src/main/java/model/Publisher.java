package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author nagys The persistent class for the publishers database table.
 */
@Entity
@Table(name = "publishers")
@NamedQueries({ @NamedQuery(name = "Publisher.findAll", query = "SELECT p FROM Publisher p"),
		@NamedQuery(name = "Publisher.findById", query = "Select p from Publisher p Where p.uuid = :uuid"),
		@NamedQuery(name = "Publisher.findByName", query = "Select p from Publisher p where p.name like :name") })

public class Publisher extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * Publisher name
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
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}