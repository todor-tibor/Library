package com.edu.library.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the authors database table.
 *
 *
 * @author sipost
 * @author kiska
 *
 */
@Entity
@Table(name = "authors")
@NamedQueries({ @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a"),
		@NamedQuery(name = "Author.searchByName", query = "SELECT a FROM Author a WHERE a.name like :name"),
		@NamedQuery(name = "Author.getById", query = "SELECT a FROM Author a WHERE a.uuid = :uuid"),
		@NamedQuery(name = "Author.getByName", query = "SELECT a FROM Author a WHERE a.name = :name") })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Author")
public class Author extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String name;

	public Author() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "--" + this.name + "--";
	}

}