package model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
		@NamedQuery(name = "Author.getById", query = "SELECT a FROM Author a WHERE a.uuid = :uuid") })
public class Author extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String name;

	public Author() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}